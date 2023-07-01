package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class listaProprieta : Fragment() {

    private lateinit var propertyRecyclerView:RecyclerView
    private lateinit var propertyValueList: ArrayList<PropertyValue>
    private lateinit var adapter: PropertyChatAdapter

    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDbref:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root= inflater.inflate(R.layout.lista_proprieta, container, false)
        propertyRecyclerView=root.findViewById(R.id.proprieta_recycler)
        adapter= PropertyChatAdapter()
        propertyRecyclerView.layoutManager=LinearLayoutManager(context)
        propertyRecyclerView.adapter=adapter
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        propertyValueList= ArrayList()

        mAuth=FirebaseAuth.getInstance()
        mDbref=FirebaseDatabase.getInstance("https://unifind-53d53-default-rtdb.europe-west1.firebasedatabase.app/").getReference()

        mDbref.child("property").child("values").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                propertyValueList.clear()
                for (postSnapshot in snapshot.children){
                    val value=postSnapshot.getValue(PropertyValue::class.java)
                    if (value?.user==FirebaseAuth.getInstance().currentUser?.uid ){
                        propertyValueList.add(value!!)
                    }
                }
                adapter.setPropertyValues(propertyValueList)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}