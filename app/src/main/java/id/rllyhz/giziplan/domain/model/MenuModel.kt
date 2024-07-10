package id.rllyhz.giziplan.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuModel(
    val id: Int = 0,
    val name: String,
    val ingredients: String,
    val instruction: String,
    val nutritionalStatusCategory: String,
    val ageCategory: String,
    val energyKiloCal: Double,
    val proteinGr: Double,
    val fatGr: Double,
    val description: String? = "",
    val notes: String? = "",
    val imagePath: Int? = null,
) : Parcelable
