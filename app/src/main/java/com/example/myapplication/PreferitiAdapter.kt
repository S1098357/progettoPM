package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class PreferitiAdapter : RecyclerView.Adapter<PreferitiAdapter.PreferitiViewHolder>() {
    private var propertyValues: List<PropertyValue> = emptyList()

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbref: DatabaseReference
    private lateinit var propertyValueList: ArrayList<PropertyValue>

    fun setPropertyValues(propertyValues: List<PropertyValue>) {
        this.propertyValues = propertyValues
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PreferitiAdapter.PreferitiViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.property_saved, parent, false)
        propertyValueList= ArrayList()
        mAuth=FirebaseAuth.getInstance()
        mDbref= FirebaseDatabase.getInstance("https://unifind-53d53-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
        return PreferitiViewHolder(view)
    }

    override fun onBindViewHolder(holder: PreferitiAdapter.PreferitiViewHolder, position: Int) {
        val propertyValue = propertyValues[position]
        holder.bind(propertyValue)
        val cliccabile=holder.itemView.findViewById<LinearLayout>(R.id.cliccabile)
        cliccabile.setOnClickListener {
            val intent=Intent(it.context,MapsActivity::class.java)
            intent.putExtra("propertyCode",propertyValue.propertyCode)
            it.context.startActivity(intent)
        }
        val bottone=holder.itemView.findViewById<ImageButton>(R.id.imageButton4)
        bottone.setOnClickListener {
            mDbref.child("property").child("values").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    propertyValueList.clear()
                    var codice: String?
                    for (postSnapshot in snapshot.children) {
                        val value = postSnapshot.getValue(PropertyValue::class.java)
                        codice = value?.propertyCode
                        if (!(value?.user==FirebaseAuth.getInstance().currentUser?.uid&&codice==propertyValue.propertyCode)) {
                            propertyValueList.add(value!!)
                        }
                    }
                    mDbref.child("property").child("values").setValue(propertyValueList)
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return propertyValues.size
    }

    inner class PreferitiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val propertyTypeTextView: TextView =
            itemView.findViewById(R.id.propertyTypeTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        private val roomsTextView: TextView = itemView.findViewById(R.id.roomsTextView)

        fun bind(propertyValue: PropertyValue) {
            propertyTypeTextView.text = propertyValue.propertyTypeTextView
            priceTextView.text = propertyValue.priceTextView
            addressTextView.text = propertyValue.addressTextView
            roomsTextView.text = propertyValue.roomsTextView
        }
    }
}