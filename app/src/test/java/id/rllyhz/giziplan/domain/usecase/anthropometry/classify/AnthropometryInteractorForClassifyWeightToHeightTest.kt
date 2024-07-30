package id.rllyhz.giziplan.domain.usecase.anthropometry.classify

import id.rllyhz.giziplan.domain.model.classification.GoodNutritionalStatus
import id.rllyhz.giziplan.domain.model.classification.Obese
import id.rllyhz.giziplan.domain.model.classification.Overweight
import id.rllyhz.giziplan.domain.model.classification.PossibleRiskOfOverweight
import id.rllyhz.giziplan.domain.model.classification.SeverelyWasted
import id.rllyhz.giziplan.domain.model.classification.Wasted
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
class AnthropometryInteractorForClassifyWeightToHeightTest {

    @Mock
    private lateinit var repository: AnthropometryRepository

    private lateinit var interactor: AnthropometryInteractor

    @Before
    fun setup() {
        interactor = AnthropometryInteractor(repository)
    }

    /* ========================================
     * SeverelyWasted Classification test cases
     * ----------------------------------------
     */
    @Test
    fun `verify zScore classification for SeverelyWasted test cases`() =
        runTest(UnconfinedTestDispatcher()) {
            // ==================================
            // SeverelyWasted test cases
            var zScoreData = ZScoreData(-3.1, ZScoreCategory.WeightToHeight)
            val resultSeverelyWasted = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                SeverelyWasted.getClassificationName(),
                resultSeverelyWasted.classificationResult.getClassificationName()
            )
            // SeverelyWasted 2
            zScoreData = ZScoreData(-5.0, ZScoreCategory.WeightToHeight)
            val resultSeverelyWasted2 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                SeverelyWasted.getClassificationName(),
                resultSeverelyWasted2.classificationResult.getClassificationName()
            )
            // Not SeverelyWasted
            zScoreData = ZScoreData(-3.0, ZScoreCategory.WeightToHeight)
            val resultNotSeverelyWasted = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                SeverelyWasted.getClassificationName(),
                resultNotSeverelyWasted.classificationResult.getClassificationName()
            )
            // Not SeverelyWasted 2
            zScoreData = ZScoreData(-2.5, ZScoreCategory.WeightToHeight)
            val resultNotSeverelyWasted2 = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                SeverelyWasted.getClassificationName(),
                resultNotSeverelyWasted2.classificationResult.getClassificationName()
            )
        }

    /* ========================================
     * Wasted Classification test cases
     * ----------------------------------------
     */
    @Test
    fun `verify zScore classification for Wasted test cases`() =
        runTest(UnconfinedTestDispatcher()) {
            // ==================================
            // Wasted test cases
            var zScoreData = ZScoreData(-3.0, ZScoreCategory.WeightToHeight)
            val resultWasted = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Wasted.getClassificationName(),
                resultWasted.classificationResult.getClassificationName()
            )
            // Wasted 2
            zScoreData = ZScoreData(-2.1, ZScoreCategory.WeightToHeight)
            val resultWasted2 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Wasted.getClassificationName(),
                resultWasted2.classificationResult.getClassificationName()
            )
            // Wasted 3
            zScoreData = ZScoreData(-2.5, ZScoreCategory.WeightToHeight)
            val resultWasted3 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Wasted.getClassificationName(),
                resultWasted3.classificationResult.getClassificationName()
            )
            // Not Wasted
            zScoreData = ZScoreData(-2.0, ZScoreCategory.WeightToHeight)
            val resultNotWasted = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                Wasted.getClassificationName(),
                resultNotWasted.classificationResult.getClassificationName()
            )
            // Not Wasted 2
            zScoreData = ZScoreData(-3.1, ZScoreCategory.WeightToHeight)
            val resultNotWasted2 = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                Wasted.getClassificationName(),
                resultNotWasted2.classificationResult.getClassificationName()
            )
        }

    /* ========================================
     * GoodNutritionalStatus Classification test cases
     * ----------------------------------------
     */
    @Test
    fun `verify zScore classification for GoodNutritionalStatus test cases`() =
        runTest(UnconfinedTestDispatcher()) {
            // ==================================
            // GoodNutritionalStatus test cases
            var zScoreData = ZScoreData(-2.0, ZScoreCategory.WeightToHeight)
            val resultGoodNutritionalStatus = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                GoodNutritionalStatus.getClassificationName(),
                resultGoodNutritionalStatus.classificationResult.getClassificationName()
            )
            // GoodNutritionalStatus 2
            zScoreData = ZScoreData(1.0, ZScoreCategory.WeightToHeight)
            val resultGoodNutritionalStatus2 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                GoodNutritionalStatus.getClassificationName(),
                resultGoodNutritionalStatus2.classificationResult.getClassificationName()
            )
            // GoodNutritionalStatus 3
            zScoreData = ZScoreData(-1.4, ZScoreCategory.WeightToHeight)
            val resultGoodNutritionalStatus3 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                GoodNutritionalStatus.getClassificationName(),
                resultGoodNutritionalStatus3.classificationResult.getClassificationName()
            )
            // Not GoodNutritionalStatus
            zScoreData = ZScoreData(-2.1, ZScoreCategory.WeightToHeight)
            val resultNotGoodNutritionalStatus = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                GoodNutritionalStatus.getClassificationName(),
                resultNotGoodNutritionalStatus.classificationResult.getClassificationName()
            )
            // Not GoodNutritionalStatus 2
            zScoreData = ZScoreData(1.1, ZScoreCategory.WeightToHeight)
            val resultNotGoodNutritionalStatus2 = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                GoodNutritionalStatus.getClassificationName(),
                resultNotGoodNutritionalStatus2.classificationResult.getClassificationName()
            )
        }

    /* ========================================
     * PossibleRiskOfOverweight Classification test cases
     * ----------------------------------------
     */
    @Test
    fun `verify zScore classification for PossibleRiskOfOverweight test cases`() =
        runTest(UnconfinedTestDispatcher()) {
            // ==================================
            // PossibleRiskOfOverweight test cases
            var zScoreData = ZScoreData(1.1, ZScoreCategory.WeightToHeight)
            val resultPossibleRiskOfOverweight = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                PossibleRiskOfOverweight.getClassificationName(),
                resultPossibleRiskOfOverweight.classificationResult.getClassificationName()
            )
            // PossibleRiskOfOverweight 2
            zScoreData = ZScoreData(2.0, ZScoreCategory.WeightToHeight)
            val resultPossibleRiskOfOverweight2 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                PossibleRiskOfOverweight.getClassificationName(),
                resultPossibleRiskOfOverweight2.classificationResult.getClassificationName()
            )
            // PossibleRiskOfOverweight 3
            zScoreData = ZScoreData(1.7, ZScoreCategory.WeightToHeight)
            val resultPossibleRiskOfOverweight3 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                PossibleRiskOfOverweight.getClassificationName(),
                resultPossibleRiskOfOverweight3.classificationResult.getClassificationName()
            )
            // Not PossibleRiskOfOverweight
            zScoreData = ZScoreData(1.0, ZScoreCategory.WeightToHeight)
            val resultNotPossibleRiskOfOverweight = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                PossibleRiskOfOverweight.getClassificationName(),
                resultNotPossibleRiskOfOverweight.classificationResult.getClassificationName()
            )
            // Not PossibleRiskOfOverweight 2
            zScoreData = ZScoreData(2.1, ZScoreCategory.WeightToHeight)
            val resultNotPossibleRiskOfOverweight2 = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                PossibleRiskOfOverweight.getClassificationName(),
                resultNotPossibleRiskOfOverweight2.classificationResult.getClassificationName()
            )
        }

    /* ========================================
     * Overweight Classification test cases
     * ----------------------------------------
     */
    @Test
    fun `verify zScore classification for Overweight test cases`() =
        runTest(UnconfinedTestDispatcher()) {
            // ==================================
            // Overweight test cases
            var zScoreData = ZScoreData(2.1, ZScoreCategory.WeightToHeight)
            val resultOverweight = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Overweight.getClassificationName(),
                resultOverweight.classificationResult.getClassificationName()
            )
            // Overweight 2
            zScoreData = ZScoreData(3.0, ZScoreCategory.WeightToHeight)
            val resultOverweight2 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Overweight.getClassificationName(),
                resultOverweight2.classificationResult.getClassificationName()
            )
            // Overweight 3
            zScoreData = ZScoreData(2.6, ZScoreCategory.WeightToHeight)
            val resultOverweight3 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Overweight.getClassificationName(),
                resultOverweight3.classificationResult.getClassificationName()
            )
            // Not Overweight
            zScoreData = ZScoreData(2.0, ZScoreCategory.WeightToHeight)
            val resultNotOverweight = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                Overweight.getClassificationName(),
                resultNotOverweight.classificationResult.getClassificationName()
            )
            // Not Overweight 2
            zScoreData = ZScoreData(3.1, ZScoreCategory.WeightToHeight)
            val resultNotOverweight2 = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                Overweight.getClassificationName(),
                resultNotOverweight2.classificationResult.getClassificationName()
            )
        }

    /* ========================================
     * Obese Classification test cases
     * ----------------------------------------
     */
    @Test
    fun `verify zScore classification for Obese test cases`() =
        runTest(UnconfinedTestDispatcher()) {
            // ==================================
            // Obese test cases
            var zScoreData = ZScoreData(3.1, ZScoreCategory.WeightToHeight)
            val resultObese = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Obese.getClassificationName(),
                resultObese.classificationResult.getClassificationName()
            )
            // Obese 2
            zScoreData = ZScoreData(3.8, ZScoreCategory.WeightToHeight)
            val resultObese2 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Obese.getClassificationName(),
                resultObese2.classificationResult.getClassificationName()
            )
            // Obese 3
            zScoreData = ZScoreData(5.0, ZScoreCategory.WeightToHeight)
            val resultObese3 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Obese.getClassificationName(),
                resultObese3.classificationResult.getClassificationName()
            )
            // Not Obese
            zScoreData = ZScoreData(3.0, ZScoreCategory.WeightToHeight)
            val resultNotObese = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                Obese.getClassificationName(),
                resultNotObese.classificationResult.getClassificationName()
            )
            // Not Obese 2
            zScoreData = ZScoreData(2.5, ZScoreCategory.WeightToHeight)
            val resultNotObese2 = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                Obese.getClassificationName(),
                resultNotObese2.classificationResult.getClassificationName()
            )
        }

    /* ========================================
     * PossibleRiskOfOverweight Classification test cases
     * ----------------------------------------
     */
    @Test
    fun `verify zScore classification for Gizi Buruk test cases`() =
        runTest(UnconfinedTestDispatcher()) {
            val zScoreData = ZScoreData(-3.0, ZScoreCategory.WeightToHeight)
            val result = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                result.classificationResult.getClassificationName(),
                SeverelyWasted.getClassificationName()
            )
        }
}