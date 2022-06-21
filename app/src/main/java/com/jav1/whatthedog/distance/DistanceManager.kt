package com.jav1.whatthedog.distance

import org.osmdroid.util.GeoPoint
import kotlin.math.roundToInt

/**
 * Created by Mathieu RABOT
 * 21.06.2022
 * This class is used to calculate the distance and price between two points
 * using the GeoPoint class and the MapView class from OSMdroid
 * For the project what-the-dog JAV1
 */
class DistanceManager {

    companion object {
        //Price per kilometer
        private const val PRICE_PER_KM = 0.05
        private const val KILOMETER_IN_METER = 1000.0

        /**
         * This function return the correct text format
         */
        fun getInfos(point1: GeoPoint, point2: GeoPoint): String {
            return if (getDistance(point1, point2) >= KILOMETER_IN_METER) {
                "Distance : ${roundTo(getDistance(point1, point2) / KILOMETER_IN_METER)} km\n" +
                        "Price : ${getPrice(point1,point2)}"
            } else {
                "Distance : ${getDistance(point1, point2)} m\n" +
                        "Price : ${getPrice(point1,point2)}"
            }
        }

        /**
         * This function is used to calculate the distance between two points
         * @param {GeoPoint} startPoint the first point
         * @param {GeoPoint} endPoint the second point
         * @return the distance in meters
         */
        private fun getDistance(point1: GeoPoint, point2: GeoPoint): Double {
            return roundTo(point1.distanceToAsDouble(point2))
        }

        /**
         * This function is used to calculate the price between two points
         */
        private fun getPrice(point1: GeoPoint, point2: GeoPoint): Double {
            return roundTo(getDistance(point1, point2) * PRICE_PER_KM)
        }

        /**
         * Round a double to a certain number of decimal places
         */
        private fun roundTo(value: Double): Double {
            return (value * 100.0).roundToInt() / 100.0
        }
    }
}