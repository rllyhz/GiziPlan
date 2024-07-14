package id.rllyhz.giziplan.domain.usecase.recommendation

import id.rllyhz.giziplan.domain.model.MeasureResultModel
import id.rllyhz.giziplan.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface MeasureResultsUseCase {
    suspend fun insertNewMeasureResults(newMeasureResult: MeasureResultModel)

    suspend fun getAllMeasureResults(): Flow<DataState<List<MeasureResultModel>>>

    suspend fun deleteMeasureResultOf(resultId: Int): Flow<DataState<Boolean>>

    suspend fun deleteAllMeasureResult(): Flow<DataState<Boolean>>
}