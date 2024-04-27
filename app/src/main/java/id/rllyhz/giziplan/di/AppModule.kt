package id.rllyhz.giziplan.di

import android.content.Context
import id.rllyhz.giziplan.data.anthropometry.AnthropometryDataSource

class AppModule(
    private val context: Context
) {
    private fun getAnthropometryDataSource(): AnthropometryDataSource =
        AnthropometryDataSource(context)

    private fun getDatabaseInstance() {}

    private fun getLocalDataSource() {}

    private fun getMainRepository() {}
}