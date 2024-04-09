package id.rllyhz.giziplan.data

import id.rllyhz.giziplan.data.local.LocalDataSource
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.model.RecommendationResultModel
import id.rllyhz.giziplan.domain.repository.GiziRepository
import id.rllyhz.giziplan.domain.utils.DataState
import id.rllyhz.giziplan.domain.utils.toModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GiziRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val dispatchers: Dispatchers
) : GiziRepository {
    override suspend fun getAllMenus(): Flow<DataState<List<MenuModel>>> =
        flow {
            emit(DataState.Loading())

            val menus = localDataSource.getAllMenus().toModels()

            emit(DataState.Success(menus))

        }.flowOn(dispatchers.IO)

    override suspend fun insertAllMenus(menus: List<MenuModel>): Flow<DataState<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMenuById(menuId: Int): Flow<DataState<MenuModel?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRecommendationResults(): Flow<DataState<List<RecommendationResultModel>>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecommendationResultOf(resultId: Int): Flow<DataState<Boolean>> {
        TODO("Not yet implemented")
    }
}