package id.rllyhz.giziplan.domain.repository

import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.model.RecommendationResultModel
import id.rllyhz.giziplan.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun getAllMenus(): Flow<DataState<List<MenuModel>>>

    suspend fun insertAllMenus(menus: List<MenuModel>): Flow<DataState<Boolean>>

    suspend fun deleteAllMenus(): Flow<DataState<Boolean>>

    suspend fun getMenuById(menuId: Int): Flow<DataState<MenuModel?>>

    suspend fun getAllRecommendationResults(): Flow<DataState<List<RecommendationResultModel>>>

    suspend fun deleteRecommendationResultOf(resultId: Int): Flow<DataState<Boolean>>

    suspend fun deleteAllRecommendationResults(): Flow<DataState<Boolean>>
}