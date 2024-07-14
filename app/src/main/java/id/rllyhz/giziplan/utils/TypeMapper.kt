package id.rllyhz.giziplan.utils

import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

fun String.toIntList(delimiter: String = ","): List<Int> =
    split(delimiter).map {
        if (it.isEmpty()) -1 else it.toInt()
    }.filter { it >= 0 }

fun Long.toDateNumericalString(): String {
    // Convert Long to LocalDateTime
    val instant = Instant.ofEpochMilli(this)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    // Format LocalDateTime to String
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

    return localDateTime.format(formatter)
}

fun String.toDateString(): String {
    var result = ""

    try {
        // Example: "14-07-2024 15:15:10"
        val datesAndTimes = split(" ")
        val dates = datesAndTimes[0]
        val times = datesAndTimes[1]

        val tempDates = dates.split("-")
        val tempTimes = times.split(":")

        val day = tempDates[0]
        val month = parseMonth(tempDates[1])
        val year = tempDates[2]
        val hour = tempTimes[0]
        val minute = tempTimes[1]
        val second = tempTimes[2]

        result = "$day $month $year $hour:$minute:$second"
        //
    } catch (e: Exception) {
        e.printStackTrace()
        result = "Invalid Numerical Date string"
    }

    return result
}

private fun parseMonth(month: String) =
    when (month) {
        "02" -> "Februari"
        "03" -> "Maret"
        "04" -> "April"
        "05" -> "Mei"
        "06" -> "Juni"
        "07" -> "Juli"
        "08" -> "Agustus"
        "09" -> "September"
        "10" -> "Oktober"
        "11" -> "November"
        "12" -> "Desember"
        else -> "Jan"
    }
