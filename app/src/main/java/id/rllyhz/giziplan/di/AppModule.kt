package id.rllyhz.giziplan.di

import android.content.Context
import id.rllyhz.giziplan.data.DatabaseRepositoryImpl
import id.rllyhz.giziplan.data.anthropometry.AnthropometryDataSource
import id.rllyhz.giziplan.data.anthropometry.core.AnthropometryCSV
import id.rllyhz.giziplan.data.anthropometry.core.AnthropometryDao
import id.rllyhz.giziplan.data.local.LocalDataSource
import id.rllyhz.giziplan.data.local.db.GiziDao
import id.rllyhz.giziplan.data.local.db.GiziDatabase
import id.rllyhz.giziplan.domain.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers

class AppModule(
    private val context: Context
) {
    private fun getAnthropometryCSV(): AnthropometryCSV =
        AnthropometryCSV(context)

    private fun getAnthropometryDao(): AnthropometryDao =
        getAnthropometryCSV().getDao()

    fun getAnthropometryDataSource(): AnthropometryDataSource =
        AnthropometryDataSource(
            getAnthropometryDao()
        )

    private fun getDatabaseInstance(): GiziDatabase =
        GiziDatabase.getInstance(context)

    private fun getDatabaseDao(): GiziDao =
        getDatabaseInstance().getDao()

    private fun getLocalDataSource(): LocalDataSource =
        LocalDataSource(getDatabaseDao())

    fun getMainRepository(): DatabaseRepository =
        DatabaseRepositoryImpl(
            getLocalDataSource(),
            Dispatchers.IO
        )
}