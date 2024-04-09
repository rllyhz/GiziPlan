package id.rllyhz.giziplan.domain.repository

import id.rllyhz.giziplan.createDummyMenuEntities
import id.rllyhz.giziplan.createDummyMenuModels
import id.rllyhz.giziplan.data.GiziRepositoryImpl
import id.rllyhz.giziplan.domain.utils.DataState
import id.rllyhz.giziplan.domain.utils.toEntities
import id.rllyhz.giziplan.domain.utils.toModels
import id.rllyhz.giziplan.utils.CoroutineTestRule
import id.rllyhz.giziplan.utils.fakes.FakeGiziDao
import id.rllyhz.giziplan.utils.fakes.FakeLocalDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.advanceUntilIdle
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
class GiziRepositoryTest {
    private lateinit var repository: GiziRepository

    @Mock
    private lateinit var giziDao: FakeGiziDao

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Before
    fun setup() {
        repository = GiziRepositoryImpl(
            FakeLocalDataSource(giziDao),
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
        advanceUntilIdle()

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
        advanceUntilIdle()

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
        advanceUntilIdle()

        Assert.assertTrue(firstEmit is DataState.Loading)
        Assert.assertTrue(actualData is DataState.Success)

        Assert.assertNotNull(actualData.data)
        Assert.assertNull(actualData.error)

        val value = actualData.data

        Assert.assertEquals(expectedValue, value)
    }

    /*
     * GET MENU BY ID - testcase
     */

    /*
     * DELETE ALL MENUS - testcase
     */

    /*
     * GET ALL RECOMMENDATION RESULTS - testcase
     */

    /*
     * DELETE RECOMMENDATION RESULTS OF - testcase
     */

    /*
     * DELETE ALL RECOMMENDATION RESULTS - testcase
     */
}