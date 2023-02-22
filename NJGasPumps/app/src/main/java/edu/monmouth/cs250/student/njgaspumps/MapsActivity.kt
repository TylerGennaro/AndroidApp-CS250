package edu.monmouth.cs250.student.njgaspumps


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import edu.monmouth.cs250.student.njgaspumps.databinding.ActivityMapsBinding
import kotlin.math.round


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private val LOCATIONREQUESTCODE = 101  //code for location permission request response
    private var locationAccess = false

    private lateinit var binding: ActivityMapsBinding

    private var njPumps = mutableListOf<Pump>()
    private var zoomLevel = 12.0f
    // WLB location

    private val muCampus = LatLng(40.2790893, -74.0054)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get data from model
        njPumps = Pump.getPumpsFromFile("njgasstations.json", this)
        Log.i("Main Activity","Total Pumps: ${njPumps.size}")

        // GET PERMISSION from manifest. If not granted, go thru requesting permission.
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            locationAccess = true
            showMapFragment()
        }
        else {
            requestPermission( Manifest.permission.ACCESS_FINE_LOCATION, LOCATIONREQUESTCODE)
        }

        title = "New Jersey Gas Pumps"
    }

    private fun showMapFragment() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.maps_menu, menu)
        MenuCompat.setGroupDividerEnabled(menu, true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.streetMap -> mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.satellieMap -> mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            R.id.all_pumps, R.id.nearby_pumps -> {
                when (item.itemId) {
                    R.id.all_pumps -> {
                        mMap.clear()
                        showPumps(mMap, null)
                    }
                    R.id.nearby_pumps -> {
                        mMap.clear()
                        showPumps(mMap, 5.0)
                    }
                }
                item.isChecked = true
            }
        }

        return true
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val mapSettings = mMap.uiSettings
        mapSettings.isZoomControlsEnabled = true
        mapSettings.isMapToolbarEnabled = true
        mapSettings.isCompassEnabled = true

        // Enable Map to show user location

        // show the parks
        showPumps(mMap, null)

        // Set up the activity to conform to click on map info window

        mMap.setOnInfoWindowClickListener (this)
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    // show the parks

    private fun showPumps (mMap: GoogleMap, range: Double?) {
        // Add a marker for MU
        mMap.addMarker(MarkerOptions().position(muCampus).title("Monmouth University")
            .snippet("West Long Branch, NJ")
            .icon(bitmapDescriptorFromVector(this, R.drawable.monmouth)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(muCampus))


        for (pump in njPumps) {
            val pumpLatLong = LatLng(pump.lat, pump.long)

            Log.i("Main Activity", pump.name)

            val distance = FloatArray(2)

            Location.distanceBetween(muCampus.latitude, muCampus.longitude, pumpLatLong.latitude,
                pumpLatLong.longitude, distance)

            val distanceInMiles = round ( (distance[0]/1609.334) * 100.00) / 100

            if (range != null && distanceInMiles > range) {
                continue
                // We do not want to display this pump since it is out of range
            }

            val snippetInfo = pump.township + " -> " + distanceInMiles.toString() + " miles"

            var icon = if (pump.name.lowercase().contains("shell")) {
                bitmapDescriptorFromVector(this, R.drawable.shell_station)
            } else if (pump.name.lowercase().contains("lukoil")) {
                bitmapDescriptorFromVector(this, R.drawable.ic_lukoil)
            } else {
                bitmapDescriptorFromVector(this, R.drawable.generic_gas_station)
            }

            val pumpMarker = mMap.addMarker(MarkerOptions().position(pumpLatLong).title(pump.name)
                .snippet(snippetInfo)
                .icon(icon))
            pumpMarker?.tag = pump.pumpID
        }

    }

    // map info window listener

    override fun onInfoWindowClick(p0: Marker) {
        val loc = p0.position
        val lat = loc.latitude
        val lng = loc.longitude
        val gmapQuery = "google.navigation:q=" + lat.toString() + "," + lng.toString()
        Log.i("Main Activity", gmapQuery)

        val gmmIntentUri = Uri.parse(gmapQuery)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    // request permission

    private fun requestPermission(permissionType: String, requestCode: Int) {
        ActivityCompat.requestPermissions(this,
            arrayOf(permissionType), requestCode )
    }

    // permission response

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATIONREQUESTCODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "Unable to show location - permission required",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    locationAccess = true
                    showMapFragment()
                }
            }
        }
    }
}