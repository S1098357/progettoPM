package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

import com.google.android.material.bottomnavigation.BottomNavigationView

class MapsActivity : AppCompatActivity(){

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var prova: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.barra)

        val intent= intent
        if (intent.getStringArrayExtra("info")!=null){
            prova=intent.getStringArrayExtra("info")!!
            if (prova[3]!=""&&prova[4]!=""){
                val lista=lista.newInstance(prova)
                loadFragment(lista)
            }
        }
        if (intent.getStringExtra("propertyCode")!=null){
            val propertyCode=intent.getStringExtra("propertyCode")
            loadFragment(Chat(propertyCode))
        }
        prova=Array(6){""}
        bottomNav=findViewById(R.id.bottom_navigation_view)
        val navigationController=findNavController(R.id.nav_fragment)
        bottomNav.setupWithNavController(navigationController)
        bottomNav.setOnItemSelectedListener{

            when(it.itemId){
                R.id.mappa->loadFragment(mappa())
                R.id.lista->{
                    if (prova=={""}) {
                        Toast.makeText(this,"Per entrare in questa sezione eseguire prima una richiesta dalla mappa",Toast.LENGTH_LONG).show()
                    }else {
                        if (prova[3]!=""&&prova[4]!=""){
                            val lista=lista.newInstance(prova)
                            loadFragment(lista)
                        }else{
                            Toast.makeText(this,"Per entrare in questa sezione eseguire prima una ricerca dalla mappa",Toast.LENGTH_LONG).show()
                        }
                    }
                }
                R.id.preferiti->loadFragment(preferiti())
                R.id.messaggi->loadFragment(listaProprieta())
                R.id.account->loadFragment(account())
                else->{}
            }
            true
        }
    }

private  fun loadFragment(fragment: Fragment) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.nav_fragment, fragment)
    transaction.commit()
}

}