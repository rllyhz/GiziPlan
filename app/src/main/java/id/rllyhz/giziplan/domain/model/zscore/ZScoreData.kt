package id.rllyhz.giziplan.domain.model.zscore

data class ZScoreData(
    val zScore: Double,
    val zScoreCategory: ZScoreCategory,
    val isOutOfRangePopulation: Boolean = false,
)