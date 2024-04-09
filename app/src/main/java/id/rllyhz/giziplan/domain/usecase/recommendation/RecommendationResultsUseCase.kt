package id.rllyhz.giziplan.domain.usecase.recommendation

import id.rllyhz.giziplan.domain.model.RecommendationResultModel
import id.rllyhz.giziplan.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface RecommendationResultsUseCase {
    suspend fun getAllRecommendationResults(): Flow<DataState<List<RecommendationResultModel>>>

    suspend fun deleteRecommendationResultsOf(resultId: Int): Flow<DataState<Boolean>>
}