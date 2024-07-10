package id.rllyhz.giziplan.recommender

import id.rllyhz.giziplan.utils.fakeMenuCsvStringContent
import org.apache.commons.csv.CSVFormat
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.StringReader
import kotlin.system.measureTimeMillis

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
        val expectedNutritionStatus = "buruk"
        val expectedAge = 12
        val topN = 5

        val relatedIndexes =
            Recommender.getRecommendation(expectedNutritionStatus, expectedAge, overview, topN)
        val relatedMenuList = menuList.filterIndexed { idx, _ -> relatedIndexes.contains(idx) }

        Assert.assertEquals(topN, relatedMenuList.size)

//        relatedMenuList.forEach {
//            val nutritionStatus = it[2].lowercase()
//            Assert.assertTrue(nutritionStatus.contains(expectedNutritionStatus))
//        }
    }

    @Test
    fun `measure execution time`() {
        val overview = menuList.map { it.last() }
        val expectedNutritionStatus = "normal"
        val expectedAge = 32
        val topN = 5

        val executionTime = measureTimeMillis {
            val relatedIndexes =
                Recommender.getRecommendation(expectedNutritionStatus, expectedAge, overview, topN)
            menuList.filterIndexed { idx, _ -> relatedIndexes.contains(idx) }
        }

        println("\n\nTotal data     : ${menuList.size}")
        println("Execution time : ${executionTime}ms\n\n")

        Assert.assertTrue(executionTime < 100)
    }

    @Test
    fun `evaluate recommender`() {
        data class TestData(
            val nutritionStatus: String,
            val ageInMonths: Int,
            val expectedMenu: List<String>,
            val isActual: Boolean = true
        )

        val menuOverviews = menuList.map { it.last() }

        val menuForNormal = menuList.find { it[2].lowercase().contains("normal") } ?: return
        val menuForWasted = menuList.find { it[2].lowercase().contains("buruk") } ?: return
        val menuForOver = menuList.find { it[2].lowercase().contains("lebih") } ?: return
        val menuForObese = menuList.find { it[2].lowercase().contains("obesitas") } ?: return

        val testData = listOf(
            // positive
            TestData("normal", 8, menuForNormal),
            TestData("normal", 23, menuForNormal),
            TestData("buruk", 6, menuForWasted),
            TestData("buruk", 27, menuForWasted),
            TestData("lebih", 32, menuForOver),
            TestData("lebih", 12, menuForOver),
            TestData("obesitas", 7, menuForObese),
            TestData("obesitas", 42, menuForObese),
            // negative
            TestData("lebih", 14, menuForNormal, false),
            TestData("obesitas", 14, menuForNormal, false),
            TestData("normal", 14, menuForWasted, false),
            TestData("normal", 14, menuForOver, false),
            TestData("lebih", 14, menuForNormal, false),
        )

        val predictedMenus = arrayListOf<List<String>>()
        val actualMenus = arrayListOf<List<String>>()

        for (testDt in testData) {
            val nutritionStatus = testDt.nutritionStatus
            val ageInMonths = testDt.ageInMonths
            val actualMenu = testDt.expectedMenu

            actualMenus.add(actualMenu)

            // from recommendation
            val menuIndexes =
                Recommender.getRecommendation(nutritionStatus, ageInMonths, menuOverviews, 1)

            val predictedIndex = menuIndexes[0] // only one
            val predictedMenu = menuList[predictedIndex]
            predictedMenus.add(predictedMenu)
        }

        var truePositives = 0
        var falsePositives = 0
        var trueNegatives = 0
        var falseNegatives = 0

        for (i in 0..<actualMenus.size) {
            val predictedNutritionStatus = predictedMenus[i][2]
            val predictedAgeInMonths = predictedMenus[i][1]

            val actualNutritionStatus = actualMenus[i][2]
            val actualAgeInMonths = actualMenus[i][1]

            val doesNutritionStatusTheSame =
                actualNutritionStatus.contains(predictedNutritionStatus)
            val doesAgeTheSame = predictedAgeInMonths == actualAgeInMonths

            val correctlyPredicted = doesNutritionStatusTheSame && doesAgeTheSame

            if (testData[i].isActual && correctlyPredicted) {
                truePositives += 1
            } else if (testData[i].isActual && !correctlyPredicted) {
                falseNegatives += 1
            } else if (!testData[i].isActual && correctlyPredicted) {
                falsePositives += 1
            } else {
                trueNegatives += 1
            }
        }

        // Measure Precision dan Recall
        val precision = precisionScore(truePositives, falsePositives)
        val recall = recallScore(truePositives, falsePositives)

        println("\n\n\nPrecision  : $precision")
        println("Recall   : $recall\n\n\n")
    }

    private fun precisionScore(truePositives: Int, falsePositives: Int): Double {
        if (truePositives + falsePositives == 0) return 0.0
        return truePositives.toDouble() / (truePositives + falsePositives)
    }

    private fun recallScore(truePositives: Int, falseNegatives: Int): Double {
        if (truePositives + falseNegatives == 0) return 0.0
        return truePositives.toDouble() / (truePositives + falseNegatives)
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