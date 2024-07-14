package id.rllyhz.giziplan.data.local

import id.rllyhz.giziplan.data.local.db.GiziDao
import id.rllyhz.giziplan.data.local.db.entity.MeasureResultEntity
import id.rllyhz.giziplan.data.local.db.entity.MenuEntity

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

    open suspend fun getAllMeasureResults(): List<MeasureResultEntity> =
        giziDao.getAllMeasureResults()

    open suspend fun insertNewMeasureResult(measureResult: MeasureResultEntity) =
        giziDao.insertNewMeasureResult(measureResult)

    open suspend fun deleteMeasureResultOf(resultId: Int) =
        giziDao.deleteMeasureResultOf(resultId)

    open suspend fun deleteAllMeasureResults() =
        giziDao.deleteAllMeasureResults()
}