package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PropertyAdapter : RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {
    private var elements: List<Element> = emptyList()

    fun setElements(elements: List<Element>) {
        this.elements = elements
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_property, parent, false)
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
        private val propertyTypeTextView: TextView = itemView.findViewById(R.id.propertyTypeTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        private val addressTextView: TextView= itemView.findViewById(R.id.addressTextView)
        private val roomsTextView: TextView= itemView.findViewById(R.id.roomsTextView)
        // Aggiungi gli altri campi del tuo layout

        fun bind(element: Element) {
            propertyTypeTextView.text = element.propertyType
            priceTextView.text = element.price.toString()
            addressTextView.text=element.address
            val stringa="Num. stanze: "+element.rooms.toString()
            roomsTextView.text=stringa
            // Imposta gli altri campi del tuo layout con i dati dell'elemento
        }
    }
}
