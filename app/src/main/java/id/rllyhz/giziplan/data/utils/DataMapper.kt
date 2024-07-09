package id.rllyhz.giziplan.data.utils

fun String.toAgeCategory(): String = when {
    lowercase() == "6-8 bulan" -> "A"
    lowercase() == "9-11 bulan" -> "B"
    lowercase() == "12-23 bulan" -> "C"
    lowercase() == "24-59 bulan" -> "D"
    else -> "A"
}