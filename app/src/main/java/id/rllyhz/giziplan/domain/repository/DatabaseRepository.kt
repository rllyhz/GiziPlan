package id.rllyhz.giziplan.domain.repository

import id.rllyhz.giziplan.domain.model.MeasureResultModel
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun getAllMenus(): Flow<DataState<List<MenuModel>>>

    suspend fun insertAllMenus(menus: List<MenuModel>): Flow<DataState<Boolean>>

    suspend fun deleteAllMenus(): Flow<DataState<Boolean>>

    suspend fun getMenuById(menuId: Int): Flow<DataState<MenuModel?>>

    suspend fun getAllMeasureResults(): Flow<DataState<List<MeasureResultModel>>>

    suspend fun insertNewMeasureResult(measureResult: MeasureResultModel)

    suspend fun deleteMeasureResultOf(resultId: Int): Flow<DataState<Boolean>>

    suspend fun deleteAllMeasureResults(): Flow<DataState<Boolean>>
}