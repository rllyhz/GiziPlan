package id.rllyhz.giziplan.di

import android.content.Context
import id.rllyhz.giziplan.data.anthropometry.AnthropometryDataSource
import id.rllyhz.giziplan.data.anthropometry.core.AnthropometryCSV
import id.rllyhz.giziplan.data.anthropometry.core.AnthropometryDao

class AppModule(
    private val context: Context
) {
    private fun getAnthropometryCSV(): AnthropometryCSV =
        AnthropometryCSV(context)

    private fun getAnthropometryDao(): AnthropometryDao =
        getAnthropometryCSV().getDao()

    private fun getAnthropometryDataSource(): AnthropometryDataSource =
        AnthropometryDataSource(
            getAnthropometryDao()
        )

    private fun getDatabaseInstance() {}

    private fun getLocalDataSource() {}

    private fun getMainRepository() {}
}