package id.rllyhz.giziplan.utils.fakes

import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryDataTable
import id.rllyhz.giziplan.data.anthropometry.model.PopulationRow
import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.domain.repository.AnthropometryRepository

class FakeRepository(
    private val weightToAgeDataTable: AnthropometryDataTable,
    private val heightToAgeDataTable: AnthropometryDataTable,
    private val weightToHeightLessThan24DataTable: AnthropometryDataTable,
    private val weightToHeightGreaterThan24DataTable: AnthropometryDataTable
) : AnthropometryRepository {
    override suspend fun getWeightToAgePopulation(gender: Gender): List<PopulationRow> =
        if (gender == Gender.Male) weightToAgeDataTable.malePopulationTable
        else weightToAgeDataTable.femalePopulationTable

    override suspend fun getHeightToAgePopulation(gender: Gender): List<PopulationRow> =
        if (gender == Gender.Male) heightToAgeDataTable.malePopulationTable
        else heightToAgeDataTable.femalePopulationTable

    override suspend fun getWeightToHeightLessThan24Population(gender: Gender): List<PopulationRow> =
        if (gender == Gender.Male) weightToHeightLessThan24DataTable.malePopulationTable
        else weightToHeightLessThan24DataTable.femalePopulationTable

    override suspend fun getWeightToHeightGreaterThan24Population(gender: Gender): List<PopulationRow> =
        if (gender == Gender.Male) weightToHeightGreaterThan24DataTable.malePopulationTable
        else weightToHeightGreaterThan24DataTable.femalePopulationTable
}