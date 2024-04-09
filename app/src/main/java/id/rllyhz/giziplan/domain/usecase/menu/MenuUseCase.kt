package id.rllyhz.giziplan.domain.usecase.menu

import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface MenuUseCase {
    suspend fun getAllMenus(): Flow<DataState<List<MenuModel>>>

    suspend fun insertAllMenus(menus: List<MenuModel>): Flow<DataState<Boolean>>

    suspend fun getMenuById(menuId: Int): Flow<DataState<MenuModel?>>
}