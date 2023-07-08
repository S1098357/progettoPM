package com.example.myapplication

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.oAuthCredential
import com.google.firebase.database.*
import android.Manifest
import android.graphics.BitmapFactory
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage


class account : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbref: DatabaseReference
    private lateinit var user: User
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var descrizione: TextInputEditText
    private lateinit var bottone: Button
    private lateinit var credential: AuthCredential
    private lateinit var immagine: ImageView
    private lateinit var selectedImageUri: Uri
    private val PICK_IMAGE_REQUEST = 1

    var immagineCambiata: Boolean= false
    var primaVolta: Boolean= true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_account, container, false)
        username = root.findViewById(R.id.editTextTextUsername)
        password = root.findViewById(R.id.editTextTextPassword)
        descrizione = root.findViewById(R.id.descrizione)
        bottone = root.findViewById(R.id.button2)
        immagine = root.findViewById(R.id.immagine)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        mDbref =
            FirebaseDatabase.getInstance("https://unifind-53d53-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference()
        mDbref.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (postSnapshot in snapshot.children) {
                    val value = postSnapshot.getValue(User::class.java)
                    if (value?.uid == FirebaseAuth.getInstance().currentUser?.uid) {
                        user = value!!
                        credential =
                            EmailAuthProvider.getCredential(
                                user.email!!,
                                user.password!!
                            )
                    }
                }

                username.setText(user.name)
                password.setText(user.password)
                descrizione.setText(user.descrizione)

                if (primaVolta) {
                    val storage = FirebaseStorage.getInstance()
                    val storageRef = storage.reference
                    val imagesRef = storageRef.child("images")
                    val imageRef = imagesRef.child(user.uid + ".jpg")
                    val ONE_MEGABYTE: Long = 1024 * 1024 // Dimensione massima dell'immagine
                    imageRef.getBytes(ONE_MEGABYTE)
                        .addOnSuccessListener { imageData ->
                            val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
                            immagine.setImageBitmap(bitmap)
                        }
                        .addOnFailureListener { exception ->
                        }
                }
                primaVolta=false
            }
                override fun onCancelled(error: DatabaseError) {

                }
        })
        immagine.setOnClickListener {

            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)


        }

        bottone.setOnClickListener {
            if (password.text.toString().length<8){
                Toast.makeText(context,"La password deve contenere almeno 8 caratteri", Toast.LENGTH_LONG).show()
            }else {
                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.reference
                val imagesRef = storageRef.child("images")
                val fileName = user.uid + ".jpg"
                val fileRef = imagesRef.child(fileName)
                if (immagineCambiata) {
                    fileRef.putFile(selectedImageUri)
                }
                user.descrizione = descrizione.text.toString()
                user.password = password.text.toString()
                user.name = username.text.toString()
                mDbref =
                    FirebaseDatabase.getInstance("https://unifind-53d53-default-rtdb.europe-west1.firebasedatabase.app/")
                        .getReference()
                mDbref.child("user").child(mAuth.currentUser?.uid!!).setValue(user)
                mAuth.currentUser!!.reauthenticate(credential)
                mAuth.currentUser!!.updatePassword(user.password!!)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data!!
            immagine.setImageURI(selectedImageUri)
            immagineCambiata=true
        }
    }
}




