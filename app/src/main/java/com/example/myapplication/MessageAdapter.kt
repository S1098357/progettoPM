package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MessageAdapter(val context : Context, val messageList:ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECIEVE=1
    val ITEM_SENT=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //messaggio ricevuto
        if(viewType==1){
            val view:View= LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return RecieveViewHolder(view)

        }else{
            //messaggio inviato
            val view:View= LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentMessage=messageList[position]

        //abbina l'holder in base al tipo di messaggio e ne sovrascrive il testo
        if (holder.javaClass==SentViewHolder::class.java){

            val viewHolder=holder as SentViewHolder
            holder.sentMessage.text=currentMessage.message

        }else{

            val viewHolder=holder as RecieveViewHolder
            holder.recieveMessage.text=currentMessage.message

        }

    }

    override fun getItemViewType(position: Int): Int {

        val currentMessage=messageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECIEVE
        }

    }

    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val sentMessage=itemView.findViewById<TextView>(R.id.txt_sent_msg)

    }

    class RecieveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val recieveMessage=itemView.findViewById<TextView>(R.id.txt_recieve_msg)

    }
}