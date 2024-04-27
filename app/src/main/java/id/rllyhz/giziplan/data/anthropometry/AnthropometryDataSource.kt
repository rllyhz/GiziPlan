package id.rllyhz.giziplan.data.anthropometry

import id.rllyhz.giziplan.data.anthropometry.core.AnthropometryDao
import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryDataTable
import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryTables

class AnthropometryDataSource(
    private val dao: AnthropometryDao,
) {
    suspend fun getWeightToAgeDataTable(): AnthropometryDataTable =
        dao.getWeightToAgeDataTable()

    suspend fun getHeightToAgeDataTable(): AnthropometryDataTable =
        dao.getHeightToAgeDataTable()

    suspend fun getWeightToHeightLessThan24DataTable(): AnthropometryDataTable =
        dao.getWeightToHeightLessThan24DataTable()

    suspend fun getWeightToHeightGreaterThan24DataTable(): AnthropometryDataTable =
        dao.getWeightToHeightGreaterThan24DataTable()
}