package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PropertyChatAdapter: RecyclerView.Adapter<PropertyChatAdapter.PropertyChatViewHolder>() {
    private var propertyValues: List<PropertyValue> = emptyList()

    fun setPropertyValues(propertyValues: List<PropertyValue>) {
        this.propertyValues = propertyValues
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyChatAdapter.PropertyChatViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.property_chat, parent, false)
        return PropertyChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return propertyValues.size
    }

    override fun onBindViewHolder(holder: PropertyChatViewHolder, position: Int) {
        val element = propertyValues[position]
        holder.bind(element)
        holder.itemView.setOnClickListener {
            val intent=Intent(it.context,MapsActivity::class.java)
            intent.putExtra("propertyCode",element.propertyCode)
            it.context.startActivity(intent)
        }
    }

    inner class PropertyChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val propertyCodeTextView = itemView.findViewById<TextView>(R.id.propertyCodeTextView)
        private val addressTextView = itemView.findViewById<TextView>(R.id.addressTextView)

        fun bind(element: PropertyValue) {
            addressTextView.text = element.addressTextView
            propertyCodeTextView.text=element.propertyCode

        }
    }
}