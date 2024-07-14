package id.rllyhz.giziplan.data.local

import id.rllyhz.giziplan.utils.createDummyMenuEntities
import id.rllyhz.giziplan.utils.createDummyMenuEntity
import id.rllyhz.giziplan.utils.createDummyRecommendationResultEntities
import id.rllyhz.giziplan.domain.utils.toModels
import id.rllyhz.giziplan.utils.fakes.FakeGiziDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class LocalDataSourceTest {
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var giziDao: FakeGiziDao

    @Before
    fun setup() {
        localDataSource = LocalDataSource(giziDao)
    }

    /*
     * GET ALL MENUS - testcase
     */
    @Test
    fun `successfully get all menus`() = runTest(UnconfinedTestDispatcher()) {
        val length = 5
        val menuEntities = createDummyMenuEntities(length)
        val menuModels = menuEntities.toModels()

        Mockito.`when`(giziDao.getAllMenus()).thenReturn(menuEntities)

        val data = localDataSource.getAllMenus()

        Assert.assertEquals(length, data.count())
        Assert.assertEquals(
            menuModels.first().id, data.first().id
        )
        Assert.assertEquals(
            menuModels.last().id, data.last().id
        )
    }

    /*
     * GET MENU BY ID - testcase
     */
    @Test
    fun `successfully get menu by id`() = runTest(UnconfinedTestDispatcher()) {
        val menuId = 3
        val menu = createDummyMenuEntity(id = menuId)

        Mockito.`when`(giziDao.getMenuById(menuId)).thenReturn(menu)

        val data = localDataSource.getMenuById(menuId)

        Assert.assertEquals(menu.id, data?.id)
    }

    /*
     * GET ALL RECOMMENDATION RESULTS - testcase
     */
    @Test
    fun `successfully get all recommendation results`() = runTest {
        val length = 5
        val recommendationResultsEntities = createDummyRecommendationResultEntities(length)

        Mockito.`when`(giziDao.getAllMeasureResults())
            .thenReturn(recommendationResultsEntities)

        val data = localDataSource.getAllMeasureResults()

        Assert.assertEquals(length, data.count())
        Assert.assertEquals(
            recommendationResultsEntities.first().id,
            data.first().id
        )
        Assert.assertEquals(
            recommendationResultsEntities.last().id,
            data.last().id
        )
    }
}