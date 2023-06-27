package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class lista :Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PropertyAdapter
    private lateinit var info:Array<String>

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
        val requestBody = FormBody.Builder()
            .add("center", "40.42938099999995,-3.7097526269835726")
            .add("country", "es")
            .add("maxItems", "50")
            .add("numPage", "1")
            .add("distance", "452")
            .add("propertyType", "bedrooms")
            .add("operation", "rent")
            .build()

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJyZWFkIl0sImV4cCI6MTY4NzkwNzkwNCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9QVUJMSUMiXSwianRpIjoiMWY0ZThkZmQtZGZhZS00MDZmLWFiMmItZGJlN2Y3NTJjZTg1IiwiY2xpZW50X2lkIjoiaGs0anZrMzNmcnRieTE3d25qdzdndGFjOGU3ZXJpcGkifQ.pBjQPpgO3wgiGfyeSj7Is_8z0my2Cpa_B0KmEd1eYLg")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Gestisci l'errore di connessione
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()

                val gson = Gson()

                val property: Property = gson.fromJson(responseBody, Property::class.java)

                val elementList: List<Element> = property.elementList

                activity?.runOnUiThread{
                    adapter.setElements(elementList)
                    Toast.makeText(context,info.toString(),Toast.LENGTH_SHORT).show()
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