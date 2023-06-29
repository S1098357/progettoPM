package com.example.myapplication

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import okhttp3.internal.wait

class PropertyAdapter : RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {
    private var elements: List<Element> = emptyList()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbref: DatabaseReference

    fun setElements(elements: List<Element>) {
        this.elements = elements
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_property, parent, false)
        mAuth=FirebaseAuth.getInstance()
        mDbref= FirebaseDatabase.getInstance("https://unifind-53d53-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
        return PropertyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val element = elements[position]
        holder.bind(element)
    }

    override fun getItemCount(): Int {
        return elements.size
    }

    inner class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val propertyTypeTextView: TextView =
            itemView.findViewById(R.id.propertyTypeTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        private val roomsTextView: TextView = itemView.findViewById(R.id.roomsTextView)


        fun bind(element: Element) {
            propertyTypeTextView.text = element.propertyType
            priceTextView.text = element.price.toString()
            addressTextView.text = element.address
            val stringa = "Num. stanze: " + element.rooms.toString()
            roomsTextView.text = stringa
            // Imposta gli altri campi del tuo layout con i dati dell'elemento
        }

        init {
            itemView.setOnClickListener {
                val addressTextView = it.findViewById<TextView>(R.id.addressTextView)
                val priceTextView = it.findViewById<TextView>(R.id.priceTextView)
                val propertyTypeTextView = it.findViewById<TextView>(R.id.propertyTypeTextView)
                val roomsTextView = it.findViewById<TextView>(R.id.roomsTextView)

                val propertyValues = PropertyValue(addressTextView.text.toString(),
                    priceTextView.text.toString(),propertyTypeTextView.text.toString(),roomsTextView.text.toString(),
                    FirebaseAuth.getInstance().currentUser?.uid!!)
                mDbref.child("property").child("values").push().setValue(propertyValues)
                Toast.makeText(it.context, "la tua casa è stata salvata e può essere visualizzata dalla tua lista dei preferiti",Toast.LENGTH_LONG).show()
                it.setBackgroundColor(Color.argb(255,200,200,200))
            }

        }
    }



}

