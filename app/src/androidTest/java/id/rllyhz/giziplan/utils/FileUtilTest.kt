package id.rllyhz.giziplan.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import id.rllyhz.giziplan.R
import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.data.local.db.utils.toMenuEntity
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FileUtilTest {
    @Test
    fun getCSVFileInRawFolder() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val csvContent = readCSVFileInRawFolder(appContext, R.raw.lklk_bb_per_u_0_60)

        assertTrue(csvContent.isNotEmpty())
    }

    @Test
    fun getMenuData() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val csvContent = getMenuDataByCSVFile(appContext)

        val menuData = csvContent.toMenuEntity()

        assertTrue(csvContent.isNotEmpty())
        assertTrue(menuData.isNotEmpty())
        assertEquals(60, menuData.size)

        assertEquals("Bubur Singkong Kukuruyuk Saus Jeruk", menuData.first().name)
        assertEquals("normal", menuData.first().nutritionalStatusCategory)
        assertEquals("A", menuData.first().ageCategory)

        assertEquals("Mie Goreng Telur Puyuh", menuData.last().name)
        assertEquals("lebih", menuData.last().nutritionalStatusCategory)
        assertEquals("D", menuData.last().ageCategory)
    }

    @Test
    fun getWeightToAgePopulationTableData() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val malePopulationTableTableData =
            getWeightToAgePopulationTableData(
                appContext,
                Gender.Male
            )
        val femalePopulationTableTableData =
            getWeightToAgePopulationTableData(
                appContext,
                Gender.Female
            )

        assertTrue(malePopulationTableTableData.isNotEmpty())
        assertEquals(61, malePopulationTableTableData.size)
        assertEquals("60", malePopulationTableTableData.last().first())
        assertEquals("27.9", malePopulationTableTableData.last().last())

        assertTrue(femalePopulationTableTableData.isNotEmpty())
        assertEquals(61, femalePopulationTableTableData.size)
        assertEquals("60", femalePopulationTableTableData.last().first())
        assertEquals("29.5", femalePopulationTableTableData.last().last())
    }

    @Test
    fun getHeightToAgePopulationTableData() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val malePopulationTableTableData =
            getHeightToAgePopulationTableData(
                appContext,
                Gender.Male
            )
        val femalePopulationTableTableData =
            getHeightToAgePopulationTableData(
                appContext,
                Gender.Female
            )

        assertTrue(malePopulationTableTableData.isNotEmpty())
        assertEquals(61, malePopulationTableTableData.size)
        assertEquals("60", malePopulationTableTableData.last().first())
        assertEquals("123.9", malePopulationTableTableData.last().last())

        assertTrue(femalePopulationTableTableData.isNotEmpty())
        assertEquals(61, femalePopulationTableTableData.size)
        assertEquals("60", femalePopulationTableTableData.last().first())
        assertEquals("123.7", femalePopulationTableTableData.last().last())
    }

    @Test
    fun getWeightToHeightPopulationTableData() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val malePopulationTableTableData =
            getWeightToHeightLessThan24PopulationTableData(
                appContext,
                Gender.Male
            )
        val femalePopulationTableTableData =
            getWeightToHeightLessThan24PopulationTableData(
                appContext,
                Gender.Female
            )

        assertTrue(malePopulationTableTableData.isNotEmpty())
        assertEquals(131, malePopulationTableTableData.size)
        assertEquals("110.0", malePopulationTableTableData.last().first())

        assertTrue(femalePopulationTableTableData.isNotEmpty())
        assertEquals(131, femalePopulationTableTableData.size)
        assertEquals("110.0", femalePopulationTableTableData.last().first())
    }
}