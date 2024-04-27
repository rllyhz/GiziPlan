package id.rllyhz.giziplan.io

import android.content.Context

class RawFilesReader(
    private val context: Context
) : FileReader {
    override fun readFile(filename: String): String {
        return ""
    }

    override fun readFileByResId(resId: Int): String {
        val inputStream = context.resources.openRawResource(resId)
        return inputStream.bufferedReader().readText().trimIndent()
    }
}