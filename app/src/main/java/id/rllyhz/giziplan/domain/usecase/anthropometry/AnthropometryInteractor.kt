package id.rllyhz.giziplan.domain.usecase.anthropometry

import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.domain.model.classification.GoodNutritionalStatus
import id.rllyhz.giziplan.domain.model.classification.InvalidZScoreHeightToAge
import id.rllyhz.giziplan.domain.model.classification.InvalidZScoreOfUnknown
import id.rllyhz.giziplan.domain.model.classification.InvalidZScoreWeightToAge
import id.rllyhz.giziplan.domain.model.classification.InvalidZScoreWeightToHeight
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
import id.rllyhz.giziplan.utils.roundToNearestHalf
import java.math.RoundingMode

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

            else -> "1.0".toBigDecimal()
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
        val dataTable = repository.getHeightToAgePopulation(gender)

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

            else -> "1.0".toBigDecimal()
        }

        val measuredMinusMedian = measuredHeight.toString().toBigDecimal() - median.toBigDecimal()
        val zScore = measuredMinusMedian.divide(sdp, 2, RoundingMode.HALF_DOWN)

        return ZScoreData(
            zScore.toDouble(), ZScoreCategory.HeightToAge, false
        )
    }

    override suspend fun measureZScoreForWeightToHeight(
        measuredWeight: Double, height: Double, isAgeLessThan24: Boolean, gender: Gender
    ): ZScoreData {
        val dataTable = if (isAgeLessThan24) {
            repository.getWeightToHeightLessThan24Population(gender)
        } else repository.getWeightToHeightGreaterThan24Population(gender)

        var isOutOfRangePopulation = true
        var foundIndexPopulation = 0

        for (i in dataTable.indices) {
            // find the same age
            if (dataTable[i].referenceValue.toDouble() == height.roundToNearestHalf()) {
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

            else -> "1.0".toBigDecimal()
        }

        val measuredMinusMedian = measuredWeight.toString().toBigDecimal() - median.toBigDecimal()
        val zScore = measuredMinusMedian.divide(sdp, 2, RoundingMode.HALF_DOWN)

        return ZScoreData(
            zScore.toDouble(), ZScoreCategory.WeightToHeight, false
        )
    }

    // TODO Fix classifyZScore
    override suspend fun classifyZScore(zScoreData: ZScoreData): ZScoreClassificationData {
        val actualZScoreCat = zScoreData.zScoreCategory
        val actualZScoreValue = zScoreData.zScore

        val weightToAgeCat = ZScoreCategory.WeightToAge
        val heightToAgeCat = ZScoreCategory.HeightToAge
        val weightToHeightCat = ZScoreCategory.WeightToHeight

        // Weight to Age
        if (actualZScoreCat == weightToAgeCat) {
            if (actualZScoreValue < -3.0) {
                // Berat badan sangat kurang
                return ZScoreClassificationData(zScoreData, SeverelyUnderweight)
            } else if (actualZScoreValue >= -3.0 && actualZScoreValue < -2.0) {
                // Berat badan kurang
                return ZScoreClassificationData(zScoreData, Underweight)
            } else if (actualZScoreValue >= -2.0 && actualZScoreValue <= 1.0) {
                // Berat badan normal
                return ZScoreClassificationData(zScoreData, NormalWeight)
            } else if (actualZScoreValue > 1.0) {
                // Risiko berat badan lebih
                return ZScoreClassificationData(zScoreData, RiskOfOverweight)
            } else {
                // Nilai z-score tidak valid
                return ZScoreClassificationData(zScoreData, InvalidZScoreWeightToAge, true)
            }
        }

        // Height to Age
        if (actualZScoreCat == heightToAgeCat) {
            if (actualZScoreValue < -3.0) {
                // Sangat pendek
                return ZScoreClassificationData(zScoreData, SeverelyStunted)
            } else if (actualZScoreValue >= -3.0 && actualZScoreValue < -2.0) {
                // Pendek
                return ZScoreClassificationData(zScoreData, Stunted)
            } else if (actualZScoreValue >= -2.0 && actualZScoreValue <= 3.0) {
                // Normal
                return ZScoreClassificationData(zScoreData, NormalHeight)
            } else if (actualZScoreValue > 3.0) {
                // Tinggi
                return ZScoreClassificationData(zScoreData, Tall)
            } else {
                // Nilai z-score tidak valid
                return ZScoreClassificationData(zScoreData, InvalidZScoreHeightToAge, true)
            }
        }

        // Weight to Height
        if (actualZScoreCat == weightToHeightCat) {
            if (actualZScoreValue < -3.0) {
                // Gizi buruk
                return ZScoreClassificationData(zScoreData, SeverelyWasted)
            } else if (actualZScoreValue >= -3.0 && actualZScoreValue < -2.0) {
                // Gizi kurang
                return ZScoreClassificationData(zScoreData, Wasted)
            } else if (actualZScoreValue >= -2.0 && actualZScoreValue <= 1.0) {
                // Gizi baik
                return ZScoreClassificationData(zScoreData, GoodNutritionalStatus)
            } else if (actualZScoreValue > 1.0 && actualZScoreValue <= 2.0) {
                // Berisiko gizi lebih
                return ZScoreClassificationData(zScoreData, PossibleRiskOfOverweight)
            } else if (actualZScoreValue > 2.0 && actualZScoreValue <= 3.0) {
                println("Gizi lebih")
                // Gizi lebih
                return ZScoreClassificationData(zScoreData, Overweight)
            } else if (actualZScoreValue > 3.0) {
                // Obesitas
                return ZScoreClassificationData(zScoreData, Obese)
            } else {
                // Nilai z-score tidak valid
                return ZScoreClassificationData(zScoreData, InvalidZScoreWeightToHeight, true)
            }
        }

        // Nilai z-score tidak valid
        return ZScoreClassificationData(zScoreData, InvalidZScoreOfUnknown, true)
    }
}