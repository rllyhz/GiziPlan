package id.rllyhz.giziplan.data

import id.rllyhz.giziplan.data.local.LocalDataSource
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.model.RecommendationResultModel
import id.rllyhz.giziplan.domain.repository.GiziRepository
import id.rllyhz.giziplan.domain.utils.DataState
import id.rllyhz.giziplan.domain.utils.toEntities
import id.rllyhz.giziplan.domain.utils.toModel
import id.rllyhz.giziplan.domain.utils.toModels
import id.rllyhz.giziplan.utils.RepositoryIOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GiziRepositoryImpl(
    private val localDataSource: LocalDataSource,
) : GiziRepository {
    override suspend fun getAllMenus(): Flow<DataState<List<MenuModel>>> =
        flow {
            emit(DataState.Loading())

            try {
                val menus = localDataSource.getAllMenus().toModels()
                emit(DataState.Success(menus))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Error(
                        RepositoryIOException(e.message.toString()),
                        "Something went wrong when retrieving all menus",
                    )
                )
            }
        }

    override suspend fun insertAllMenus(menus: List<MenuModel>): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading())

            try {
                localDataSource.insertAllMenus(menus.toEntities())
                emit(DataState.Success(true))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Error(
                        RepositoryIOException(e.message.toString()),
                        "Something went wrong when inserting all menus",
                    )
                )
            }
        }

    override suspend fun getMenuById(menuId: Int): Flow<DataState<MenuModel?>> =
        flow {
            emit(DataState.Loading())

            try {
                val menu = localDataSource.getMenuById(menuId)
                emit(DataState.SuccessWithNullableData(menu?.toModel()))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Error(
                        RepositoryIOException(e.message.toString()),
                        "Something went wrong when retrieving menu by id",
                    )
                )
            }
        }

    override suspend fun deleteAllMenus(): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading())

            try {
                localDataSource.deleteAllMenus()
                emit(DataState.Success(true))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Error(
                        RepositoryIOException(e.message.toString()),
                        "Something went wrong when deleting all menus",
                    )
                )
            }
        }

    override suspend fun getAllRecommendationResults(): Flow<DataState<List<RecommendationResultModel>>> =
        flow {
            emit(DataState.Loading())

            try {
                val recommendationResults = localDataSource.getAllRecommendationResults().toModel()
                emit(DataState.Success(recommendationResults))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Error(
                        RepositoryIOException(e.message.toString()),
                        "Something went wrong when retrieving all recommendation results",
                    )
                )
            }
        }

    override suspend fun deleteRecommendationResultOf(resultId: Int): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading())

            try {
                localDataSource.deleteRecommendationResultOf(resultId)
                emit(DataState.Success(true))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Error(
                        RepositoryIOException(e.message.toString()),
                        "Something went wrong when deleting recommendation results of given id",
                    )
                )
            }
        }

    override suspend fun deleteAllRecommendationResults(): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading())

        try {
            localDataSource.deleteAllRecommendationResults()
            emit(DataState.Success(true))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Error(
                    RepositoryIOException(e.message.toString()),
                    "Something went wrong when deleting all recommendation results",
                )
            )
        }
    }
}