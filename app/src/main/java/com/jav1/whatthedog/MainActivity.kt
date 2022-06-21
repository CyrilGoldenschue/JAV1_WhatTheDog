package com.jav1.whatthedog

import android.Manifest.permission.*
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView


import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay

private lateinit var map : MapView
private lateinit var textView : TextView
private lateinit var rotation : RotationGestureOverlay
//Default Sainte-croix location
private var latitude : Double = 46.82292
private var longitude : Double = 6.50133
private var startPoint : GeoPoint = GeoPoint(latitude, longitude)
private var startTitle : String = "Mr. Viret's position"
private var startDescription : String = "This is the start point where the calculation will start"
private var permissions = arrayOf(
    WRITE_EXTERNAL_STORAGE,
    ACCESS_COARSE_LOCATION,
    ACCESS_NETWORK_STATE,
    ACCESS_WIFI_STATE,
    INTERNET
)

/**
 * Main activity for the app.
 * @author Mathieu RABOT
 * @version 1.0
 * This project is created for the purpose of learning Android development.
 * By adding a map to an application by using the Osmdroid library
 */
class MainActivity : AppCompatActivity() {
    /**
     * Called when the activity is first created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Request permissions if not granted
        requestPermissionsIfNecessary(permissions)

        //Inflate and create the map
        val context = this.applicationContext
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))

        //Capture components from the layout
        map = findViewById(R.id.map)
        textView = findViewById(R.id.text)

        //Set the map view
        rotation = RotationGestureOverlay(map)

        configureMapWithDefaultParameters(map, rotation)

        //Create a marker to the default location
        val marker = MarkerCreatorOverlay(startPoint)
        map.overlays.add(marker)
        MarkerManager.addMarker(map, startPoint, startTitle, startDescription)

        //Add compass on the map
        val compass: CompassOverlay = CompassOverlay(this, map)
        compass.enableCompass()

        //Add a listener to the map
        configureMapListeners(context,textView)

        //Add components to the map overlay
        map.overlays.add(compass)
        map.overlays.add(rotation)
    }

    /**
     * Resumes the map.
     */
    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    /**
     * Pauses the map.
     */
    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    /**
     * Map configuration
     * @param {Mapview} map used
     * @param {RotationGestureOverlay} rotation used
     */
    private fun configureMapWithDefaultParameters(currentMap: MapView, currentRotation: RotationGestureOverlay) {
        currentMap.setTileSource(TileSourceFactory.MAPNIK)

        currentRotation.isEnabled = true

        currentMap.zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
        currentMap.setMultiTouchControls(true)

        currentMap.controller.setZoom(18.0)
        currentMap.controller.setCenter(startPoint)

        map = currentMap
        rotation = currentRotation
    }

    /**
     * Define map listeners
     * @param {Context} context used
     * @param {TextView} textView used
     */
    private fun configureMapListeners(context: Context, textView: TextView) {
        map.addMapListener(object : MapListener {
            override fun onScroll(event: ScrollEvent): Boolean {
                updateInfo(context, textView)
                return true
            }

            override fun onZoom(event: ZoomEvent): Boolean {
                updateInfo(context, textView)
                return true
            }
        })
    }

    /**
     * update coordinates and zoom level
     * @param {Context} context used
     * @param {TextView} textView used
     */
    private fun updateInfo(context: Context, textView: TextView) {
        val center = map.mapCenter
        latitude = center.latitude
        longitude = center.longitude
        val textTest = context.resources.getString(R.string.distance) + "Latitude : $latitude\n Longitude : $longitude"
        textView.text = textTest
    }

    /**
     * Check if the app has permission to access fine location
     * @return true if the app has permission to access fine location
     * @param {Array<String>} permissions The permissions to check.
     * @param {Int} requestCode The request code for the permission.
     * @param {Int} grantResults The grant results for the permission.
     */
    private fun onRequestPermissionResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        val permissionsToRequest: ArrayList<String> = ArrayList()
        for (i in grantResults.indices) {
            permissionsToRequest.add(permissions[i])
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                requestCode
            )
        }
    }

    /**
     * Check if the app has permission to access fine location
     * @param {Array<String>} permissions The permissions to check.
     */
    private fun requestPermissionsIfNecessary(permissions: Array<String>) {
        val permissionsToRequest: ArrayList<String> = ArrayList()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(permission)
            }
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 0)
        }
    }

}