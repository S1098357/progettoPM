package com.example.myapplication

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Chat(propertyCode: String?) : Fragment() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private var propertyCode: String = propertyCode!!

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbref: DatabaseReference
    private lateinit var listaUtenti: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.lista_contatti, container, false)
        userRecyclerView = root.findViewById(R.id.user_recycler)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        mDbref =
            FirebaseDatabase.getInstance("https://unifind-53d53-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference()

        //lista degli user
        userList = ArrayList()
        adapter = UserAdapter(requireContext(), userList)

        userRecyclerView.layoutManager = LinearLayoutManager(context)
        userRecyclerView.adapter = adapter
        //lista degli id
        listaUtenti = ArrayList()

        mDbref.child("property").child("values").addValueEventListener(object : ValueEventListener {
            //salva tutti gli id degli utenti che hanno tra i preferiti la proprietà selezionata
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val currentPropertyValue = postSnapshot.getValue(PropertyValue::class.java)
                    if (currentPropertyValue?.propertyCode == propertyCode) {
                        listaUtenti.add(currentPropertyValue.user!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        //salva tutti gli user con la proprietà selezionata tra i preferiti
        mDbref.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children) {

                    val currentUser = postSnapshot.getValue(User::class.java)

                    if (mAuth.currentUser?.uid != currentUser?.uid && listaUtenti.contains(
                            currentUser?.uid
                        )
                    ) {
                        userList.add(currentUser!!)
                    }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}
