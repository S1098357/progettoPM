package com.example.myapplication

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.PopupWindow
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.BarraBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.IOException
import android.view.MenuInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils.*
import android.widget.ImageButton
import android.widget.Toast

private lateinit var mMap: GoogleMap
private lateinit var binding2: BarraBinding




class mappa : Fragment(), OnMapReadyCallback{
    private lateinit var searchView: SearchView
    lateinit var button: TextView
    lateinit var main: LinearLayout
    lateinit var numInquilini :ImageButton
    lateinit var info:Array<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root= inflater.inflate(R.layout.fragment_mappa, container, false)
        searchView = root.findViewById(R.id.idSearchView)
        button = root.findViewById(R.id.filters)
        main=root.findViewById(R.id.main)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment :SupportMapFragment=childFragmentManager.findFragmentById(R.id.cartina) as SupportMapFragment
        binding2 = BarraBinding.inflate(layoutInflater)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                val location = searchView.query.toString()
                var addressList: List<Address> = mutableListOf()
                if ( location==null||location=="") {
                    Toast.makeText(context,"nessuna località inserita",Toast.LENGTH_SHORT).show()
                }
                else{
                    val geocoder = Geocoder(context!!)
                    try {
                        addressList = geocoder.getFromLocationName(location, 1) as List<Address>
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                        if (addressList.size==0)
                        {
                            Toast.makeText(context,"La località inserita non è valida",Toast.LENGTH_SHORT).show()
                        }else {
                            val address: Address = addressList[0]
                            val latLng = LatLng(address.latitude, address.longitude)
                            mMap.addMarker(MarkerOptions().position(latLng).title(location))
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10F))
                        }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        mapFragment.getMapAsync(this)

        info=Array(3){""}
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick (view:View?) {
                val layout = PopupWindow(context)
                val view = layoutInflater.inflate(R.layout.filtri, null)
                layout.contentView = view
                val filtri = view.findViewById<ConstraintLayout>(R.id.filtri)
                numInquilini=filtri.findViewById(R.id.numInquilini)
                val seek = filtri.findViewById<SeekBar>(R.id.seekBar)
                val prezzo = filtri.findViewById<TextView>(R.id.prezzo)
                seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onStopTrackingTouch(p0: SeekBar?) {
                        val stringa=prezzo.text.toString()
                        stringa.removeSuffix(" €")
                        info[0]=stringa
                    }

                    override fun onProgressChanged(
                        seekbar: SeekBar?,
                        progress: Int,
                        p2: Boolean
                    ) {
                        val stringaPrezzo = progress.toString() + " €"
                        prezzo.text = stringaPrezzo

                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {

                    }
                })
                val distanza = filtri.findViewById<TextView>(R.id.distanza)
                val seek2 = filtri.findViewById<SeekBar>(R.id.seekBar3)
                seek2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onStopTrackingTouch(p0: SeekBar?) {
                        val stringa=distanza.text.toString()
                        stringa.removeSuffix(" Km")
                        info[1]=stringa
                    }

                    override fun onProgressChanged(
                        seekbar: SeekBar?,
                        progress: Int,
                        p2: Boolean
                    ) {
                        val stringa = progress.toString() + " Km"
                        distanza.text = stringa
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {
                    }

                })

                val popupMenu = PopupMenu(context,numInquilini)
                val numScelto=view.findViewById<TextView>(R.id.numScelto)
                popupMenu.menuInflater.inflate(R.menu.menu,popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {
                    val id=it.itemId
                    when(id){
                        R.id.menu_item_1->{
                            numScelto.text=" 1"
                            info[2]="1"
                        }
                        R.id.menu_item_2->{
                            numScelto.text=" 2"
                            info[2]="2"
                        }
                        R.id.menu_item_3->{
                            numScelto.text=" 3"
                            info[2]="3"
                        }
                        R.id.menu_item_4->{
                            numScelto.text=" 4"
                            info[2]="4"
                        }
                        R.id.menu_item_5->{
                            numScelto.text=" 5"
                            info[2]="5"
                        }
                        R.id.menu_item_6->{
                            numScelto.text=" 6"
                            info[2]="6"
                        }
                        R.id.menu_item_7->{
                            numScelto.text=" 7"
                            info[2]="7"
                        }
                        R.id.menu_item_8->{
                            numScelto.text=" 8"
                            info[2]="8"
                        }
                        R.id.menu_item_9->{
                            numScelto.text=" 9"
                            info[2]="9"
                        }
                    }
                    false
                }
                numInquilini.setOnClickListener{popupMenu.show()}
                val applica=filtri.findViewById<Button>(R.id.applica)
                applica.setOnClickListener{
                    layout.dismiss()
                    val intent= Intent(context, MapsActivity::class.java)
                    intent.putExtra("info",info)
                    startActivity(intent)
                }

                layout.animationStyle= R.style.Animation
                layout.height = main.height
                layout.width = main.width
                layout.showAtLocation(main, Gravity.BOTTOM, 0, 0)
                view.setOnClickListener {
                    layout.dismiss()
                }
            }
        })

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
}