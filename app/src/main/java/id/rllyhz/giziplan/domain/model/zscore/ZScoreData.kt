package id.rllyhz.giziplan.domain.model.zscore

import id.rllyhz.giziplan.data.anthropometry.type.ZScoreCategory

data class ZScoreData(
    val zScore: Double,
    val zScoreCategory: ZScoreCategory,
    val isOutOfRangePopulation: Boolean,
)