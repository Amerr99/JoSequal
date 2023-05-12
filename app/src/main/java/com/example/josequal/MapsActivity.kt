package com.example.josequal

import android.Manifest
import android.app.Dialog
import android.content.ContentProviderClient
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog.Locations
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.josequal.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.CircleOptions
//
//class CustomDialog(
//    context: Context,
//    private val title: String,
//    private val message: String
//) : Dialog(context) {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.dialog_layout)
//
//        // Set the title and message of the dialog
//        findViewById<TextView>(R.id.titleTextView).text = title
//        findViewById<TextView>(R.id.messageTextView).text = message
//
//        // Set the click listener for the close button
//        findViewById<Button>(R.id.closeButton).setOnClickListener {
//            dismiss()
//        }
//    }
//
//}


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val permissionCode =101






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        getCurrentLocationForUser()
    }
    private fun getCurrentLocationForUser(){
        if (ActivityCompat.checkSelfPermission(
                this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!=
                    PackageManager.PERMISSION_GRANTED
                    ){
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),permissionCode
            )
            return
        }
        val getLocation = fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            location ->
            if (location !=null){
currentLocation =location
                Toast.makeText(applicationContext,currentLocation.latitude.toString() + "" +
                        currentLocation.longitude.toString(),Toast.LENGTH_LONG).show()

                val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)

            }
            
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0]==
                    PackageManager.PERMISSION_GRANTED){
                getCurrentLocationForUser()
            }

        }
    }




    override fun onMapReady(googleMap: GoogleMap) {

//        val viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
//
//        viewModel.markerList.observe(this, { markers ->
//            googleMap.clear()
//            markers.forEach { marker ->
//                googleMap.addMarker(marker).apply {
//                    setOnMarkerClickListener {
//                        // Create and show the dialog
//                        CustomDialog(this@MapActivity, "Marker Clicked", "You clicked on a marker").show()
//
//                        true
//                    }
//                }
//            }
//        })
//
//        viewModel.loadMarkers()



        val amman = LatLng(31.9539,35.9106)
        val irbid = LatLng(32.5568,35.8469)
        val karak = LatLng(31.1853,35.7048)
        val marker1 = MarkerOptions().position(amman).title("Amman")
        val marker2= MarkerOptions().position(irbid).title("Irbid")
        val marker3 = MarkerOptions().position(karak).title("Alkarak")


        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val marker = MarkerOptions().position(latLng).title("You are here")
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(amman))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(amman,7f))
        googleMap?.addMarker(marker)
        googleMap?.addMarker(marker1)
        googleMap?.addMarker(marker2)
        googleMap?.addMarker(marker3)



    }
}