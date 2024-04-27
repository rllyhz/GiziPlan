package id.rllyhz.giziplan.data

import id.rllyhz.giziplan.data.anthropometry.AnthropometryDataSource
import id.rllyhz.giziplan.data.local.LocalDataSource
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.model.RecommendationResultModel
import id.rllyhz.giziplan.domain.repository.GiziRepository
import id.rllyhz.giziplan.domain.utils.DataState
import id.rllyhz.giziplan.domain.utils.toEntities
import id.rllyhz.giziplan.domain.utils.toModel
import id.rllyhz.giziplan.domain.utils.toModels
import id.rllyhz.giziplan.domain.utils.toResultModels
import id.rllyhz.giziplan.utils.RepositoryIOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class GiziRepositoryImpl(
    private val anthropometryDataSource: AnthropometryDataSource,
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineContext,
    private val isOnTesting: Boolean = false,
) : GiziRepository {
    override suspend fun getAllMenus(): Flow<DataState<List<MenuModel>>> =
        flow {
            emit(DataState.Loading())

            try {
                val menus = localDataSource.getAllMenus().toModels()
                emit(DataState.Success(menus))
            } catch (e: Exception) {
                if (!isOnTesting) e.printStackTrace()
                emit(
                    DataState.Error(
                        RepositoryIOException(e.message.toString()),
                        "Something went wrong when retrieving all menus",
                    )
                )
            }
        }.flowOn(ioDispatcher)

    override suspend fun insertAllMenus(menus: List<MenuModel>): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading())

            try {
                localDataSource.insertAllMenus(menus.toEntities())
                emit(DataState.Success(true))
            } catch (e: Exception) {
                if (!isOnTesting) e.printStackTrace()
                emit(
                    DataState.Error(
                        RepositoryIOException(e.message.toString()),
                        "Something went wrong when inserting all menus",
                    )
                )
            }
        }.flowOn(ioDispatcher)

    override suspend fun getMenuById(menuId: Int): Flow<DataState<MenuModel?>> =
        flow<DataState<MenuModel?>> {
            emit(DataState.Loading())

            try {
                val menu = localDataSource.getMenuById(menuId)
                emit(DataState.SuccessWithNullableData(menu?.toModel()))
            } catch (e: Exception) {
                if (!isOnTesting) e.printStackTrace()
                emit(
                    DataState.Error(
                        RepositoryIOException(e.message.toString()),
                        "Something went wrong when retrieving menu by id",
                    )
                )
            }
        }.flowOn(ioDispatcher)

    override suspend fun deleteAllMenus(): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading())

            try {
                localDataSource.deleteAllMenus()
                emit(DataState.Success(true))
            } catch (e: Exception) {
                if (!isOnTesting) e.printStackTrace()
                emit(
                    DataState.Error(
                        RepositoryIOException(e.message.toString()),
                        "Something went wrong when deleting all menus",
                    )
                )
            }
        }.flowOn(ioDispatcher)

    override suspend fun getAllRecommendationResults(): Flow<DataState<List<RecommendationResultModel>>> =
        flow {
            emit(DataState.Loading())

            try {
                val recommendationResults =
                    localDataSource.getAllRecommendationResults().toResultModels()
                emit(DataState.Success(recommendationResults))
            } catch (e: Exception) {
                if (!isOnTesting) e.printStackTrace()
                emit(
                    DataState.Error(
                        RepositoryIOException(e.message.toString()),
                        "Something went wrong when retrieving all recommendation results",
                    )
                )
            }
        }.flowOn(ioDispatcher)

    override suspend fun deleteRecommendationResultOf(resultId: Int): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading())

            try {
                localDataSource.deleteRecommendationResultOf(resultId)
                emit(DataState.Success(true))
            } catch (e: Exception) {
                if (!isOnTesting) e.printStackTrace()
                emit(
                    DataState.Error(
                        RepositoryIOException(e.message.toString()),
                        "Something went wrong when deleting recommendation results of given id",
                    )
                )
            }
        }.flowOn(ioDispatcher)

    override suspend fun deleteAllRecommendationResults(): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading())

            try {
                localDataSource.deleteAllRecommendationResults()
                emit(DataState.Success(true))
            } catch (e: Exception) {
                if (!isOnTesting) e.printStackTrace()
                emit(
                    DataState.Error(
                        RepositoryIOException(e.message.toString()),
                        "Something went wrong when deleting all recommendation results",
                    )
                )
            }
        }.flowOn(ioDispatcher)
}