package id.rllyhz.giziplan.data

import id.rllyhz.giziplan.data.anthropometry.AnthropometryDataSource
import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryTables
import id.rllyhz.giziplan.data.anthropometry.model.PopulationRow
import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.domain.repository.AnthropometryRepository
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class AnthropometryRepositoryImpl(
    private val anthropometryDataSource: AnthropometryDataSource,
    private val ioDispatcher: CoroutineContext,
) : AnthropometryRepository {

    override suspend fun getAllAnthropometryTables(): AnthropometryTables =
        withContext(ioDispatcher) {
            anthropometryDataSource.getAnthropometryTables()
        }

    override suspend fun getWeightToAgePopulation(gender: Gender): List<PopulationRow> =
        withContext(ioDispatcher) {
            when (gender) {
                Gender.Male -> anthropometryDataSource.getWeightToAgeDataTable().malePopulationTable
                else -> anthropometryDataSource.getWeightToAgeDataTable().femalePopulationTable
            }
        }

    override suspend fun getHeightToAgePopulation(gender: Gender): List<PopulationRow> =
        withContext(ioDispatcher) {
            when (gender) {
                Gender.Male -> anthropometryDataSource.getHeightToAgeDataTable().malePopulationTable
                else -> anthropometryDataSource.getHeightToAgeDataTable().femalePopulationTable
            }
        }

    override suspend fun getWeightToHeightPopulation(gender: Gender): List<PopulationRow> =
        withContext(ioDispatcher) {
            when (gender) {
                Gender.Male -> anthropometryDataSource.getWeightToHeightDataTable().malePopulationTable
                else -> anthropometryDataSource.getWeightToHeightDataTable().femalePopulationTable
            }
        }
}