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
        val csvContent = readCSVFileInRawFolder(appContext, R.raw.bb_menurut_umur_laki_laki)

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
        assertEquals(30, malePopulationTableTableData.size)
        assertEquals(29.0, malePopulationTableTableData.last().first(), 0.0)

        assertTrue(femalePopulationTableTableData.isNotEmpty())
        assertEquals(25, femalePopulationTableTableData.size)
        assertEquals(24.0, femalePopulationTableTableData.last().first(), 0.0)
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
        assertEquals(25, malePopulationTableTableData.size)
        assertEquals(24.0, malePopulationTableTableData.last().first(), 0.0)

        assertTrue(femalePopulationTableTableData.isNotEmpty())
        assertEquals(20, femalePopulationTableTableData.size)
        assertEquals(19.0, femalePopulationTableTableData.last().first(), 0.0)
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
        assertEquals(54.5, malePopulationTableTableData.last().first(), 0.0)

        assertTrue(femalePopulationTableTableData.isNotEmpty())
        assertEquals(15, femalePopulationTableTableData.size)
        assertEquals(52.0, femalePopulationTableTableData.last().first(), 0.0)
    }
}