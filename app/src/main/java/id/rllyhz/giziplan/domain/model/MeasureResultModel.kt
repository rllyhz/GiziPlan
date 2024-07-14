package id.rllyhz.giziplan.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class MeasureResultModel(
    val age: Int,
    val gender: Int,
    val height: Double,
    val weight: Double,
    val nutritionalStatusResult: String,
    val heightToAgeResult: Double,
    val weightToAgeResult: Double,
    val weightToHeightResult: Double,
    val createdAt: Long = Date().time,
    val id: Int = 0,
) : Parcelable