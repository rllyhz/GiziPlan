package id.rllyhz.giziplan.recommender

import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryDataTable
import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.data.anthropometry.type.PopulationValueType
import id.rllyhz.giziplan.data.anthropometry.type.ReferenceValueType
import id.rllyhz.giziplan.data.anthropometry.utils.toPopulationRow
import id.rllyhz.giziplan.domain.model.classification.SeverelyWasted
import id.rllyhz.giziplan.domain.usecase.anthropometry.AnthropometryInteractor
import id.rllyhz.giziplan.utils.fakes.FakeRepository
import id.rllyhz.giziplan.utils.femaleHeightToAgeCSVContent
import id.rllyhz.giziplan.utils.femaleWeightToAgeCSVContent
import id.rllyhz.giziplan.utils.femaleWeightToHeight_0_24_CSVContent
import id.rllyhz.giziplan.utils.femaleWeightToHeight_24_60_CSVContent
import id.rllyhz.giziplan.utils.maleHeightToAgeCSVContent
import id.rllyhz.giziplan.utils.maleWeightToAgeCSVContent
import id.rllyhz.giziplan.utils.maleWeightToHeight_0_24_CSVContent
import id.rllyhz.giziplan.utils.maleWeightToHeight_24_60_CSVContent
import id.rllyhz.giziplan.utils.parseCSVContentToPopulationDataTable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class RecommenderEvalTest {
    private var _menuList: List<List<String>>? = null
    private val menuList: List<List<String>> get() = _menuList!!

    private var _dataTest: List<List<String>>? = null
    private val dataTest: List<List<String>> get() = _dataTest!!

    private lateinit var interactor: AnthropometryInteractor

    private lateinit var weightToAgeDataTable: AnthropometryDataTable
    private lateinit var heightToAgeDataTable: AnthropometryDataTable
    private lateinit var weightToHeightLessThan24DataTable: AnthropometryDataTable
    private lateinit var weightToHeightGreaterThan24DataTable: AnthropometryDataTable

    private fun preparePopulation() {
        if (this::weightToAgeDataTable.isInitialized && this::heightToAgeDataTable.isInitialized && this::weightToHeightLessThan24DataTable.isInitialized && this::weightToHeightGreaterThan24DataTable.isInitialized) return

        // Setup Weight To Age Data Table
        val weightToAgeMaleDataTable =
            parseCSVContentToPopulationDataTable(maleWeightToAgeCSVContent)
        val weightToAgeFemaleDataTable =
            parseCSVContentToPopulationDataTable(femaleWeightToAgeCSVContent)

        weightToAgeDataTable = AnthropometryDataTable(
            ReferenceValueType.AgeInMonths,
            PopulationValueType.WeightInKilograms,
            weightToAgeMaleDataTable.toPopulationRow(
                ReferenceValueType.AgeInMonths,
                PopulationValueType.WeightInKilograms,
            ),
            weightToAgeFemaleDataTable.toPopulationRow(
                ReferenceValueType.AgeInMonths,
                PopulationValueType.WeightInKilograms,
            ),
        )

        // Setup Height To Age Data Table
        val heightToAgeMaleDataTable =
            parseCSVContentToPopulationDataTable(maleHeightToAgeCSVContent)
        val heightToAgeFemaleDataTable =
            parseCSVContentToPopulationDataTable(femaleHeightToAgeCSVContent)

        heightToAgeDataTable = AnthropometryDataTable(
            ReferenceValueType.AgeInMonths,
            PopulationValueType.HeightInCentimeters,
            heightToAgeMaleDataTable.toPopulationRow(
                ReferenceValueType.AgeInMonths,
                PopulationValueType.HeightInCentimeters,
            ),
            heightToAgeFemaleDataTable.toPopulationRow(
                ReferenceValueType.AgeInMonths,
                PopulationValueType.HeightInCentimeters,
            ),
        )

        // Setup Weight To Height Data Table
        val weightToHeightLessThan24MaleDataTable =
            parseCSVContentToPopulationDataTable(maleWeightToHeight_0_24_CSVContent)
        val weightToHeightGreaterThan24MaleDataTable =
            parseCSVContentToPopulationDataTable(maleWeightToHeight_24_60_CSVContent)
        val weightToHeightLessThan24FemaleDataTable =
            parseCSVContentToPopulationDataTable(femaleWeightToHeight_0_24_CSVContent)
        val weightToHeightGreaterThan24FemaleDataTable =
            parseCSVContentToPopulationDataTable(femaleWeightToHeight_24_60_CSVContent)

        weightToHeightLessThan24DataTable = AnthropometryDataTable(
            ReferenceValueType.HeightInCentimeters,
            PopulationValueType.WeightInKilograms,
            weightToHeightLessThan24MaleDataTable.toPopulationRow(
                ReferenceValueType.HeightInCentimeters,
                PopulationValueType.WeightInKilograms,
            ),
            weightToHeightLessThan24FemaleDataTable.toPopulationRow(
                ReferenceValueType.HeightInCentimeters,
                PopulationValueType.WeightInKilograms,
            ),
        )

        weightToHeightGreaterThan24DataTable = AnthropometryDataTable(
            ReferenceValueType.HeightInCentimeters,
            PopulationValueType.WeightInKilograms,
            weightToHeightGreaterThan24MaleDataTable.toPopulationRow(
                ReferenceValueType.HeightInCentimeters,
                PopulationValueType.WeightInKilograms,
            ),
            weightToHeightGreaterThan24MaleDataTable.toPopulationRow(
                ReferenceValueType.HeightInCentimeters,
                PopulationValueType.WeightInKilograms,
            ),
        )
    }

    @Before
    fun setup() {
        if (_menuList == null) {
            _menuList = getMenu()
        }
        if (_dataTest == null) {
            _dataTest = getValidationData()
        }
        if (!this::interactor.isInitialized) {
            preparePopulation()
            val repository = FakeRepository(
                weightToAgeDataTable,
                heightToAgeDataTable,
                weightToHeightLessThan24DataTable,
                weightToHeightGreaterThan24DataTable
            )
            interactor = AnthropometryInteractor(repository)
        }
    }

    @Test
    fun `successfully get validation data test`() = runTest(UnconfinedTestDispatcher()) {
        val dataTest = getValidationData()
        Assert.assertEquals(20, dataTest.size)
    }

    @Test
    fun `verify the first validation data`() = runTest(UnconfinedTestDispatcher()) {
        val first = dataTest[0]
        val age = first[1].toIntOrNull() ?: 0
        val gender = if (first[2] == "Laki-laki") Gender.Male else Gender.Female
        val height = first[3].toDoubleOrNull() ?: 0.0
        val weight = first[4].toDoubleOrNull() ?: 0.0
        val expectedNutritionalStatus = first[5]
        val totalRecommendationMenu = first[6].toIntOrNull() ?: 0

        val zScoreData = interactor.measureZScoreForWeightToHeight(
            weight, height, age < 24, gender
        )
        val classificationData = interactor.classifyZScore(zScoreData)

        Assert.assertEquals(false, classificationData.zScoreInvalid)
        Assert.assertEquals(
            SeverelyWasted.getClassificationName(),
            classificationData.classificationResult.getClassificationName()
        )
    }

    @Test
    fun `verify classification result of the first validation data`() =
        runTest(UnconfinedTestDispatcher()) {
            val menuOverviews = menuList.map { it.last() }
            val nTop = 3

            val first = dataTest[0]
            val age = first[1].toIntOrNull() ?: 0
            val gender = if (first[2] == "Laki-laki") Gender.Male else Gender.Female
            val height = first[3].toDoubleOrNull() ?: 0.0
            val weight = first[4].toDoubleOrNull() ?: 0.0
            val expectedNutritionalStatus = first[5]
            val totalRecommendationMenu = first[6].toIntOrNull() ?: 0

            val zScoreData = interactor.measureZScoreForWeightToHeight(
                weight, height, age < 24, gender
            )
            val classificationData = interactor.classifyZScore(zScoreData)

            // get recommendation result
            val recommendedIndexes = Recommender.getRecommendation(
                expectedNutritionalStatus, age, menuOverviews, nTop
            )
            val recommendedMenu =
                menuList.filterIndexed { menuIndex, _ -> recommendedIndexes.contains(menuIndex) }

            recommendedMenu.forEach {
                Assert.assertEquals(
                    expectedNutritionalStatus.lowercase(),
                    it[2].lowercase()
                )
            }
        }

    @Test
    fun `evaluate recommender system using f1-score`() =
        runTest(UnconfinedTestDispatcher()) {
            val menuOverviews = menuList.map { it.last() }
            val nTop = 3

            var tp = 0
            var fp = 0
            var fn = 0
            var expectedTotalMenuFound = 0
            var actualTotalMenuFound = 0

            dataTest.forEachIndexed { _, data ->
                // extract columns
                val age = data[1].toIntOrNull() ?: 0
                val gender = if (data[2] == "Laki-laki") Gender.Male else Gender.Female
                val height = data[3].toDoubleOrNull() ?: 0.0
                val weight = data[4].toDoubleOrNull() ?: 0.0
                val actualNutritionalStatus = data[5].lowercase()
                val actualTotalRecommendationMenu = data[6].toIntOrNull() ?: 0
                expectedTotalMenuFound += actualTotalRecommendationMenu

                // measure and classify z-score value
                val zScoreData = interactor.measureZScoreForWeightToHeight(
                    weight, height, age < 24, gender
                )
                val classificationData = interactor.classifyZScore(zScoreData)

                // get recommendation result
                val recommendedIndexes = Recommender.getRecommendation(
                    actualNutritionalStatus, age, menuOverviews, nTop
                )
                val recommendedMenu =
                    menuList.filterIndexed { menuIndex, _ -> recommendedIndexes.contains(menuIndex) }
                actualTotalMenuFound += recommendedMenu.size

                Assert.assertEquals(nTop, recommendedMenu.size)

                // menu[2] => nutritional status on each menu
                val foundTP =
                    recommendedMenu.count { menu -> menu[2].lowercase() == actualNutritionalStatus }
                val foundFP =
                    recommendedMenu.count { menu -> menu[2].lowercase() != actualNutritionalStatus }
                val foundFN = if (actualTotalRecommendationMenu >= foundTP) {
                    actualTotalRecommendationMenu - foundTP
                } else 0

                tp += foundTP
                fp += foundFP
                fn += foundFN
            }

            println("\n\n")
            println("TP: $tp")
            println("FP: $fp")
            println("FN: $fn")
            println("Expected total menu found: $expectedTotalMenuFound")
            println("Actual total menu found: $actualTotalMenuFound")
            println("\n\n")
        }
}