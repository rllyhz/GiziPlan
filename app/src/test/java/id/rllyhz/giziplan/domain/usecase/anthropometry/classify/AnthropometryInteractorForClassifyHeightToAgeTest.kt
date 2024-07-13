package id.rllyhz.giziplan.domain.usecase.anthropometry.classify

import id.rllyhz.giziplan.domain.model.classification.NormalHeight
import id.rllyhz.giziplan.domain.model.classification.SeverelyStunted
import id.rllyhz.giziplan.domain.model.classification.Stunted
import id.rllyhz.giziplan.domain.model.classification.Tall
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
class AnthropometryInteractorForClassifyHeightToAgeTest {

    @Mock
    private lateinit var repository: AnthropometryRepository

    private lateinit var interactor: AnthropometryInteractor

    @Before
    fun setup() {
        interactor = AnthropometryInteractor(repository)
    }

    /* ========================================
     * SeverelyStunted Classification test cases
     * ----------------------------------------
     */
    @Test
    fun `verify zScore classification for SeverelyStunted test cases`() =
        runTest(UnconfinedTestDispatcher()) {
            // ==================================
            // SeverelyStunted test cases
            var zScoreData = ZScoreData(-3.1, ZScoreCategory.HeightToAge)
            val resultSeverelyStunted = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                SeverelyStunted.getClassificationName(),
                resultSeverelyStunted.classificationResult.getClassificationName()
            )
            // SeverelyStunted 2
            zScoreData = ZScoreData(-5.0, ZScoreCategory.HeightToAge)
            val resultSeverelyStunted2 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                SeverelyStunted.getClassificationName(),
                resultSeverelyStunted2.classificationResult.getClassificationName()
            )
            // Not SeverelyStunted
            zScoreData = ZScoreData(-3.0, ZScoreCategory.HeightToAge)
            val resultNotSeverelyStunted = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                SeverelyStunted.getClassificationName(),
                resultNotSeverelyStunted.classificationResult.getClassificationName()
            )
            // Not SeverelyStunted 2
            zScoreData = ZScoreData(-2.5, ZScoreCategory.HeightToAge)
            val resultNotSeverelyStunted2 = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                SeverelyStunted.getClassificationName(),
                resultNotSeverelyStunted2.classificationResult.getClassificationName()
            )
        }

    /* ========================================
     * Stunted Classification test cases
     * ----------------------------------------
     */
    @Test
    fun `verify zScore classification for Stunted test cases`() =
        runTest(UnconfinedTestDispatcher()) {
            // ==================================
            // Stunted test cases
            var zScoreData = ZScoreData(-3.0, ZScoreCategory.HeightToAge)
            val resultStunted = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Stunted.getClassificationName(),
                resultStunted.classificationResult.getClassificationName()
            )
            // Stunted 2
            zScoreData = ZScoreData(-2.1, ZScoreCategory.HeightToAge)
            val resultStunted2 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Stunted.getClassificationName(),
                resultStunted2.classificationResult.getClassificationName()
            )
            // Stunted 3
            zScoreData = ZScoreData(-2.5, ZScoreCategory.HeightToAge)
            val resultStunted3 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Stunted.getClassificationName(),
                resultStunted3.classificationResult.getClassificationName()
            )
            // Not Stunted
            zScoreData = ZScoreData(-2.0, ZScoreCategory.HeightToAge)
            val resultNotStunted = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                Stunted.getClassificationName(),
                resultNotStunted.classificationResult.getClassificationName()
            )
            // Not Stunted 2
            zScoreData = ZScoreData(-3.1, ZScoreCategory.HeightToAge)
            val resultNotStunted2 = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                Stunted.getClassificationName(),
                resultNotStunted2.classificationResult.getClassificationName()
            )
        }

    /* ========================================
     * NormalHeight Classification test cases
     * ----------------------------------------
     */
    @Test
    fun `verify zScore classification for NormalHeight test cases`() =
        runTest(UnconfinedTestDispatcher()) {
            // ==================================
            // NormalHeight test cases
            var zScoreData = ZScoreData(-2.0, ZScoreCategory.HeightToAge)
            val resultNormalHeight = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                NormalHeight.getClassificationName(),
                resultNormalHeight.classificationResult.getClassificationName()
            )
            // NormalHeight 2
            zScoreData = ZScoreData(3.0, ZScoreCategory.HeightToAge)
            val resultNormalHeight2 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                NormalHeight.getClassificationName(),
                resultNormalHeight2.classificationResult.getClassificationName()
            )
            // NormalHeight 3
            zScoreData = ZScoreData(-1.4, ZScoreCategory.HeightToAge)
            val resultNormalHeight3 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                NormalHeight.getClassificationName(),
                resultNormalHeight3.classificationResult.getClassificationName()
            )
            // Not NormalHeight
            zScoreData = ZScoreData(-2.1, ZScoreCategory.HeightToAge)
            val resultNotNormalHeight = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                NormalHeight.getClassificationName(),
                resultNotNormalHeight.classificationResult.getClassificationName()
            )
            // Not NormalHeight 2
            zScoreData = ZScoreData(3.1, ZScoreCategory.HeightToAge)
            val resultNotNormalHeight2 = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                NormalHeight.getClassificationName(),
                resultNotNormalHeight2.classificationResult.getClassificationName()
            )
        }

    /* ========================================
     * Tall Classification test cases
     * ----------------------------------------
     */
    @Test
    fun `verify zScore classification for Tall test cases`() =
        runTest(UnconfinedTestDispatcher()) {
            // ==================================
            // Tall test cases
            var zScoreData = ZScoreData(3.1, ZScoreCategory.HeightToAge)
            val resultTall = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Tall.getClassificationName(),
                resultTall.classificationResult.getClassificationName()
            )
            // Tall 2
            zScoreData = ZScoreData(3.8, ZScoreCategory.HeightToAge)
            val resultTall2 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Tall.getClassificationName(),
                resultTall2.classificationResult.getClassificationName()
            )
            // Tall 3
            zScoreData = ZScoreData(5.0, ZScoreCategory.HeightToAge)
            val resultTall3 = interactor.classifyZScore(zScoreData)
            Assert.assertEquals(
                Tall.getClassificationName(),
                resultTall3.classificationResult.getClassificationName()
            )
            // Not Tall
            zScoreData = ZScoreData(3.0, ZScoreCategory.HeightToAge)
            val resultNotTall = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                Tall.getClassificationName(),
                resultNotTall.classificationResult.getClassificationName()
            )
            // Not Tall 2
            zScoreData = ZScoreData(2.5, ZScoreCategory.HeightToAge)
            val resultNotTall2 = interactor.classifyZScore(zScoreData)
            Assert.assertNotEquals(
                Tall.getClassificationName(),
                resultNotTall2.classificationResult.getClassificationName()
            )
        }
}