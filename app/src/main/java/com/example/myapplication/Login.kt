package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password : EditText
    private lateinit var bottone_login: TextView
    private lateinit var bottone_registrazione: TextView

    private lateinit var mAuth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth=FirebaseAuth.getInstance()

        email=findViewById(R.id.editTextTextEmailAddress)
        password=findViewById(R.id.editTextTextPassword)
        bottone_login=findViewById(R.id.btn_show_popup)
        bottone_registrazione=findViewById(R.id.textView4)

        bottone_registrazione.setOnClickListener{
            val intent= Intent(this, Registrazione::class.java)
            startActivity(intent)
        }

        bottone_login.setOnClickListener{
            val email=email.text.toString()
            val password=password.text.toString()

            login(email,password)
        }
    }

    //logga l'utente con i dati presenti nel db
    private fun login(email: String, password:String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent= Intent(this@Login,MapsActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login,"l'utente non esiste o la password è errata",Toast.LENGTH_SHORT).show()
                }
            }
    }

}