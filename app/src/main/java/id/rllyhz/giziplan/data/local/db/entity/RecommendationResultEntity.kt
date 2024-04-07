package id.rllyhz.giziplan.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hasil_rekomendasi")
data class RecommendationResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "hasil_id")
    val resultId: Int,
    @ColumnInfo(name = "menu_id")
    val menuId: Int,
    @ColumnInfo(name = "umur")
    val age: Int,
    @ColumnInfo(name = "tinggi_badan")
    val height: Double,
    @ColumnInfo(name = "berat_badan")
    val weight: Double,
    @ColumnInfo(name = "hasil_status_gizi")
    val nutritionalStatusResults: String,
    val createdAt: Long,
)