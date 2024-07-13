package id.rllyhz.giziplan.domain.usecase.anthropometry.classify

import id.rllyhz.giziplan.domain.model.classification.NormalWeight
import id.rllyhz.giziplan.domain.model.classification.RiskOfOverweight
import id.rllyhz.giziplan.domain.model.classification.SeverelyUnderweight
import id.rllyhz.giziplan.domain.model.classification.Underweight
import id.rllyhz.giziplan.domain.model.zscore.ZScoreCategory
import id.rllyhz.giziplan.domain.model.zscore.ZScoreData
import id.rllyhz.giziplan.domain.repository.AnthropometryRepository
import id.rllyhz.giziplan.domain.usecase.anthropometry.AnthropometryInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class AnthropometryInteractorForClassifyWeightToAgeTest {

    @Mock
    private lateinit var repository: AnthropometryRepository

    private lateinit var interactor: AnthropometryInteractor

    @Before
    fun setup() {
        interactor = AnthropometryInteractor(repository)
    }

    /* ========================================
     * SeverelyUnderweight Classification test cases
     * ----------------------------------------
     */
    @Test
    fun `verify zScore classification for SeverelyUnderweight test cases`() =
        runTest(UnconfinedTestDispatcher()) {
            // ==================================
            // SeverelyUnderweight test cases
            var zScoreData = ZScoreData(-3.1, ZScoreCategory.WeightToAge)
            val resultSeverelyUnderweight = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                SeverelyUnderweight.getClassificationName(),
                resultSeverelyUnderweight.classificationResult.getClassificationName()
            )
            // SeverelyUnderweight 2
            zScoreData = ZScoreData(-5.0, ZScoreCategory.WeightToAge)
            val resultSeverelyUnderweight2 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                SeverelyUnderweight.getClassificationName(),
                resultSeverelyUnderweight2.classificationResult.getClassificationName()
            )
            // Not SeverelyUnderweight
            zScoreData = ZScoreData(-3.0, ZScoreCategory.WeightToAge)
            val resultNotSeverelyUnderweight = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                SeverelyUnderweight.getClassificationName(),
                resultNotSeverelyUnderweight.classificationResult.getClassificationName()
            )
            // Not SeverelyUnderweight 2
            zScoreData = ZScoreData(-2.5, ZScoreCategory.WeightToAge)
            val resultNotSeverelyUnderweight2 = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                SeverelyUnderweight.getClassificationName(),
                resultNotSeverelyUnderweight2.classificationResult.getClassificationName()
            )
        }

    /* ========================================
     * Underweight Classification test cases
     * ----------------------------------------
     */
    @Test
    fun `verify zScore classification for Underweight test cases`() =
        runTest(UnconfinedTestDispatcher()) {
            // ==================================
            // Underweight test cases
            var zScoreData = ZScoreData(-3.0, ZScoreCategory.WeightToAge)
            val resultUnderweight = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Underweight.getClassificationName(),
                resultUnderweight.classificationResult.getClassificationName()
            )
            // Underweight 2
            zScoreData = ZScoreData(-2.1, ZScoreCategory.WeightToAge)
            val resultUnderweight2 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Underweight.getClassificationName(),
                resultUnderweight2.classificationResult.getClassificationName()
            )
            // Underweight 3
            zScoreData = ZScoreData(-2.5, ZScoreCategory.WeightToAge)
            val resultUnderweight3 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Underweight.getClassificationName(),
                resultUnderweight3.classificationResult.getClassificationName()
            )
            // Not Underweight
            zScoreData = ZScoreData(-2.0, ZScoreCategory.WeightToAge)
            val resultNotUnderweight = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                Underweight.getClassificationName(),
                resultNotUnderweight.classificationResult.getClassificationName()
            )
            // Not Underweight 2
            zScoreData = ZScoreData(-3.1, ZScoreCategory.WeightToAge)
            val resultNotUnderweight2 = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                Underweight.getClassificationName(),
                resultNotUnderweight2.classificationResult.getClassificationName()
            )
        }

    /* ========================================
     * NormalWeight Classification test cases
     * ----------------------------------------
     */
    @Test
    fun `verify zScore classification for NormalWeight test cases`() =
        runTest(UnconfinedTestDispatcher()) {
            // ==================================
            // NormalWeight test cases
            var zScoreData = ZScoreData(-2.0, ZScoreCategory.WeightToAge)
            val resultNormalWeight = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                NormalWeight.getClassificationName(),
                resultNormalWeight.classificationResult.getClassificationName()
            )
            // NormalWeight 2
            zScoreData = ZScoreData(1.0, ZScoreCategory.WeightToAge)
            val resultNormalWeight2 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                NormalWeight.getClassificationName(),
                resultNormalWeight2.classificationResult.getClassificationName()
            )
            // NormalWeight 3
            zScoreData = ZScoreData(-1.6, ZScoreCategory.WeightToAge)
            val resultNormalWeight3 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                NormalWeight.getClassificationName(),
                resultNormalWeight3.classificationResult.getClassificationName()
            )
            // Not NormalWeight
            zScoreData = ZScoreData(-2.1, ZScoreCategory.WeightToAge)
            val resultNotNormalWeight = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                NormalWeight.getClassificationName(),
                resultNotNormalWeight.classificationResult.getClassificationName()
            )
            // Not NormalWeight 2
            zScoreData = ZScoreData(1.1, ZScoreCategory.WeightToAge)
            val resultNotNormalWeight2 = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                NormalWeight.getClassificationName(),
                resultNotNormalWeight2.classificationResult.getClassificationName()
            )
        }

    /* ========================================
     * RiskOfOverweight Classification test cases
     * ----------------------------------------
     */
    @Test
    fun `verify zScore classification for RiskOfOverweight test cases`() =
        runTest(UnconfinedTestDispatcher()) {
            // ==================================
            // RiskOfOverweight test cases
            var zScoreData = ZScoreData(1.1, ZScoreCategory.WeightToAge)
            val resultRiskOfOverweight = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                RiskOfOverweight.getClassificationName(),
                resultRiskOfOverweight.classificationResult.getClassificationName()
            )
            // RiskOfOverweight 2
            zScoreData = ZScoreData(2.0, ZScoreCategory.WeightToAge)
            val resultRiskOfOverweight2 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                RiskOfOverweight.getClassificationName(),
                resultRiskOfOverweight2.classificationResult.getClassificationName()
            )
            // RiskOfOverweight 3
            zScoreData = ZScoreData(4.0, ZScoreCategory.WeightToAge)
            val resultRiskOfOverweight3 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                RiskOfOverweight.getClassificationName(),
                resultRiskOfOverweight3.classificationResult.getClassificationName()
            )
            // Not RiskOfOverweight
            zScoreData = ZScoreData(1.0, ZScoreCategory.WeightToAge)
            val resultNotRiskOfOverweight = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                RiskOfOverweight.getClassificationName(),
                resultNotRiskOfOverweight.classificationResult.getClassificationName()
            )
            // Not RiskOfOverweight 2
            zScoreData = ZScoreData(0.5, ZScoreCategory.WeightToAge)
            val resultNotRiskOfOverweight2 = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                RiskOfOverweight.getClassificationName(),
                resultNotRiskOfOverweight2.classificationResult.getClassificationName()
            )
        }
}