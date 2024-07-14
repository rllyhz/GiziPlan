package id.rllyhz.giziplan.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "hasil_perhitungan")
data class MeasureResultEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "hasil_id")
    val id: Int = 0,
    @ColumnInfo(name = "umur")
    val age: Int,
    @ColumnInfo(name = "jenis_kelamin")
    val gender: Int, // 0 -> lk, 1 -> pr
    @ColumnInfo(name = "tinggi_badan")
    val height: Double,
    @ColumnInfo(name = "berat_badan")
    val weight: Double,
    @ColumnInfo(name = "status_gizi")
    val nutritionalStatusResult: String,
    @ColumnInfo(name = "tinggi_badan_per_umur")
    val heightToAgeResult: Double = 0.0,
    @ColumnInfo(name = "berat_badan_per_umur")
    val weightToAgeResult: Double = 0.0,
    @ColumnInfo(name = "berat_badan_per_tinggi_badan")
    val weightToHeightResult: Double = 0.0,
    @ColumnInfo(name = "dibuat_pada")
    val createdAt: Long = Date().time,
)