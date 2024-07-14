package id.rllyhz.giziplan.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.rllyhz.giziplan.data.local.db.entity.MeasureResultEntity
import id.rllyhz.giziplan.data.local.db.entity.MenuEntity

@Dao
interface GiziDao {
    @Query("SELECT * FROM menu")
    suspend fun getAllMenus(): List<MenuEntity>

    @Query("SELECT * FROM menu WHERE id = :menuId")
    suspend fun getMenuById(menuId: Int): MenuEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMenus(menus: List<MenuEntity>)

    @Query("DELETE FROM menu")
    suspend fun deleteAllMenus()

    @Query("SELECT * FROM hasil_perhitungan ORDER BY dibuat_pada DESC")
    suspend fun getAllMeasureResults(): List<MeasureResultEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewMeasureResult(measureResult: MeasureResultEntity)

    @Query("DELETE FROM hasil_perhitungan WHERE hasil_id = :resultId")
    suspend fun deleteMeasureResultOf(resultId: Int)

    @Query("DELETE FROM hasil_perhitungan")
    suspend fun deleteAllMeasureResults()
}