package id.rllyhz.giziplan.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import id.rllyhz.giziplan.R
import id.rllyhz.giziplan.data.anthropometry.type.Gender
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
            getWeightToHeightPopulationTableData(
                appContext,
                Gender.Male
            )
        val femalePopulationTableTableData =
            getWeightToHeightPopulationTableData(
                appContext,
                Gender.Female
            )

        assertTrue(malePopulationTableTableData.isNotEmpty())
        assertEquals(20, malePopulationTableTableData.size)
        assertEquals("54.5", malePopulationTableTableData.last().first())

        assertTrue(femalePopulationTableTableData.isNotEmpty())
        assertEquals(15, femalePopulationTableTableData.size)
        assertEquals("52.0", femalePopulationTableTableData.last().first())
    }
}