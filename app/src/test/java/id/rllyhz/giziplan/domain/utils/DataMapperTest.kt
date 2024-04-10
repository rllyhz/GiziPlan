package id.rllyhz.giziplan.domain.utils

import id.rllyhz.giziplan.createDummyMenuEntities
import id.rllyhz.giziplan.createDummyMenuEntity
import id.rllyhz.giziplan.createDummyMenuModel
import id.rllyhz.giziplan.createDummyMenuModels
import id.rllyhz.giziplan.createDummyRecommendationResultEntity
import id.rllyhz.giziplan.createDummyRecommendationResultModel
import id.rllyhz.giziplan.domain.model.AgeCategory
import id.rllyhz.giziplan.domain.model.NutritionalStatusCategory
import id.rllyhz.giziplan.utils.toIntList
import org.junit.Assert
import org.junit.Test

class DataMapperTest {

    @Test
    fun `successfully convert menu entity type into menu model type`() {
        val menuEntity = createDummyMenuEntity(
            0, NutritionalStatusCategory.Overweight, AgeCategory.B
        )

        val menuModel = menuEntity.toModel()

        Assert.assertEquals(menuModel.id, menuEntity.id)
        Assert.assertEquals(menuModel.name, menuEntity.name)
        Assert.assertEquals(menuModel.ageCategory, menuEntity.ageCategory)
        Assert.assertEquals(
            menuModel.nutritionalStatusCategory,
            menuEntity.nutritionalStatusCategory
        )
        Assert.assertEquals(menuModel.description, menuEntity.description)
    }

    @Test
    fun `successfully convert all menu entity types into menu model types`() {
        val expectedLength = 3
        val entities = createDummyMenuEntities(expectedLength)
        val expectedFirstModelId = entities.first().id
        val expectedFirstModelName = entities.first().name
        val expectedFirstModelAgeCategory = entities.first().ageCategory
        val expectedFirstModelNutritionalStatusCategory = entities.first().nutritionalStatusCategory

        val models = entities.toModels()
        val firstModel = models.first()

        Assert.assertEquals(expectedLength, models.count())
        Assert.assertEquals(expectedFirstModelId, firstModel.id)
        Assert.assertEquals(expectedFirstModelName, firstModel.name)
        Assert.assertEquals(expectedFirstModelAgeCategory, firstModel.ageCategory)
        Assert.assertEquals(
            expectedFirstModelNutritionalStatusCategory,
            firstModel.nutritionalStatusCategory
        )
    }

    @Test
    fun `successfully convert recommendation result entities type into recommendation result model type`() {
        val resultId1 = 3
        val resultId2 = 7
        val nutritionalStatusCategory1 = NutritionalStatusCategory.Normal
        val nutritionalStatusCategory2 = NutritionalStatusCategory.PossibleRiskOfOverweight

        val entities = arrayListOf(
            createDummyRecommendationResultEntity(
                0, resultId1, 1, nutritionalStatusCategory1
            ),
            createDummyRecommendationResultEntity(
                1, resultId1, 2, nutritionalStatusCategory1
            ),
            createDummyRecommendationResultEntity(
                2, resultId1, 3, nutritionalStatusCategory1
            ),
            createDummyRecommendationResultEntity(
                3, resultId2, 4, nutritionalStatusCategory2
            ),
            createDummyRecommendationResultEntity(
                4, resultId2, 5, nutritionalStatusCategory2
            ),
        )

        val recommendationResultModel = entities.toResultModels()
        Assert.assertEquals(recommendationResultModel.count(), 2)

        val firstResult = recommendationResultModel.first()
        Assert.assertEquals(firstResult.resultId, resultId1)
        Assert.assertEquals(firstResult.menuIds, "1,2,3")

        val lastResult = recommendationResultModel.last()
        Assert.assertEquals(lastResult.resultId, resultId2)
        Assert.assertEquals(lastResult.menuIds, "4,5")
    }

    @Test
    fun `successfully convert menu model type into menu entity type`() {
        val menuModel = createDummyMenuModel(
            0, NutritionalStatusCategory.Overweight, AgeCategory.B
        )

        val entity = menuModel.toEntity()

        Assert.assertEquals(menuModel.id, entity.id)
        Assert.assertEquals(menuModel.name, entity.name)
        Assert.assertEquals(menuModel.ageCategory, entity.ageCategory)
        Assert.assertEquals(
            menuModel.nutritionalStatusCategory,
            entity.nutritionalStatusCategory
        )
        Assert.assertEquals(menuModel.description, entity.description)
    }

    @Test
    fun `successfully convert all menu model types into menu entity types`() {
        val expectedLength = 3
        val models = createDummyMenuModels(expectedLength)
        val expectedFirstEntityId = models.first().id
        val expectedFirstEntityName = models.first().name
        val expectedFirstEntityAgeCategory = models.first().ageCategory
        val expectedFirstEntityNutritionalStatusCategory = models.first().nutritionalStatusCategory

        val entities = models.toEntities()
        val firstEntity = entities.first()

        Assert.assertEquals(expectedLength, entities.count())
        Assert.assertEquals(expectedFirstEntityId, firstEntity.id)
        Assert.assertEquals(expectedFirstEntityName, firstEntity.name)
        Assert.assertEquals(expectedFirstEntityAgeCategory, firstEntity.ageCategory)
        Assert.assertEquals(
            expectedFirstEntityNutritionalStatusCategory,
            firstEntity.nutritionalStatusCategory
        )
    }

    @Test
    fun `successfully convert recommendation result model into entities type`() {
        val resultId = 4
        val menuIds = "2,5,6,8,3"
        val arrayTypeMenuIds = menuIds.toIntList()
        val expectedLength = arrayTypeMenuIds.count()
        val expectedFirstMenuId = arrayTypeMenuIds.first()
        val expectedLastMenuId = arrayTypeMenuIds.last()

        val model = createDummyRecommendationResultModel(
            0, resultId, menuIds,
        )

        val entities = model.toEntities()
        Assert.assertEquals(expectedLength, entities.count())

        val firstEntity = entities.first()
        Assert.assertEquals(resultId, firstEntity.resultId)
        Assert.assertEquals(expectedFirstMenuId, firstEntity.menuId)

        val lastEntity = entities.last()
        Assert.assertEquals(resultId, lastEntity.resultId)
        Assert.assertEquals(expectedLastMenuId, lastEntity.menuId)
    }
}