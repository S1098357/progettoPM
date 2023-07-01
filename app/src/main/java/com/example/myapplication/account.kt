package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.oAuthCredential
import com.google.firebase.database.*

class account : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbref: DatabaseReference
    private lateinit var user: User
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var descrizione:TextInputEditText
    private lateinit var bottone: Button
    lateinit var credential: AuthCredential

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_account, container, false)
        username=root.findViewById(R.id.editTextTextUsername)
        password=root.findViewById(R.id.editTextTextPassword)
        descrizione=root.findViewById(R.id.descrizione)
        bottone=root.findViewById(R.id.button2)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAuth = FirebaseAuth.getInstance()
        mDbref =
            FirebaseDatabase.getInstance("https://unifind-53d53-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference()
        super.onViewCreated(view, savedInstanceState)
        mDbref.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (postSnapshot in snapshot.children) {
                    val value = postSnapshot.getValue(User::class.java)
                    if (value?.uid == FirebaseAuth.getInstance().currentUser?.uid) {
                        user = value!!
                        credential=EmailAuthProvider.getCredential(user.email!!,user.password!!)
                    }
                }

                username.setText(user.name)
                password.setText(user.password)
                descrizione.setText(user.descrizione)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        username.setOnClickListener{
            bottone.visibility=View.VISIBLE
        }
        password.setOnClickListener{
            bottone.visibility=View.VISIBLE
        }
        descrizione.setOnClickListener{
            bottone.visibility=View.VISIBLE
        }

        bottone.setOnClickListener {
            user.descrizione=descrizione.text.toString()
            user.password=password.text.toString()
            user.name=username.text.toString()
            mDbref=FirebaseDatabase.getInstance("https://unifind-53d53-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
            mDbref.child("user").child(mAuth.currentUser?.uid!!).setValue(user)
            mAuth.currentUser!!.reauthenticate(credential)
            mAuth.currentUser!!.updatePassword(user.password!!)

        }
    }

}