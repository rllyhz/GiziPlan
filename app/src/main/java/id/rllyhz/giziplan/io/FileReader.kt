package id.rllyhz.giziplan.io

interface FileReader {
    fun readFile(filename: String): String

    fun readFileByResId(resId: Int): String
}