package id.rllyhz.giziplan.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.rllyhz.giziplan.data.local.db.entity.MenuEntity
import id.rllyhz.giziplan.data.local.db.entity.RecommendationResultEntity

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

    @Query("SELECT * FROM hasil_rekomendasi")
    suspend fun getAllRecommendationResults(): List<RecommendationResultEntity>

    @Query("DELETE FROM hasil_rekomendasi WHERE hasil_id = :resultId")
    suspend fun deleteRecommendationResultOf(resultId: Int)

    @Query("DELETE FROM hasil_rekomendasi")
    suspend fun deleteAllRecommendationResults()
}