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
import okhttp3.*
import java.io.IOException

class lista :Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PropertyAdapter
    private lateinit var info:Array<String>
    private lateinit var distanza:String
    private lateinit var numInquilini:String
    private lateinit var prezzo:String
    private lateinit var ordina:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root= inflater.inflate(R.layout.fragment_lista, container, false)
        recyclerView =root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = PropertyAdapter()
        recyclerView.adapter = adapter
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = "https://api.idealista.com/3.5/es/search"
        val latitude=info[3]
        val longitude=info[4]
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

        val requestBody = FormBody.Builder()
            .add("center", latitude+","+longitude)
            .add("country", "es")
            .add("maxItems", "20")
            .add("distance", distanza)
            .add("numPage", "1")
            .add("maxPrice",prezzo)
            .add("bedrooms",numInquilini)
            .add("propertyType", "bedrooms")
            .add("sort",ordina)
            .add("operation", "rent")
            .add("language","it")

            .build()

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJyZWFkIl0sImV4cCI6MTY4Nzk3OTMzMCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9QVUJMSUMiXSwianRpIjoiOTdlMWE2ZGMtM2QxZC00MTUwLThiYmMtMTNkNDU2OTQ4YmQ3IiwiY2xpZW50X2lkIjoiaGs0anZrMzNmcnRieTE3d25qdzdndGFjOGU3ZXJpcGkifQ.1Qcuw5T37ryNmUV8cJRaadPoBSnGOlMF7MY5lFcA3fo")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread{
                    Toast.makeText(context,"Errore",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()

                val gson = Gson()

                val property: Property = gson.fromJson(responseBody, Property::class.java)

                val elementList: List<Element> = property.elementList

                activity?.runOnUiThread{
                    adapter.setElements(elementList)
                }

            }
        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getStringArray("info")?.let {
            info = it
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