package id.rllyhz.giziplan.domain.usecase.anthropometry

import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.domain.model.classification.GoodNutritionalStatus
import id.rllyhz.giziplan.domain.model.classification.NormalHeight
import id.rllyhz.giziplan.domain.model.classification.NormalWeight
import id.rllyhz.giziplan.domain.model.classification.Obese
import id.rllyhz.giziplan.domain.model.classification.Overweight
import id.rllyhz.giziplan.domain.model.classification.PossibleRiskOfOverweight
import id.rllyhz.giziplan.domain.model.classification.RiskOfOverweight
import id.rllyhz.giziplan.domain.model.classification.SeverelyStunted
import id.rllyhz.giziplan.domain.model.classification.SeverelyUnderweight
import id.rllyhz.giziplan.domain.model.classification.SeverelyWasted
import id.rllyhz.giziplan.domain.model.classification.Stunted
import id.rllyhz.giziplan.domain.model.classification.Tall
import id.rllyhz.giziplan.domain.model.classification.Underweight
import id.rllyhz.giziplan.domain.model.classification.Wasted
import id.rllyhz.giziplan.domain.model.zscore.ZScoreCategory
import id.rllyhz.giziplan.domain.model.zscore.ZScoreClassificationData
import id.rllyhz.giziplan.domain.model.zscore.ZScoreData
import id.rllyhz.giziplan.domain.repository.AnthropometryRepository
import java.math.RoundingMode
import java.text.DecimalFormat

