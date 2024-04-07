package id.rllyhz.giziplan.domain.utils

import id.rllyhz.giziplan.data.local.db.entity.MenuEntity
import id.rllyhz.giziplan.data.local.db.entity.RecommendationResultEntity
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.model.RecommendationResultModel
import id.rllyhz.giziplan.utils.toIntList

fun MenuEntity.toModel(): MenuModel = MenuModel(
    id,
    name,
    ingredients,
    instruction,
    nutritionalStatusCategory,
    ageCategory,
    energyKiloCal,
    proteinGr,
    fatGr,
    description,
    notes,
    imagePath
)

fun List<RecommendationResultEntity>.toModel(): List<RecommendationResultModel> {
    if (isEmpty()) return emptyList()

    val recommendationResults = arrayListOf<RecommendationResultModel>()

    groupBy { it.resultId }.forEach { (resultId, list) ->
        val first = list.first()

        var menuIds = ""
        val lastItemId = list.last().id

        for (eachRecommendation in list) {
            menuIds += eachRecommendation.menuId.toString()

            if (eachRecommendation.id != lastItemId) {
                menuIds += ","
            }
        }

        val model = RecommendationResultModel(
            0,
            resultId,
            menuIds,
            first.age,
            first.height,
            first.weight,
            first.nutritionalStatusResults,
            first.createdAt
        )

        recommendationResults.add(model)
    }

    return recommendationResults
}

fun MenuModel.toEntity(): MenuEntity = MenuEntity(
    id,
    name,
    ingredients,
    instruction,
    nutritionalStatusCategory,
    ageCategory,
    energyKiloCal,
    proteinGr,
    fatGr,
    description,
    notes,
    imagePath
)

fun RecommendationResultModel.toEntities(): List<RecommendationResultEntity> {
    val entities = arrayListOf<RecommendationResultEntity>()
    val menuIds = this.menuIds.toIntList()

    for (id in menuIds) {
        val newEntity = RecommendationResultEntity(
            0, resultId, id, age, height, weight, nutritionalStatusResults, createdAt
        )
        entities.add(newEntity)
    }

    return entities
}