package id.rllyhz.giziplan.utils

fun String.toIntList(delimiter: String = ","): List<Int> =
    split(delimiter).map {
        if (it.isEmpty()) -1 else it.toInt()
    }.filter { it >= 0 }