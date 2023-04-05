package com.example.kite.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateAndTime {
    val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
}