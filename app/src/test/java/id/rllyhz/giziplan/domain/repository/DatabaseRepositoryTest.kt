package id.rllyhz.giziplan.domain.repository

import android.content.Context
import id.rllyhz.giziplan.data.DatabaseRepositoryImpl
import id.rllyhz.giziplan.domain.utils.DataState
import id.rllyhz.giziplan.domain.utils.toEntities
import id.rllyhz.giziplan.domain.utils.toModels
import id.rllyhz.giziplan.domain.utils.toResultModels
import id.rllyhz.giziplan.utils.CoroutineTestRule
import id.rllyhz.giziplan.utils.createDummyMenuEntities
import id.rllyhz.giziplan.utils.createDummyMenuEntity
import id.rllyhz.giziplan.utils.createDummyMenuModels
import id.rllyhz.giziplan.utils.createDummyRecommendationResultEntities
import id.rllyhz.giziplan.utils.fakes.FakeGiziDao
import id.rllyhz.giziplan.utils.fakes.FakeLocalDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class DatabaseRepositoryTest {
    private lateinit var repository: DatabaseRepository

    @Mock
    private lateinit var giziDao: FakeGiziDao

    @Mock
    private lateinit var context: Context

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Before
    fun setup() {
        repository = DatabaseRepositoryImpl(
            FakeLocalDataSource(giziDao),
            UnconfinedTestDispatcher(),
            true,
        )
    }

    /*
     * GET ALL MENUS - testcase
     */
    @Test
    fun `successfully get all menus`() = runTest {
        val menuEntities = createDummyMenuEntities(5)
        val expectedMenuModels = menuEntities.toModels()
        val expectedCount = expectedMenuModels.count()

        Mockito.`when`(giziDao.getAllMenus())
            .thenReturn(menuEntities)

        val result = repository.getAllMenus()
        val firstEmit = result.first()
        val actualData = result.last()

        Assert.assertTrue(firstEmit is DataState.Loading)
        Assert.assertTrue(actualData is DataState.Success)

        Assert.assertNotNull(actualData.data)
        Assert.assertNull(actualData.error)

        Assert.assertEquals(expectedCount, actualData.data?.count())

        Assert.assertEquals(
            expectedMenuModels.first().id,
            actualData.data?.first()?.id,
        )
        Assert.assertEquals(
            expectedMenuModels.last().id,
            actualData.data?.last()?.id,
        )
    }

    @Test
    fun `successfully handling exceptions when getAllMenus() thrown an exception`() = runTest {
        val expectedExceptionMessage = "Can't get all menus"

        Mockito.`when`(giziDao.getAllMenus())
            .thenThrow(RuntimeException(expectedExceptionMessage))

        val result = repository.getAllMenus()
        val firstEmit = result.first()
        val actualData = result.last()

        Assert.assertTrue(firstEmit is DataState.Loading)
        Assert.assertNull(actualData.data)
        Assert.assertNotNull(actualData.error)
        Assert.assertEquals(
            expectedExceptionMessage, actualData.error?.exception?.message,
        )
    }

    /*
     * INSERT ALL MENUS - testcase
     */
    @Test
    fun `successfully insert all menus`() = runTest {
        val menuModels = createDummyMenuModels(5)
        val menuEntities = menuModels.toEntities()
        val expectedValue = true

        Mockito.`when`(giziDao.insertAllMenus(menuEntities))
            .thenReturn(Unit)

        val result = repository.insertAllMenus(menuModels)
        val firstEmit = result.first()
        val actualData = result.last()

        Assert.assertTrue(firstEmit is DataState.Loading)
        Assert.assertTrue(actualData is DataState.Success)

        Assert.assertNotNull(actualData.data)
        Assert.assertNull(actualData.error)

        val value = actualData.data

        Assert.assertEquals(expectedValue, value)
    }

    @Test
    fun `successfully handle when insertAllMenus throw an exception`() = runTest {
        val menuModels = createDummyMenuModels(5)
        val menuEntities = menuModels.toEntities()
        val expectedExceptionMessage = "Can't insert all menus"

        Mockito.`when`(giziDao.insertAllMenus(menuEntities))
            .thenThrow(RuntimeException(expectedExceptionMessage))

        val result = repository.insertAllMenus(menuModels)
        val firstEmit = result.first()
        val actualData = result.last()

        Assert.assertTrue(firstEmit is DataState.Loading)
        Assert.assertTrue(actualData is DataState.Error)

        Assert.assertNull(actualData.data)
        Assert.assertNotNull(actualData.error)

        Assert.assertEquals(
            expectedExceptionMessage, actualData.error?.exception?.message,
        )
    }

    /*
     * GET MENU BY ID - testcase
     */
    @Test
    fun `successfully get menu by id`() = runTest {
        val menuId = 3

        Mockito.`when`(giziDao.getMenuById(menuId))
            .thenReturn(
                createDummyMenuEntity(
                    id = menuId,
                )
            )

        val result = repository.getMenuById(menuId)
        val actualData = result.last()

        Assert.assertTrue(actualData is DataState.SuccessWithNullableData)
        Assert.assertNotNull(actualData.data)
        Assert.assertEquals(menuId, actualData.data?.id)
    }

    @Test
    fun `successfully handle when getMenuById returns null value`() = runTest {
        val menuId = 3

        Mockito.`when`(giziDao.getMenuById(menuId))
            .thenReturn(null)

        val result = repository.getMenuById(menuId)
        val actualData = result.last()

        Assert.assertTrue(actualData is DataState.SuccessWithNullableData)
        Assert.assertNull(actualData.data)
    }

    /*
     * DELETE ALL MENUS - testcase
     */
    @Test
    fun `successfully delete all menus`() = runTest {
        Mockito.`when`(giziDao.deleteAllMenus()).thenReturn(Unit)

        val result = repository.deleteAllMenus()
        val actualData = result.last()

        Assert.assertTrue(actualData is DataState.Success)
        Assert.assertNotNull(actualData.data)
    }

    @Test
    fun `successfully handle when deleteAllMenus throws an exception`() = runTest {
        val message = "Can't delete all menus"

        Mockito.`when`(giziDao.deleteAllMenus())
            .thenThrow(RuntimeException(message))

        val result = repository.deleteAllMenus()
        val actualData = result.last()

        Assert.assertNull(actualData.data)
        Assert.assertNotNull(actualData.error)

        Assert.assertEquals(
            message, actualData.error?.exception?.message,
        )
    }

    /*
     * GET ALL RECOMMENDATION RESULTS - testcase
     */
    @Test
    fun `successfully get all recommendation results`() = runTest {
        val length = 5
        val recommendationResultEntities = createDummyRecommendationResultEntities(length)
        val recommendationResultModels = recommendationResultEntities.toResultModels()

        Mockito.`when`(giziDao.getAllRecommendationResults())
            .thenReturn(recommendationResultEntities)

        val result = repository.getAllRecommendationResults()
        val actualData = result.last()

        Assert.assertTrue(actualData is DataState.Success)
        Assert.assertNotNull(actualData.data)

        Assert.assertEquals(
            recommendationResultModels.count(),
            actualData.data?.count()
        )

        Assert.assertEquals(
            recommendationResultModels.first().resultId,
            actualData.data?.first()?.resultId
        )

        Assert.assertEquals(
            recommendationResultModels.last().resultId,
            actualData.data?.last()?.resultId
        )
    }

    @Test
    fun `successfully handle when getAllRecommendationResults throws an exception`() = runTest {
        val message = "Can't get all recommendation results"

        Mockito.`when`(giziDao.getAllRecommendationResults())
            .thenThrow(RuntimeException(message))

        val result = repository.getAllRecommendationResults()
        val actualData = result.last()

        Assert.assertNull(actualData.data)
        Assert.assertNotNull(actualData.error)

        Assert.assertEquals(
            message, actualData.error?.exception?.message,
        )
    }

    /*
     * DELETE RECOMMENDATION RESULTS OF - testcase
     */
    @Test
    fun `successfully delete recommendation results of`() = runTest {
        val resultId = 3
        Mockito.`when`(giziDao.deleteRecommendationResultOf(resultId)).thenReturn(Unit)

        val result = repository.deleteRecommendationResultOf(resultId)
        val actualData = result.last()

        Assert.assertTrue(actualData is DataState.Success)
        Assert.assertNotNull(actualData.data)
    }

    @Test
    fun `successfully handle when deleteRecommendationResultsOf throws an exception`() = runTest {
        val resultId = 3
        val message = "Can't delete recommendation results of given resultId"

        Mockito.`when`(giziDao.deleteRecommendationResultOf(resultId))
            .thenThrow(RuntimeException(message))

        val result = repository.deleteRecommendationResultOf(resultId)
        val actualData = result.last()

        Assert.assertNull(actualData.data)
        Assert.assertNotNull(actualData.error)

        Assert.assertEquals(
            message, actualData.error?.exception?.message,
        )
    }

    /*
     * DELETE ALL RECOMMENDATION RESULTS - testcase
     */
    @Test
    fun `successfully delete all recommendation results`() = runTest {
        Mockito.`when`(giziDao.deleteAllRecommendationResults()).thenReturn(Unit)

        val result = repository.deleteAllRecommendationResults()
        val actualData = result.last()

        Assert.assertTrue(actualData is DataState.Success)
        Assert.assertNotNull(actualData.data)
    }

    @Test
    fun `successfully handle when deleteAllRecommendationResults throws an exception`() = runTest {
        val message = "Can't delete all recommendation results"

        Mockito.`when`(giziDao.deleteAllRecommendationResults())
            .thenThrow(RuntimeException(message))

        val result = repository.deleteAllRecommendationResults()
        val actualData = result.last()

        Assert.assertNull(actualData.data)
        Assert.assertNotNull(actualData.error)

        Assert.assertEquals(
            message, actualData.error?.exception?.message,
        )
    }
}