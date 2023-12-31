package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.*
import java.io.IOException

class lista :Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PropertyAdapter
    private lateinit var info: Array<String>
    private lateinit var distanza: String
    private lateinit var numInquilini: String
    private lateinit var prezzo: String
    private lateinit var ordina: String
    private lateinit var accessToken: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_lista, container, false)
        recyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = PropertyAdapter()
        recyclerView.adapter = adapter
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runBlocking {
            makeApiRequest()
        }

        //parametri da inserire nel caso ci siano filtri o meno
        if (info[1]==""){
            distanza="15000"
        }else {
            distanza = info[1]
        }
        if (info[0]==""){
            prezzo="10000"
        }else {
            prezzo = info[0]
        }
        if (info[2]==""){
            numInquilini="1"
        }else {
            numInquilini = info[2]
        }
        if (info[5]==""){
            ordina="asc"
        }else {
            ordina = info[5]
        }


        //nel caso di esaurimento richieste API
        /*val esempio = """{
                        "elementList": [
                    {
                        "propertyCode": "27258694",
                        "thumbnail":
                        "http://im1.idealista.com/thumbs?wi=149&he=105&en=ce5ubOrxWNmH1A3mngN0U7T8055gZnr6FRGL
                        OPQu%2F6HU1hx3p1hrWFhNB0%2FlDIXf&ch=-1707394652",
                        "numPhotos": 16,
                        "floor": "4",
                        "price": 525,
                        "propertyType": "studio",
                        "operation": "rent",
                        "size": 53,
                        "exterior": true,
                        "rooms": 0,
                        "bathrooms": 1,
                        "address": "lago constanza 66",
                        "province": "madrid",
                        "municipality": "madrid",
                        "district": "ciudad lineal",
                        "neighborhood": "ventas",
                        "country": "es",
                        "latitude": 40.4294543,
                        "longitude": -3.6460803,
                        "showAddress": true,
                        "url": "http://www.idealista.com/27258694",
                        "agency": false,
                        "favourite": false,
                        "hasVideo": false,
                        "status": "good",
                        "age": "builtInThe70s",
                        "newDevelopment": true,
                        "newProperty": false
                    }
                    ],
                    "total": 30438,
                    "totalPages": 30438,
                    "actualPage": 1,
                    "itemsPerPage": 1,
                    "numPaginations": 0,
                    "summary": [
                    "alquilar",
                    "viviendas",
                    "madrid provincia",
                    "de todos los precios",
                    "de todos los tamaños",
                    "usada / buen estado"
                    ],
                    "paginable": true,
                    "upperRangePosition": 1,
                    "lowerRangePosition": 0
                }"""*/

    }

    //prende i campi inseriti su info se presenti
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getStringArray("info")?.let {
            info = it
        }
    }

    //richiesta per accessToken
    fun makeApiRequest() {
        val grantType = "client_credentials"
        val scope = "read"
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = ApiClient.apiService.getToken(grantType, scope)
                accessToken = response.accessToken
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val url = "https://api.idealista.com/3.5/es/search"
            val latitude = info[3]
            val longitude = info[4]

            //richiesta per la lista delle proprietà
            val requestBody = FormBody.Builder()
                .add("center", latitude + "," + longitude)
                .add("country", "es")
                .add("maxItems", "20")
                .add("distance", distanza)
                .add("numPage", "1")
                .add("maxPrice", prezzo)
                .add("bedrooms", numInquilini)
                .add("propertyType", "bedrooms")
                .add("sort", ordina)
                .add("operation", "rent")
                .add("language", "it")

                .build()

            val client = OkHttpClient()

            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    activity?.runOnUiThread {
                        Toast.makeText(context, "Errore", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {

                    val responseBody = response.body?.string()

                    val gson = Gson()

                    //data class in ApiIdealista
                    val property: Property = gson.fromJson(responseBody, Property::class.java)

                    //val property: Property = gson.fromJson(esempio, Property::class.java)

                    val elementList: List<Element> = property.elementList

                    activity?.runOnUiThread {
                        adapter.setElements(elementList)
                    }
                }
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(info: Array<String>) =
            lista().apply {
                arguments = Bundle().apply {
                    putStringArray("info", info)
                }
            }
    }
}