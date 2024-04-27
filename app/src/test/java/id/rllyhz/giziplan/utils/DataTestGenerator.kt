package id.rllyhz.giziplan.utils

import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryDataTable
import id.rllyhz.giziplan.data.anthropometry.model.PopulationRow
import id.rllyhz.giziplan.data.anthropometry.type.ReferenceValueType
import id.rllyhz.giziplan.data.anthropometry.type.PopulationValueType
import id.rllyhz.giziplan.data.local.db.entity.MenuEntity
import id.rllyhz.giziplan.data.local.db.entity.RecommendationResultEntity
import id.rllyhz.giziplan.domain.model.AgeCategory
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.model.RecommendationResultModel
import id.rllyhz.giziplan.domain.model.classification.ClassificationData
import id.rllyhz.giziplan.domain.model.classification.GoodNutritionalStatus
import java.util.Date
import kotlin.random.Random

fun createDummyMenuEntity(
    id: Int = 0,
    classificationData: ClassificationData = getRandomNutritionalStatusCategory(),
    ageCategory: AgeCategory = getRandomAgeCategory()
): MenuEntity = MenuEntity(
    id,
    "Simple Menu",
    "Ingredients Menu",
    "Ingredients Menu",
    classificationData.getClassificationName(),
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
            getRandomNutritionalStatusCategory().getClassificationName(),
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

fun createDummyMenuModels(amount: Int = 20): List<MenuModel> {
    val models = arrayListOf<MenuModel>()

    for (i in 0..<amount) {
        val newModel = MenuModel(
            i,
            "Simple Menu $i",
            "Ingredients Menu $i",
            "Ingredients Menu $i",
            getRandomAgeCategory().stringCategory,
            getRandomNutritionalStatusCategory().getClassificationName(),
            randomEnergy(),
            randomProtein(),
            randomFat(),
            "Simple Description Menu $i",
            "Notes for simple Menu $i",
        )

        models.add(newModel)
    }

    return models
}

fun createDummyRecommendationResultEntity(
    id: Int = 0,
    resultId: Int = 0,
    menuId: Int = 0,
    classificationData: ClassificationData = GoodNutritionalStatus
): RecommendationResultEntity = RecommendationResultEntity(
    id,
    resultId,
    menuId,
    randomNum(60, 120),
    randomNum(60, 120).toDouble(),
    12.0,
    classificationData.getClassificationName(),
    0
)

fun createDummyRecommendationResultEntities(
    amount: Int = 20
): List<RecommendationResultEntity> {
    val recommendationResults = arrayListOf<RecommendationResultEntity>()

    val range = 3
    var lastResultId = 0
    var menuId: Int
    var counter = 1

    for (i in 0..<amount) {
        menuId = i + 1

        val newRecommendationResult = RecommendationResultEntity(
            i,
            lastResultId,
            menuId,
            randomAge(),
            randomHeight(),
            randomHeight(),
            getRandomNutritionalStatusCategory().getClassificationName(),
            Date().time,
        )

        recommendationResults.add(newRecommendationResult)

        if (counter >= range) {
            counter = 1 // reset
            lastResultId += 1
        } else {
            counter += 1
        }
    }

    return recommendationResults
}

fun createDummyMenuModel(
    id: Int = 0,
    classificationData: ClassificationData = getRandomNutritionalStatusCategory(),
    ageCategory: AgeCategory = getRandomAgeCategory()
): MenuModel = MenuModel(
    id,
    "Simple Menu",
    "Ingredients Menu",
    "Ingredients Menu",
    classificationData.getClassificationName(),
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
    classificationData: ClassificationData = GoodNutritionalStatus
): RecommendationResultModel = RecommendationResultModel(
    id,
    resultId,
    menuIds,
    randomNum(60, 120),
    randomNum(60, 120).toDouble(),
    12.0,
    classificationData.getClassificationName(),
    0
)

private fun createDummyPopulationRows(
    referenceValueType: ReferenceValueType,
    populationValueType: PopulationValueType,
    measuredValueStart: Int = 0,
    populationValueStart: Int? = null,
    size: Int = 20
): List<PopulationRow> {
    val populationRows = arrayListOf<PopulationRow>()
    var measuredValue = measuredValueStart
    var populationValue = populationValueStart ?: Random.nextInt(12, 80)

    for (i in 0..size) {
        val min3SD = populationValue.toDouble() + 0.2
        val min2SD = min3SD + 0.2
        val min1SD = min2SD + 0.2
        val median = min1SD + 0.2
        val plus1SD = median + 0.2
        val plus2SD = plus1SD + 0.2
        val plus3SD = plus2SD + 0.2

        val newPopulationRow = PopulationRow(
            referenceValueType, populationValueType,
            measuredValue.toDouble(),
            min3SD, min2SD, min1SD, median, plus1SD, plus2SD, plus3SD,
        )

        populationRows.add(newPopulationRow)
        measuredValue++
        populationValue++
    }

    return populationRows
}

fun createDummyDataTable(
    referenceValueType: ReferenceValueType,
    populationValueType: PopulationValueType
): AnthropometryDataTable {
    val malePopulationRows = createDummyPopulationRows(
        referenceValueType, populationValueType
    )

    val femalePopulationRows = createDummyPopulationRows(
        referenceValueType, populationValueType
    )

    return AnthropometryDataTable(
        referenceValueType, populationValueType,
        malePopulationRows,
        femalePopulationRows,
    )
}

fun createDummyPopulationCSVContent(): String =
    """
        "Umur (bulan)","Berat Badan (Kg)","","","","","",""
        "","-3 SD","-2 SD","-1 SD","Median","+1 SD","+2 SD","+3 SD"
        "0","2.1","2.5","2.9","3.3","3.9","4.4","5.0"
        "1","2.9","3.4","3.9","4.5","5.1","5.8","6.6"
        "2","3.8","4.3","4.9","5.6","6.3","7.1","8.0"
        "3","4.4","5.0","5.7","6.4","7.2","8.0","9.0"
        "4","4.9","5.6","6.2","7.0","7.8","8.7","9.7"
        "5","5.3","6.0","6.7","7.5","8.4","9.3","10.4"
    """.trimIndent()