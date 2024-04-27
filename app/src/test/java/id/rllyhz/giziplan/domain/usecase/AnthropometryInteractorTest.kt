package id.rllyhz.giziplan.domain.usecase

import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryDataTable
import id.rllyhz.giziplan.data.anthropometry.model.PopulationRow
import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.data.anthropometry.type.PopulationValueType
import id.rllyhz.giziplan.data.anthropometry.type.ReferenceValueType
import id.rllyhz.giziplan.data.anthropometry.utils.toPopulationRow
import id.rllyhz.giziplan.domain.repository.AnthropometryRepository
import id.rllyhz.giziplan.domain.usecase.anthropometry.AnthropometryInteractor
import id.rllyhz.giziplan.utils.femaleWeightToAgeCSVContent
import id.rllyhz.giziplan.utils.maleWeightToAgeCSVContent
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

    private lateinit var malePopulation: List<PopulationRow>
    private lateinit var femalePopulation: List<PopulationRow>

    private fun preparePopulation() {
        if (this::malePopulation.isInitialized && this::femalePopulation.isInitialized) return

        val maleDataTable = parseCSVContentToPopulationDataTable(maleWeightToAgeCSVContent)
        val femaleDataTable = parseCSVContentToPopulationDataTable(femaleWeightToAgeCSVContent)

        val weightToAgeDataTable = AnthropometryDataTable(
            ReferenceValueType.AgeInMonths,
            PopulationValueType.WeightInKilograms,
            maleDataTable.toPopulationRow(
                ReferenceValueType.AgeInMonths,
                PopulationValueType.WeightInKilograms,
            ),
            femaleDataTable.toPopulationRow(
                ReferenceValueType.AgeInMonths,
                PopulationValueType.WeightInKilograms,
            ),
        )

        malePopulation = weightToAgeDataTable.malePopulationTable
        femalePopulation = weightToAgeDataTable.femalePopulationTable
    }

    @Before
    fun setup() {
        preparePopulation()
        interactor = AnthropometryInteractor(repository)
    }

    @Test
    fun `measureZScoreForWeightToAge - for male`() = runTest(UnconfinedTestDispatcher()) {
        Mockito.`when`(repository.getWeightToAgePopulation(Gender.Male)).thenReturn(malePopulation)

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
            .thenReturn(femalePopulation)

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
}