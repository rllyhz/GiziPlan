package id.rllyhz.giziplan.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu")
data class MenuEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "nama_makanan")
    val name: String,
    @ColumnInfo(name = "bahan_bahan")
    val ingredients: String,
    @ColumnInfo(name = "instruksi_singkat")
    val instruction: String,
    @ColumnInfo(name = "kategori_status_gizi")
    val nutritionalStatusCategory: String,
    @ColumnInfo(name = "kategori_umur")
    val ageCategory: String,
    @ColumnInfo(name = "energi_kkal")
    val energyKiloCal: Double,
    @ColumnInfo(name = "protein_gr")
    val proteinGr: Double,
    @ColumnInfo(name = "lemak_gr")
    val fatGr: Double,
    @ColumnInfo(name = "deskripsi")
    val description: String? = "",
    @ColumnInfo(name = "catatan")
    val notes: String? = "",
    @ColumnInfo(name = "gambar_path")
    val imagePath: String? = null,
)
