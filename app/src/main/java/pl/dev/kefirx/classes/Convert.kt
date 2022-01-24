package pl.dev.kefirx.classes

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlin.math.roundToInt

object Convert {

    val monthMap: Map<String, String> = mapOf(
        "1" to "sty",
        "2" to "lut",
        "3" to "mar",
        "4" to "kwi",
        "5" to "maj",
        "6" to "cze",
        "7" to "lip",
        "8" to "sie",
        "9" to "wrz",
        "10" to "paź",
        "11" to "lis",
        "12" to "gru"
    )
    val monthFullMap: Map<String, String> = mapOf(
        "1" to "styczeń",
        "2" to "luty",
        "3" to "marzec",
        "4" to "kwiecień",
        "5" to "maj",
        "6" to "czerwiec",
        "7" to "lipiec",
        "8" to "sierpień",
        "9" to "wrzesień",
        "10" to "październik",
        "11" to "listopad",
        "12" to "grudzień"
    )


    fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }


    private fun makeTimeString(hour: Int, min: Int, sec: Int): String =
        String.format("%02d:%02d:%02d", hour, min, sec)

}