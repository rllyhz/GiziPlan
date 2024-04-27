package id.rllyhz.giziplan.data.anthropometry

import id.rllyhz.giziplan.data.anthropometry.core.AnthropometryDao
import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryDataTable
import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryTables

class AnthropometryDataSource(
    private val dao: AnthropometryDao,
) {
    suspend fun getAnthropometryTables(): AnthropometryTables = AnthropometryTables(
        dao.getWeightToAgeDataTable(),
        dao.getHeightToAgeDataTable(),
        dao.getWeightToHeightDataTable(),
    )

    suspend fun getWeightToAgeDataTable(): AnthropometryDataTable =
        dao.getWeightToAgeDataTable()

    suspend fun getHeightToAgeDataTable(): AnthropometryDataTable =
        dao.getHeightToAgeDataTable()

    suspend fun getWeightToHeightDataTable(): AnthropometryDataTable =
        dao.getWeightToHeightDataTable()
}