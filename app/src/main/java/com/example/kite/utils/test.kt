package com.example.kite.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val currentDate = LocalDateTime.now().format(dateFormatter)
    val currentTime = LocalDateTime.now().format(timeFormatter)
}