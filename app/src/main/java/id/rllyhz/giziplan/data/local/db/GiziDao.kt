package id.rllyhz.giziplan.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import id.rllyhz.giziplan.data.local.db.entity.MenuEntity
import id.rllyhz.giziplan.data.local.db.entity.RecommendationResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GiziDao {
    @Query("SELECT * FROM menu")
    fun getAllMenu(): Flow<List<MenuEntity>>

    @Query("SELECT * FROM menu WHERE id = :menuId")
    fun getMenuById(menuId: Int): MenuEntity?

    @Insert
    fun insertAll(vararg users: MenuEntity)

    @Query("SELECT * FROM hasil_rekomendasi")
    fun getAllRecommendationResults(): Flow<List<RecommendationResultEntity>>

    @Query("SELECT * FROM hasil_rekomendasi WHERE hasil_id = :resultId")
    fun getRecommendationResultOf(resultId: Int): Flow<List<RecommendationResultEntity>>

    @Query("DELETE FROM hasil_rekomendasi WHERE hasil_id = :resultId")
    fun deleteRecommendationResultOf(resultId: Int)
}