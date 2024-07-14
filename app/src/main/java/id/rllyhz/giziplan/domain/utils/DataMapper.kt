package id.rllyhz.giziplan.domain.utils

import id.rllyhz.giziplan.data.local.db.entity.MeasureResultEntity
import id.rllyhz.giziplan.data.local.db.entity.MenuEntity
import id.rllyhz.giziplan.domain.model.MeasureResultModel
import id.rllyhz.giziplan.domain.model.MenuModel

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

fun MeasureResultModel.toEntity(): MeasureResultEntity = MeasureResultEntity(
    age = age,
    gender = gender,
    height = height,
    weight = weight,
    nutritionalStatusResult = nutritionalStatusResult,
    heightToAgeResult = heightToAgeResult,
    weightToAgeResult = weightToAgeResult,
    weightToHeightResult = weightToHeightResult,
    createdAt = createdAt
)

fun List<MenuEntity>.toModels(): List<MenuModel> = map { it.toModel() }

fun List<MeasureResultEntity>.toResultModels(): List<MeasureResultModel> {
    if (isEmpty()) return emptyList()

    val recommendationResults = arrayListOf<MeasureResultModel>()

    forEach {
        val temp = MeasureResultModel(
            it.age, it.gender, it.height, it.weight,
            it.nutritionalStatusResult,
            it.heightToAgeResult,
            it.weightToAgeResult,
            it.weightToHeightResult,
            it.createdAt,
            it.id,
        )
        recommendationResults.add(temp)
    }

    return recommendationResults
}

fun List<MenuModel>.toEntities(): List<MenuEntity> = map { it.toEntity() }