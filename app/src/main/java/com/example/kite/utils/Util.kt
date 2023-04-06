package com.example.kite.utils

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Util {
    fun getDateFromTimeString(
        millis: Long,
        expectedFormat: String = "yyyy-MM-dd"
    ): String {
        val sdf: DateFormat = SimpleDateFormat(expectedFormat, Locale.getDefault())
        val date: Date?
        var dateString = ""
        try {
            date = Date(millis)
            dateString = sdf.format(date)
        } catch (e: Exception) {
            e.stackTrace
        }
        Log.e("Time", dateString)
        return dateString
    }

    fun getMillisFromTime(
        timeString: String,
        timeformat: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    ): Long {
        val sdf: DateFormat = SimpleDateFormat(timeformat, Locale.getDefault())
        val date: Date?
        var millis = 0L
        try {
            date = sdf.parse(timeString)
            millis = date.time
        } catch (e: Exception) {
            e.stackTrace
        }
        return millis
    }
}