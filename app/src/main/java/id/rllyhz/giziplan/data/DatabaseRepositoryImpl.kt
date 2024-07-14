package id.rllyhz.giziplan.data

import id.rllyhz.giziplan.data.local.LocalDataSource
import id.rllyhz.giziplan.domain.model.MeasureResultModel
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.repository.DatabaseRepository
import id.rllyhz.giziplan.domain.utils.DataState
import id.rllyhz.giziplan.domain.utils.toEntities
import id.rllyhz.giziplan.domain.utils.toEntity
import id.rllyhz.giziplan.domain.utils.toModel
import id.rllyhz.giziplan.domain.utils.toModels
import id.rllyhz.giziplan.domain.utils.toResultModels
import id.rllyhz.giziplan.utils.RepositoryIOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class DatabaseRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineContext,
    private val isOnTesting: Boolean = false,
) : DatabaseRepository {

    override suspend fun getAllMenus(): Flow<DataState<List<MenuModel>>> = flow {
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

    override suspend fun insertAllMenus(menus: List<MenuModel>): Flow<DataState<Boolean>> = flow {
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

    override suspend fun deleteAllMenus(): Flow<DataState<Boolean>> = flow {
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

    override suspend fun getAllMeasureResults(): Flow<DataState<List<MeasureResultModel>>> = flow {
        emit(DataState.Loading())

        try {
            val recommendationResults = localDataSource.getAllMeasureResults().toResultModels()
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

    override suspend fun insertNewMeasureResult(measureResult: MeasureResultModel) =
        localDataSource.insertNewMeasureResult(measureResult.toEntity())

    override suspend fun deleteMeasureResultOf(resultId: Int): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading())

        try {
            localDataSource.deleteMeasureResultOf(resultId)
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

    override suspend fun deleteAllMeasureResults(): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading())

        try {
            localDataSource.deleteAllMeasureResults()
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