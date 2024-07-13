package id.rllyhz.giziplan.domain.usecase.anthropometry.measure

import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryDataTable
import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.data.anthropometry.type.PopulationValueType
import id.rllyhz.giziplan.data.anthropometry.type.ReferenceValueType
import id.rllyhz.giziplan.data.anthropometry.utils.toPopulationRow
import id.rllyhz.giziplan.domain.repository.AnthropometryRepository
import id.rllyhz.giziplan.domain.usecase.anthropometry.AnthropometryInteractor
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
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class AnthropometryInteractorTest {

    @Mock
    private lateinit var repository: AnthropometryRepository

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
        preparePopulation()
        interactor = AnthropometryInteractor(repository)
    }

    @Test
    fun `measureZScoreForWeightToAge - for male`() = runTest(UnconfinedTestDispatcher()) {
        Mockito.`when`(repository.getWeightToAgePopulation(Gender.Male))
            .thenReturn(weightToAgeDataTable.malePopulationTable)

        val result1 = interactor.measureZScoreForWeightToAge(
            12.0, 64, Gender.Male
        )

        Assert.assertTrue(result1.isOutOfRangePopulation)

        val result2 = interactor.measureZScoreForWeightToAge(
            11.2, // in kg
            16, // in months
            Gender.Male
        )

        Assert.assertEquals(0.58, result2.zScore, 0.0)

        val result3 = interactor.measureZScoreForWeightToAge(
            13.4, // in kg
            49, // in months
            Gender.Male
        )

        Assert.assertEquals(-1.55, result3.zScore, 0.0)
    }

    @Test
    fun `measureZScoreForWeightToAge - for female`() = runTest(UnconfinedTestDispatcher()) {
        Mockito.`when`(repository.getWeightToAgePopulation(Gender.Female))
            .thenReturn(weightToAgeDataTable.femalePopulationTable)

        val result1 = interactor.measureZScoreForWeightToAge(
            32.0, 61, Gender.Female
        )

        Assert.assertTrue(result1.isOutOfRangePopulation)

        val result2 = interactor.measureZScoreForWeightToAge(
            16.0, // in kg
            39, // in months
            Gender.Female
        )

        Assert.assertEquals(0.76, result2.zScore, 0.0)

        val result3 = interactor.measureZScoreForWeightToAge(
            11.0, // in kg
            39, // in months
            Gender.Female
        )

        Assert.assertEquals(-2.0, result3.zScore, 0.0)
    }

    @Test
    fun `measureZScoreForHeightToAge - for male`() = runTest(UnconfinedTestDispatcher()) {
        Mockito.`when`(repository.getHeightToAgePopulation(Gender.Male))
            .thenReturn(heightToAgeDataTable.malePopulationTable)

        val result1 = interactor.measureZScoreForHeightToAge(
            62.0, 64, Gender.Male
        )

        Assert.assertTrue(result1.isOutOfRangePopulation)

        val result2 = interactor.measureZScoreForHeightToAge(
            70.6, // in cm
            12, // in months
            Gender.Male
        )

        Assert.assertEquals(-2.22, result2.zScore, 0.0)

        val result3 = interactor.measureZScoreForHeightToAge(
            105.2, // in cm
            50, // in months
            Gender.Male
        )

        Assert.assertEquals(0.19, result3.zScore, 0.0)
    }

    @Test
    fun `measureZScoreForHeightToAge - for female`() = runTest(UnconfinedTestDispatcher()) {
        Mockito.`when`(repository.getHeightToAgePopulation(Gender.Female))
            .thenReturn(heightToAgeDataTable.femalePopulationTable)

        val result1 = interactor.measureZScoreForHeightToAge(
            62.0, 62, Gender.Female
        )

        Assert.assertTrue(result1.isOutOfRangePopulation)

        val result2 = interactor.measureZScoreForHeightToAge(
            64.8, // in cm
            6, // in months
            Gender.Female
        )

        Assert.assertEquals(-0.41, result2.zScore, 0.0)

        val result3 = interactor.measureZScoreForHeightToAge(
            94.7, // in cm
            34, // in months
            Gender.Female
        )

        Assert.assertEquals(0.29, result3.zScore, 0.0)
    }

    @Test
    fun `measureZScoreForWeightToHeightLessThan24 - for male`() =
        runTest(UnconfinedTestDispatcher()) {
            Mockito.`when`(repository.getWeightToHeightLessThan24Population(Gender.Male))
                .thenReturn(weightToHeightLessThan24DataTable.malePopulationTable)

            val result1 = interactor.measureZScoreForWeightToHeight(
                44.9, 2.6, true, Gender.Male
            )

            Assert.assertTrue(result1.isOutOfRangePopulation)

            val result2 = interactor.measureZScoreForWeightToHeight(
                2.8, // in kg
                48.0, // in cm
                true, Gender.Male
            )

            Assert.assertEquals(-0.5, result2.zScore, 0.0)

            val result3 = interactor.measureZScoreForWeightToHeight(
                11.2, // in kg
                80.5, // in cm
                true, Gender.Male
            )

            Assert.assertEquals(0.7, result3.zScore, 0.0)
        }

    @Test
    fun `measureZScoreForWeightToHeightLessThan24 - for female`() =
        runTest(UnconfinedTestDispatcher()) {
            Mockito.`when`(repository.getWeightToHeightLessThan24Population(Gender.Female))
                .thenReturn(weightToHeightLessThan24DataTable.femalePopulationTable)

            val result1 = interactor.measureZScoreForWeightToHeight(
                42.5, 2.6, true, Gender.Female
            )

            Assert.assertTrue(result1.isOutOfRangePopulation)

            val result2 = interactor.measureZScoreForWeightToHeight(
                4.2, // in kg
                50.0, // in cm
                true, Gender.Female
            )

            Assert.assertEquals(2.67, result2.zScore, 0.0)

            val result3 = interactor.measureZScoreForWeightToHeight(
                18.3, // in kg
                108.0, // in cm
                true, Gender.Female
            )

            Assert.assertEquals(0.41, result3.zScore, 0.0)
        }

    // TODO change assertion statements to adapt to the greater than 24 population
    @Test
    fun `measureZScoreForWeightToHeightGreaterThan24 - for male`() =
        runTest(UnconfinedTestDispatcher()) {
            Mockito.`when`(repository.getWeightToHeightGreaterThan24Population(Gender.Male))
                .thenReturn(weightToHeightGreaterThan24DataTable.malePopulationTable)

            val result1 = interactor.measureZScoreForWeightToHeight(
                64.9, 7.4, false, Gender.Male
            )

            Assert.assertTrue(result1.isOutOfRangePopulation)

            val result2 = interactor.measureZScoreForWeightToHeight(
                7.8, // in kg
                67.0, // in cm
                false, Gender.Male
            )

            Assert.assertEquals(-0.17, result2.zScore, 0.0)

            val result3 = interactor.measureZScoreForWeightToHeight(
                10.2, // in kg
                70.5, // in cm
                false, Gender.Male
            )

            Assert.assertEquals(1.87, result3.zScore, 0.0)
        }

    // TODO change assertion statements to adapt to the greater than 24 population
    @Test
    fun `measureZScoreForWeightToHeightGreaterThan24 - for female`() =
        runTest(UnconfinedTestDispatcher()) {
            Mockito.`when`(repository.getWeightToHeightGreaterThan24Population(Gender.Female))
                .thenReturn(weightToHeightGreaterThan24DataTable.femalePopulationTable)

            val result1 = interactor.measureZScoreForWeightToHeight(
                2.6, 62.0, false, Gender.Female
            )

            Assert.assertTrue(result1.isOutOfRangePopulation)

            val result2 = interactor.measureZScoreForWeightToHeight(
                6.4, // in kg
                66.0, // in cm
                false, Gender.Female
            )

            Assert.assertEquals(-2.17, result2.zScore, 0.0)

            val result3 = interactor.measureZScoreForWeightToHeight(
                18.3, // in kg
                108.0, // in cm
                false, Gender.Female
            )

            Assert.assertEquals(0.29, result3.zScore, 0.0)
        }
}