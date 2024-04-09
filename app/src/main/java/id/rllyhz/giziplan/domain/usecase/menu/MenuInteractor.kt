package id.rllyhz.giziplan.domain.usecase.menu

import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.repository.GiziRepository
import id.rllyhz.giziplan.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

class MenuInteractor(
    private val repository: GiziRepository
) : MenuUseCase {
    override suspend fun getAllMenus(): Flow<DataState<List<MenuModel>>> =
        repository.getAllMenus()

    override suspend fun insertAllMenus(menus: List<MenuModel>): Flow<DataState<Boolean>> =
        repository.insertAllMenus(menus)

    override suspend fun getMenuById(menuId: Int): Flow<DataState<MenuModel?>> =
        repository.getMenuById(menuId)
}