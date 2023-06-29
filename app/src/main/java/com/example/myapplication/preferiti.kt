package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

private lateinit var mAuth: FirebaseAuth
private lateinit var mDbref: DatabaseReference
private lateinit var adapter: PreferitiAdapter
private lateinit var propertyValueList: ArrayList<PropertyValue>
private lateinit var recyclerView: RecyclerView
class preferiti : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root=inflater.inflate(R.layout.fragment_preferiti, container, false)
        recyclerView =root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = PreferitiAdapter()
        recyclerView.adapter = adapter
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAuth=FirebaseAuth.getInstance()
        mDbref= FirebaseDatabase.getInstance("https://unifind-53d53-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
        super.onViewCreated(view, savedInstanceState)
        propertyValueList=ArrayList()
        mDbref.child("property").child("values").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    propertyValueList.clear()

                    for (postSnapshot in snapshot.children){
                        val value=postSnapshot.getValue(PropertyValue::class.java)
                        if (value?.user==FirebaseAuth.getInstance().currentUser?.uid){
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
