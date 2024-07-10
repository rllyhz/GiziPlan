package id.rllyhz.giziplan.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.repository.DatabaseRepository
import id.rllyhz.giziplan.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

class MenuViewModel(
    private val dbRepository: DatabaseRepository,
) : ViewModel() {
    suspend fun loadMenu(): Flow<DataState<List<MenuModel>>> =
        dbRepository.getAllMenus()

    class Factory(
        private val dbRepository: DatabaseRepository,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MenuViewModel(dbRepository) as T
        }
    }
}