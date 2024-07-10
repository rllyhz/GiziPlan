package id.rllyhz.giziplan.recommender

import id.rllyhz.giziplan.utils.fakeMenuCsvStringContent
import org.apache.commons.csv.CSVFormat
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.StringReader

class RecommenderTest {
    private var _menuList: List<List<String>>? = null
    private val menuList: List<List<String>> get() = _menuList!!

    @Before
    fun setup() {
        if (_menuList == null) {
            _menuList = getMenu()
        }
    }

    @Test
    fun `successfully recommend related menu`() {
        val overview = menuList.map { it.last() }
        val expectedNutritionStatus = "normal"
        val expectedAge = 32
        val topN = 5

        val relatedIndexes =
            Recommender.getRecommendation(expectedNutritionStatus, expectedAge, overview, topN)
        val relatedMenuList = menuList.filterIndexed { idx, _ -> relatedIndexes.contains(idx) }

        Assert.assertEquals(topN, relatedMenuList.size)

        relatedMenuList.forEach {
            val nutritionStatus = it[2].lowercase()
            Assert.assertTrue(nutritionStatus.contains(expectedNutritionStatus))
        }
    }

    private fun getMenu(): List<List<String>> {
        val data = arrayListOf<List<String>>()
        val parser = CSVFormat.DEFAULT.parse(StringReader(fakeMenuCsvStringContent))

        parser.forEachIndexed { index, row ->
            if (index <= 0) return@forEachIndexed

            val temp = arrayListOf<String>()

            val name = row[0].trim()
            temp.add(name)

            val age = row[1].trim().lowercase()
            temp.add(age)

            val nutritionalStatus = row[2].trim()
            temp.add(nutritionalStatus)

            val ingredients = row[3].trim().trim { it == ';' }
            temp.add(ingredients)

            val instructions = row[4].trim().trim { it == ';' }
            temp.add(instructions)

            val nutritionInfo = row[5].trim().trim { it == ';' }
            temp.add(nutritionInfo)

            val notes = row[6].trim().trim { it == ';' }
            temp.add(notes)

            var status = nutritionalStatus
            if (nutritionalStatus.contains(",")) {
                status = nutritionalStatus.split(",").joinToString(" dan ")
            }

            val overview = "status gizi ${status.lowercase()} untuk umur ${age.lowercase()}"
            temp.add(overview)

            data.add(temp)
        }

        return data
    }
}