package id.rllyhz.giziplan.utils

fun <T> List<T>.takeRandomly(count: Int = 4): List<T> {

    val result = arrayListOf<T>()

    for (i in 0..<count) {
        val index = (indices).random()
        result.add(this[index])
    }

    return result
}