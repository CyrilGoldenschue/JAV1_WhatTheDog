package com.jav1.whatthedog.polyline

import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline

/**
 * Created by Mathieu RABOT
 * 21.06.2022
 * This class is used to create a line on the map
 * using the GeoPoint class, the Polyline class and the MapView class from OSMdroid
 * For the project what-the-dog JAV1
 */
class PolylineManager {
    companion object {
        /**
         * This function is used to create a line on the map
         * @param {MapView} mapView the mapView where the line will be created
         * @param {List<GeoPoint>}points the list of points that will be used to create the line
         */
        fun addPolyline(mapView: MapView, start: GeoPoint, end: GeoPoint) {
            val polyline = Polyline()
            polyline.addPoint(start)
            polyline.addPoint(end)
            mapView.overlays.add(polyline)
            mapView.invalidate()
        }
    }
}