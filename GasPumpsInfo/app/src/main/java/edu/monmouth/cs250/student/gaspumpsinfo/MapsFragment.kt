package edu.monmouth.cs250.student.gaspumpsinfo

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.math.round

class MapsFragment : Fragment() {

    private lateinit var mMap: GoogleMap

    private var zoomLevel = 13.0f
    private val muCampus = LatLng(40.2790893, -74.0054)
    private var locationAccess = false
    private val LOCATIONREQUESTCODE = 101  //code for location permission request response
    private var njPumps = mutableListOf<Pump>()
    private lateinit var ctx: Context

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        mMap = googleMap

        Log.i("Maps Activity", "\n\nMap is ready\n\n")

        // get data from model
        njPumps = Pump.getPumpsFromFile("njgasstations.json", ctx)
        Log.i("Maps Activity","Total Pumps: ${njPumps.size}")

        showPumps(mMap, 5.0) // Set to null to see all pumps

        // Add a marker for MU
        mMap.addMarker(MarkerOptions().position(muCampus).title("Monmouth University")
            .snippet("West Long Branch, NJ")
            .icon(bitmapDescriptorFromVector(ctx, R.drawable.monmouth)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(muCampus))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(muCampus, zoomLevel))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ctx = this.requireContext()
        // GET PERMISSION from manifest. If not granted, go thru requesting permission.
        val permission = ContextCompat.checkSelfPermission(
            ctx,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        Log.i("Maps Fragment", "Checking permission")
        if (permission == PackageManager.PERMISSION_GRANTED) {
            locationAccess = true
            showMapFragment()
        } else {
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATIONREQUESTCODE)
            Log.i("Maps Fragment", "Permission denied")
        }
    }

    private fun showMapFragment() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        Log.i("Maps Fragment", "Permission granted")
    }

    // request permission

    private fun requestPermission(permissionType: String, requestCode: Int) {
        ActivityCompat.requestPermissions(this.requireActivity(),
            arrayOf(permissionType), requestCode )
        Log.i("Maps Fragment", "Requested Permission")
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    private fun showPumps (mMap: GoogleMap, range: Double?) {
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
                bitmapDescriptorFromVector(ctx, R.drawable.shell_station)
            } else if (pump.name.lowercase().contains("lukoil")) {
                bitmapDescriptorFromVector(ctx, R.drawable.ic_lukoil)
            } else {
                bitmapDescriptorFromVector(ctx, R.drawable.generic_gas_station)
            }

            val pumpMarker = mMap.addMarker(MarkerOptions().position(pumpLatLong).title(pump.name)
                .snippet(snippetInfo)
                .icon(icon))
            pumpMarker?.tag = pump.pumpID
        }

    }

    // permission response

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i("Maps Fragment", "Received Permission Results")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATIONREQUESTCODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        ctx,
                        "Unable to show location - permission required",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    locationAccess = true
                    showMapFragment()
                    Log.i("Maps Fragment", "Permission granted. Showing Map.")
                }
            }
        }
    }
}