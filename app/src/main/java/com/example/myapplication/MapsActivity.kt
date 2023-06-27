package com.example.myapplication

import android.app.Activity
import android.app.DownloadManager
import android.app.VoiceInteractor
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.Fragment.*
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.findFragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.PagerAdapter
import com.example.myapplication.databinding.ActivityMainBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.myapplication.databinding.ActivityMapsBinding
import com.example.myapplication.databinding.BarraBinding
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MapsActivity : AppCompatActivity(){

    private lateinit var bottomNav: BottomNavigationView

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.barra)

        run("https://api.idealista.com/3.5/it/search")

        bottomNav=findViewById(R.id.bottom_navigation_view)
        val navigationController=findNavController(R.id.nav_fragment)
        bottomNav.setupWithNavController(navigationController)
        bottomNav.setOnItemSelectedListener{

            when(it.itemId){
                R.id.mappa->  loadFragment(mappa())
                R.id.lista->loadFragment(lista())
                R.id.preferiti->loadFragment(preferiti())
                R.id.messaggi->loadFragment(Chat())
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

    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) = println(response.body?.string())
        })
    }
}