package id.rllyhz.giziplan.domain.usecase.recommendation

import id.rllyhz.giziplan.domain.model.MeasureResultModel
import id.rllyhz.giziplan.domain.repository.DatabaseRepository
import id.rllyhz.giziplan.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

class MeasureResultsInteractor(
    private val repository: DatabaseRepository
) : MeasureResultsUseCase {
    override suspend fun insertNewMeasureResults(newMeasureResult: MeasureResultModel) =
        repository.insertNewMeasureResult(newMeasureResult)

    override suspend fun getAllMeasureResults(): Flow<DataState<List<MeasureResultModel>>> =
        repository.getAllMeasureResults()

    override suspend fun deleteMeasureResultOf(resultId: Int): Flow<DataState<Boolean>> =
        repository.deleteMeasureResultOf(resultId)

    override suspend fun deleteAllMeasureResult(): Flow<DataState<Boolean>> =
        repository.deleteAllMeasureResults()
}