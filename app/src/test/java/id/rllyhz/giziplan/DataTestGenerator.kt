package id.rllyhz.giziplan

import id.rllyhz.giziplan.data.local.db.entity.MenuEntity
import id.rllyhz.giziplan.data.local.db.entity.RecommendationResultEntity
import id.rllyhz.giziplan.domain.model.AgeCategory
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.model.NutritionalStatusCategory
import id.rllyhz.giziplan.domain.model.RecommendationResultModel
import id.rllyhz.giziplan.utils.getRandomAgeCategory
import id.rllyhz.giziplan.utils.getRandomNutritionalStatusCategory
import id.rllyhz.giziplan.utils.randomEnergy
import id.rllyhz.giziplan.utils.randomFat
import id.rllyhz.giziplan.utils.randomNum
import id.rllyhz.giziplan.utils.randomProtein

fun createDummyMenuEntity(
    id: Int = 0,
    nutritionalStatusCategory: NutritionalStatusCategory = getRandomNutritionalStatusCategory(),
    ageCategory: AgeCategory = getRandomAgeCategory()
): MenuEntity = MenuEntity(
    id,
    "Simple Menu",
    "Ingredients Menu",
    "Ingredients Menu",
    nutritionalStatusCategory.stringCategory,
    ageCategory.stringCategory,
    randomEnergy(),
    randomProtein(),
    randomFat(),
    "Simple Description Menu",
    "Notes for simple Menu",
)

fun createDummyMenuEntities(amount: Int = 20): List<MenuEntity> {
    val entities = arrayListOf<MenuEntity>()

    for (i in 0..<amount) {
        val newEntity = MenuEntity(
            i,
            "Simple Menu $i",
            "Ingredients Menu $i",
            "Ingredients Menu $i",
            getRandomAgeCategory().stringCategory,
            getRandomNutritionalStatusCategory().stringCategory,
            randomEnergy(),
            randomProtein(),
            randomFat(),
            "Simple Description Menu $i",
            "Notes for simple Menu $i",
        )

        entities.add(newEntity)
    }

    return entities
}

fun createDummyRecommendationResultEntity(
    id: Int = 0,
    resultId: Int = 0,
    menuId: Int = 0,
    nutritionalStatusCategory: NutritionalStatusCategory = NutritionalStatusCategory.Normal
): RecommendationResultEntity = RecommendationResultEntity(
    id,
    resultId,
    menuId,
    randomNum(60, 120),
    randomNum(60, 120).toDouble(),
    12.0,
    nutritionalStatusCategory.stringCategory,
    0
)

fun createDummyMenuModel(
    id: Int = 0,
    nutritionalStatusCategory: NutritionalStatusCategory = getRandomNutritionalStatusCategory(),
    ageCategory: AgeCategory = getRandomAgeCategory()
): MenuModel = MenuModel(
    id,
    "Simple Menu",
    "Ingredients Menu",
    "Ingredients Menu",
    nutritionalStatusCategory.stringCategory,
    ageCategory.stringCategory,
    randomEnergy(),
    randomProtein(),
    randomFat(),
    "Simple Description Menu",
    "Notes for simple Menu",
)

fun createDummyRecommendationResultModel(
    id: Int,
    resultId: Int,
    menuIds: String,
    nutritionalStatusCategory: NutritionalStatusCategory = NutritionalStatusCategory.Normal
): RecommendationResultModel = RecommendationResultModel(
    id,
    resultId,
    menuIds,
    randomNum(60, 120),
    randomNum(60, 120).toDouble(),
    12.0,
    nutritionalStatusCategory.stringCategory,
    0
)