class AnthropometryInteractor(
    private val repository: AnthropometryRepository,
) : AnthropometryUseCase {

    override suspend fun measureZScoreForWeightToAge(
        measuredWeight: Double, age: Int, gender: Gender
    ): ZScoreData {
        val dataTable = repository.getWeightToAgePopulation(gender)

        var isOutOfRangePopulation = true
        var foundIndexPopulation = 0

        for (i in dataTable.indices) {
            // find the same age
            if (dataTable[i].referenceValue.toInt() == age) {
                isOutOfRangePopulation = false
                foundIndexPopulation = i
                break
            }
        }

        if (isOutOfRangePopulation) {
            return ZScoreData(
                0.0, ZScoreCategory.WeightToAge, true
            )
        }

        val foundPopulationRow = dataTable[foundIndexPopulation]

        val min1SD = foundPopulationRow.min1SD
        val median = foundPopulationRow.median
        val plus1SD = foundPopulationRow.plus1SD

        val sdp = when {
            measuredWeight > median.toDouble() -> {
                plus1SD.toBigDecimal() - median.toBigDecimal()
            }

            measuredWeight < median.toDouble() -> {
                median.toBigDecimal() - min1SD.toBigDecimal()
            }

            else -> "0.0".toBigDecimal()
        }

        val measuredMinusMedian = measuredWeight.toString().toBigDecimal() - median.toBigDecimal()
        val zScore = measuredMinusMedian.divide(sdp, 2, RoundingMode.HALF_DOWN)

        return ZScoreData(
            zScore.toDouble(), ZScoreCategory.WeightToAge, false
        )
    }

    override suspend fun measureZScoreForHeightToAge(
        measuredHeight: Double, age: Int, gender: Gender
    ): ZScoreData {
        val dataTable = repository.getWeightToHeightPopulation(gender)

        var isOutOfRangePopulation = true
        var foundIndexPopulation = 0

        for (i in dataTable.indices) {
            // find the same age
            if (dataTable[i].referenceValue.toInt() == age) {
                isOutOfRangePopulation = false
                foundIndexPopulation = i
                break
            }
        }

        if (isOutOfRangePopulation) {
            return ZScoreData(
                0.0, ZScoreCategory.HeightToAge, true
            )
        }

        val foundPopulationRow = dataTable[foundIndexPopulation]

        val min1SD = foundPopulationRow.min1SD
        val median = foundPopulationRow.median
        val plus1SD = foundPopulationRow.plus1SD

        val sdp = when {
            measuredHeight > median.toDouble() -> {
                plus1SD.toBigDecimal() - median.toBigDecimal()
            }

            measuredHeight < median.toDouble() -> {
                median.toBigDecimal() - min1SD.toBigDecimal()
            }

            else -> "0.0".toBigDecimal()
        }

        val measuredMinusMedian = measuredHeight.toString().toBigDecimal() - median.toBigDecimal()
        val zScore = measuredMinusMedian.divide(sdp, 2, RoundingMode.HALF_DOWN)

        return ZScoreData(
            zScore.toDouble(), ZScoreCategory.HeightToAge, false
        )
    }

    override suspend fun measureZScoreForWeightToHeight(
        measuredWeight: Double, height: Double, gender: Gender
    ): ZScoreData {
        val dataTable = repository.getWeightToHeightPopulation(gender)

        var isOutOfRangePopulation = true
        var foundIndexPopulation = 0

        for (i in dataTable.indices) {
            // find the same age
            if (dataTable[i].referenceValue.toDouble() == height) {
                isOutOfRangePopulation = false
                foundIndexPopulation = i
                break
            }
        }

        if (isOutOfRangePopulation) {
            return ZScoreData(
                0.0, ZScoreCategory.WeightToHeight, true
            )
        }

        val foundPopulationRow = dataTable[foundIndexPopulation]

        val min1SD = foundPopulationRow.min1SD
        val median = foundPopulationRow.median
        val plus1SD = foundPopulationRow.plus1SD

        val sdp = when {
            measuredWeight > median.toDouble() -> {
                plus1SD.toBigDecimal() - median.toBigDecimal()
            }

            measuredWeight < median.toDouble() -> {
                median.toBigDecimal() - min1SD.toBigDecimal()
            }

            else -> "0.0".toBigDecimal()
        }

        val measuredMinusMedian = measuredWeight.toString().toBigDecimal() - median.toBigDecimal()
        val zScore = measuredMinusMedian.divide(sdp, 2, RoundingMode.HALF_DOWN)

        return ZScoreData(
            zScore.toDouble(), ZScoreCategory.WeightToHeight, false
        )
    }

    override suspend fun classifyZScore(zScoreData: ZScoreData): ZScoreClassificationData =
        when (zScoreData.zScoreCategory) {
            ZScoreCategory.WeightToAge -> {
                if (zScoreData.zScore < -3.0) ZScoreClassificationData(
                    zScoreData, SeverelyUnderweight
                )
                if (zScoreData.zScore >= -3.0 && zScoreData.zScore < -2.0) ZScoreClassificationData(
                    zScoreData, Underweight
                )
                if (zScoreData.zScore >= -2.0 && zScoreData.zScore <= 1.0) ZScoreClassificationData(
                    zScoreData, NormalWeight
                )
                if (zScoreData.zScore > 1.0) ZScoreClassificationData(
                    zScoreData, RiskOfOverweight
                )
                else ZScoreClassificationData(
                    zScoreData, NormalWeight
                )
            }

            ZScoreCategory.HeightToAge -> {
                if (zScoreData.zScore < -3.0) ZScoreClassificationData(
                    zScoreData, SeverelyStunted
                )
                if (zScoreData.zScore >= -3.0 && zScoreData.zScore < -2.0) ZScoreClassificationData(
                    zScoreData, Stunted
                )
                if (zScoreData.zScore >= -2.0 && zScoreData.zScore <= 3.0) ZScoreClassificationData(
                    zScoreData, NormalHeight
                )
                if (zScoreData.zScore > 3.0) ZScoreClassificationData(
                    zScoreData, Tall
                )
                else ZScoreClassificationData(
                    zScoreData, NormalHeight
                )
            }

            ZScoreCategory.WeightToHeight -> {
                if (zScoreData.zScore < -3.0) ZScoreClassificationData(
                    zScoreData, SeverelyWasted
                )
                if (zScoreData.zScore >= -3.0 && zScoreData.zScore < -2.0) ZScoreClassificationData(
                    zScoreData, Wasted
                )
                if (zScoreData.zScore >= -2.0 && zScoreData.zScore <= 1.0) ZScoreClassificationData(
                    zScoreData, GoodNutritionalStatus
                )
                if (zScoreData.zScore > 1.0 && zScoreData.zScore <= 2.0) ZScoreClassificationData(
                    zScoreData, PossibleRiskOfOverweight
                )
                if (zScoreData.zScore > 2.0 && zScoreData.zScore <= 3.0) ZScoreClassificationData(
                    zScoreData, Overweight
                )
                if (zScoreData.zScore > 3.0) ZScoreClassificationData(
                    zScoreData, Obese
                )
                else ZScoreClassificationData(
                    zScoreData, NormalHeight
                )
            }
        }
}