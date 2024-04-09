package id.rllyhz.giziplan.data.local

import id.rllyhz.giziplan.data.local.db.GiziDao
import id.rllyhz.giziplan.data.local.db.entity.MenuEntity
import id.rllyhz.giziplan.data.local.db.entity.RecommendationResultEntity

class LocalDataSource(
    private val giziDao: GiziDao
) {
    suspend fun getAllMenus(): List<MenuEntity> =
        giziDao.getAllMenus()

    suspend fun getMenuById(menuId: Int): MenuEntity? =
        giziDao.getMenuById(menuId)

    suspend fun insertAllMenus(menus: List<MenuEntity>) =
        giziDao.insertAllMenus(menus)

    suspend fun deleteAllMenus() =
        giziDao.deleteAllMenus()

    suspend fun getAllRecommendationResults(): List<RecommendationResultEntity> =
        giziDao.getAllRecommendationResults()

    suspend fun deleteRecommendationResultOf(resultId: Int) =
        giziDao.deleteRecommendationResultOf(resultId)

    suspend fun deleteAllRecommendationResults() =
        giziDao.deleteAllRecommendationResults()
}