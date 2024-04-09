package id.rllyhz.giziplan.data.local

import id.rllyhz.giziplan.data.local.db.GiziDao
import id.rllyhz.giziplan.data.local.db.entity.MenuEntity
import id.rllyhz.giziplan.data.local.db.entity.RecommendationResultEntity

open class LocalDataSource(
    private val giziDao: GiziDao
) {
    open suspend fun getAllMenus(): List<MenuEntity> =
        giziDao.getAllMenus()

    open suspend fun getMenuById(menuId: Int): MenuEntity? =
        giziDao.getMenuById(menuId)

    open suspend fun insertAllMenus(menus: List<MenuEntity>) =
        giziDao.insertAllMenus(menus)

    open suspend fun deleteAllMenus() =
        giziDao.deleteAllMenus()

    open suspend fun getAllRecommendationResults(): List<RecommendationResultEntity> =
        giziDao.getAllRecommendationResults()

    open suspend fun deleteRecommendationResultOf(resultId: Int) =
        giziDao.deleteRecommendationResultOf(resultId)

    open suspend fun deleteAllRecommendationResults() =
        giziDao.deleteAllRecommendationResults()
}