package id.rllyhz.giziplan.domain.utils

import id.rllyhz.giziplan.createDummyMenuEntity
import id.rllyhz.giziplan.createDummyMenuModel
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

        val recommendationResultModel = entities.toModel()
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