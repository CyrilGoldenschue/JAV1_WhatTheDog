package com.jav1.whatthedog

import android.Manifest.permission.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView


import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView

private lateinit var map : MapView
private lateinit var textView : TextView
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