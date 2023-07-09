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
import com.google.firebase.database.*
import okhttp3.internal.wait

class PropertyAdapter : RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {
    private var elements: List<Element> = emptyList()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbref: DatabaseReference
    private lateinit var propertyValueList: ArrayList<PropertyValue>
    private lateinit var codici:ArrayList<String?>
    fun setElements(elements: List<Element>) {
        this.elements = elements
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_property, parent, false)
        mAuth=FirebaseAuth.getInstance()
        mDbref= FirebaseDatabase.getInstance("https://unifind-53d53-default-rtdb.europe-west1.firebasedatabase.app/").getReference()

        propertyValueList = ArrayList()
        mDbref.child("property").child("values")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    propertyValueList.clear()

                    for (postSnapshot in snapshot.children) {
                        val value = postSnapshot.getValue(PropertyValue::class.java)
                        if (value?.user == FirebaseAuth.getInstance().currentUser?.uid) {
                            propertyValueList.add(value!!)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

        //lista di property code
        codici = ArrayList()

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
        private val propertyCodeTextView: TextView= itemView.findViewById(R.id.propertyCodeTextView)


        fun bind(element: Element) {
            propertyTypeTextView.text = element.propertyType
            priceTextView.text = element.price.toString()
            addressTextView.text = element.address
            val stringa = "Num. stanze: " + element.rooms.toString()
            roomsTextView.text = stringa
            propertyCodeTextView.text=element.propertyCode
            // Imposta gli altri campi del tuo layout con i dati dell'elemento
        }

        init {
            itemView.setOnClickListener {
                val addressTextView = it.findViewById<TextView>(R.id.addressTextView)
                val priceTextView = it.findViewById<TextView>(R.id.priceTextView)
                val propertyTypeTextView = it.findViewById<TextView>(R.id.propertyTypeTextView)
                val roomsTextView = it.findViewById<TextView>(R.id.roomsTextView)
                val propertyCodeTextView = it.findViewById<TextView>(R.id.propertyCodeTextView)

                //passa il codice della proprietà da salvare
                for (propertyValue in propertyValueList) {
                    codici.add(propertyValue.propertyCode)
                }

                //controlla che la proprietà non sia gia salvata
                if (!codici.contains(propertyCodeTextView.text.toString())) {

                    val propertyValues = PropertyValue(
                        addressTextView.text.toString(),
                        priceTextView.text.toString(),
                        propertyTypeTextView.text.toString(),
                        roomsTextView.text.toString(),
                        FirebaseAuth.getInstance().currentUser?.uid!!,
                        propertyCodeTextView.text.toString()
                    )
                    mDbref.child("property").child("values").push().setValue(propertyValues)
                    Toast.makeText(
                        it.context,
                        "la tua casa è stata salvata e può essere visualizzata dalla tua lista dei preferiti",
                        Toast.LENGTH_LONG
                    ).show()
                }else{
                    Toast.makeText(
                        it.context,
                        "la casa selezionata era già stata salvata da questo utente",
                        Toast.LENGTH_LONG
                    ).show()
                }
                //cambia colore all'oggetto cliccato
                    it.setBackgroundColor(Color.argb(255, 200, 200, 200))
                }
            }

        }
    }




