package id.rllyhz.giziplan.data.anthropometry.core

import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryDataTable

interface AnthropometryDao {
    suspend fun getWeightToAgeDataTable(): AnthropometryDataTable

    suspend fun getHeightToAgeDataTable(): AnthropometryDataTable

    suspend fun getWeightToHeightLessThan24DataTable(): AnthropometryDataTable

    suspend fun getWeightToHeightGreaterThan24DataTable(): AnthropometryDataTable
}