package id.rllyhz.giziplan.utils.fakes

import id.rllyhz.giziplan.createDummyMenuEntities
import id.rllyhz.giziplan.createDummyMenuEntity
import id.rllyhz.giziplan.createDummyRecommendationResultEntities
import id.rllyhz.giziplan.data.local.db.GiziDao
import id.rllyhz.giziplan.data.local.db.entity.MenuEntity
import id.rllyhz.giziplan.data.local.db.entity.RecommendationResultEntity

class FakeGiziDao(
    private val menuCount: Int
) : GiziDao {
    override suspend fun getAllMenus(): List<MenuEntity> =
        createDummyMenuEntities(menuCount)

    override suspend fun getMenuById(menuId: Int): MenuEntity =
        createDummyMenuEntity(id = menuId)

    override suspend fun insertAllMenus(menus: List<MenuEntity>) {}

    override suspend fun deleteAllMenus() {}

    override suspend fun getAllRecommendationResults(): List<RecommendationResultEntity> =
        createDummyRecommendationResultEntities()

    override suspend fun deleteRecommendationResultOf(resultId: Int) {}

    override suspend fun deleteAllRecommendationResults() {}
}