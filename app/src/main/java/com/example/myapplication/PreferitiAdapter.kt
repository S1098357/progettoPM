package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PreferitiAdapter : RecyclerView.Adapter<PreferitiAdapter.PreferitiViewHolder>() {
    private var propertyValues: List<PropertyValue> = emptyList()

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
        return PreferitiViewHolder(view)
    }

    override fun onBindViewHolder(holder: PreferitiAdapter.PreferitiViewHolder, position: Int) {
        val propertyValue = propertyValues[position]
        holder.bind(propertyValue)
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