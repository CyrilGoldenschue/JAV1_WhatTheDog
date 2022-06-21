package com.jav1.whatthedog

import android.view.MotionEvent
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Overlay

/**
 * Created by Mathieu RABOT
 * 21.06.2022
 * This class is used handle the events on the map that use markers
 * using the GeoPoint class, the Overlay class and the MapView class from OSMdroid
 * For the project what-the-dog JAV1
 */
class MarkerCreatorOverlay(private val startPoint: GeoPoint) : Overlay() {

}
