package id.rllyhz.giziplan.domain.model


data class RecommendationResultModel(
    val id: Int,
    val resultId: Int = 0,
    val menuIds: String,
    val age: Int,
    val height: Double,
    val weight: Double,
    val nutritionalStatusResults: String,
    val createdAt: Long,
)