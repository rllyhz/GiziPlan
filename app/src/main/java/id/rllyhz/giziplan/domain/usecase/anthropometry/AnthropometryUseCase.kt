package id.rllyhz.giziplan.domain.usecase.anthropometry

import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.domain.model.ZScoreCategory

interface AnthropometryUseCase {
    fun measureZScoreForWeightToAge(
        measuredWeight: Double,
        age: Int,
        gender: Gender,
    ): Double?

    fun measureZScoreForHeightToAge(
        measuredHeight: Double,
        age: Int,
        gender: Gender,
    ): Double?

    fun measureZScoreForWeightToHeight(
        measuredWeight: Double,
        height: Double,
        gender: Gender,
    ): Double?

    fun classifyZScore(category: ZScoreCategory, zScore: Double): String
}