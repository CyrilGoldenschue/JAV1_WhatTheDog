package com.jav1.whatthedog

import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

/**
 * Created by Mathieu RABOT
 * 21.06.2022
 * This class is used to create a marker on the map
 * using the GeoPoint class, the Marker class and the MapView class from OSMdroid
 * For the project what-the-dog JAV1
 */
class MarkerManager {
    companion object {
        /**
         * Creates a marker on a specific GeoPoint.
         * @param {GeoPoint} point The GeoPoint to create the marker on.
         * @param {MapView} mapView The MapView to create the marker on.
         * @param {String} title The title of the marker.
         * @param {String} snippet The snippet of the marker.
         */
        fun addMarker(mapView: MapView, geoPoint: GeoPoint, title: String, snippet: String) {
            val marker = Marker(mapView)
            marker.position = geoPoint
            marker.title = title
            marker.snippet = snippet
            mapView.overlays.add(marker)
        }
    }
}