package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var binding: ActivityMainBinding
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.barra)
        var schermo=findViewById<ConstraintLayout>(R.id.ricerca)
        button=schermo.findViewById(R.id.button)
        button.setOnClickListener(this)
        bottomNav=findViewById(R.id.bottom_navigation_view)
        val navigationController=findNavController(R.id.nav_fragment)
        bottomNav.setupWithNavController(navigationController)
        bottomNav.setOnItemSelectedListener{

            when(it.itemId){
                R.id.mappa->loadFragment(mappa())
                R.id.lista->loadFragment(lista())
                R.id.preferiti->loadFragment(preferiti())
                R.id.messaggi->loadFragment(messaggi())
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


    override fun onClick(view: View?){
        val main=findViewById<LinearLayout>(R.id.main)
        val layout= PopupWindow(this)
        val view= layoutInflater.inflate(R.layout.filtri,null)
        layout.contentView= view
        val filtri= view.findViewById<ConstraintLayout>(R.id.filtri)
        var seek=filtri.findViewById<SeekBar>(R.id.seekBar)
        var prezzo=filtri.findViewById<TextView>(R.id.prezzo)
        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, p2: Boolean) {
                var stringaPrezzo= progress.toString()+" €"
                prezzo.text=stringaPrezzo

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }
        })
        var distanza= filtri.findViewById<TextView>(R.id.distanza)
        val seek2=filtri.findViewById<SeekBar>(R.id.seekBar3)
        seek2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, p2: Boolean) {
                val stringa=progress.toString()+ " Km"
                distanza.text=stringa
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }
        })
        view.animation=AnimationUtils.loadAnimation(this,R.anim.slide_in_bottom)
        layout.height=main.height
        layout.width=main.width
        view.setOnClickListener{
            layout.dismiss()
        }
        layout.showAtLocation(main,Gravity.BOTTOM,0,0)


    }

}