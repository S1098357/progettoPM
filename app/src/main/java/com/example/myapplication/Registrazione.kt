package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Registrazione : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password : EditText
    private lateinit var bottone_registrati: TextView
    private lateinit var username: EditText

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrazione)

        email=findViewById(R.id.email)
        password=findViewById(R.id.editTextTextPassword2)
        username=findViewById(R.id.editTextTextPersonName5)
        bottone_registrati=findViewById(R.id.registrazione)

        mAuth= FirebaseAuth.getInstance()

        bottone_registrati.setOnClickListener {
            val name=username.text.toString()
            val email=email.text.toString()
            val password=password.text.toString()

            registrati(name,email,password)
        }
    }

    private fun registrati(name:String,email:String,password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!,password)
                    val intent= Intent(this@Registrazione,MapsActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {

                    Toast.makeText(this@Registrazione,"C'è stato qualche errore",Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun addUserToDatabase(name: String,email: String,uid:String,password: String){

        mDbRef=FirebaseDatabase.getInstance("https://unifind-53d53-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid,password))

    }


}