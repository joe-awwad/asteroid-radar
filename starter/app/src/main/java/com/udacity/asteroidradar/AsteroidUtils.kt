package com.udacity.asteroidradar

import java.text.SimpleDateFormat
import java.util.*

fun currentDay() = getCurrentDayFormattedDate()

fun plusDays(days: Int) = getFutureDayFormattedDate(plusDays = days)

fun minusDays(days: Int) = getFutureDayFormattedDate(plusDays = -days)

private fun getFutureDayFormattedDate(plusDays: Int): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, plusDays)

    return SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        .format(calendar.time)
}

private fun getCurrentDayFormattedDate(): String {
    val calendar = Calendar.getInstance()

    return SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        .format(calendar.time)
}