package id.rllyhz.giziplan.domain.usecase.recommendation

import id.rllyhz.giziplan.domain.model.RecommendationResultModel
import id.rllyhz.giziplan.domain.repository.DatabaseRepository
import id.rllyhz.giziplan.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

class RecommendationResultsInteractor(
    private val repository: DatabaseRepository
) : RecommendationResultsUseCase {
    override suspend fun getAllRecommendationResults(): Flow<DataState<List<RecommendationResultModel>>> =
        repository.getAllRecommendationResults()

    override suspend fun deleteRecommendationResultsOf(resultId: Int): Flow<DataState<Boolean>> =
        repository.deleteRecommendationResultOf(resultId)
}