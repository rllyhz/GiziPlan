package id.rllyhz.giziplan.domain.utils

import id.rllyhz.giziplan.domain.model.AgeCategory
import id.rllyhz.giziplan.domain.model.classification.GoodNutritionalStatus
import id.rllyhz.giziplan.domain.model.classification.Overweight
import id.rllyhz.giziplan.domain.model.classification.PossibleRiskOfOverweight
import id.rllyhz.giziplan.utils.createDummyMenuEntities
import id.rllyhz.giziplan.utils.createDummyMenuEntity
import id.rllyhz.giziplan.utils.createDummyMenuModel
import id.rllyhz.giziplan.utils.createDummyMenuModels
import id.rllyhz.giziplan.utils.createDummyRecommendationResultEntity
import org.junit.Assert
import org.junit.Test

class DataMapperTest {

    @Test
    fun `successfully convert menu entity type into menu model type`() {
        val menuEntity = createDummyMenuEntity(
            0, Overweight, AgeCategory.B
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
        val nutritionalStatusCategory1 = GoodNutritionalStatus
        val nutritionalStatusCategory2 = PossibleRiskOfOverweight

        val entities = arrayListOf(
            createDummyRecommendationResultEntity(
                0, nutritionalStatusCategory1
            ),
            createDummyRecommendationResultEntity(
                1, nutritionalStatusCategory1
            ),
            createDummyRecommendationResultEntity(
                2, nutritionalStatusCategory1
            ),
            createDummyRecommendationResultEntity(
                3, nutritionalStatusCategory2
            ),
            createDummyRecommendationResultEntity(
                4, nutritionalStatusCategory2
            ),
        )

        val recommendationResultModel = entities.toResultModels()
        Assert.assertEquals(recommendationResultModel.count(), 5)

        val firstResult = recommendationResultModel.first()
        Assert.assertEquals(firstResult.id, 0)

        val lastResult = recommendationResultModel.last()
        Assert.assertEquals(lastResult.id, 4)
    }

    @Test
    fun `successfully convert menu model type into menu entity type`() {
        val menuModel = createDummyMenuModel(
            0, Overweight, AgeCategory.B
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
}