package id.rllyhz.giziplan.domain.usecase.anthropometry

import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryTables
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

class AnthropometryInteractor(
    private val anthropometryTables: AnthropometryTables,
) : AnthropometryUseCase {
    override fun measureZScoreForWeightToAge(
        measuredWeight: Double, age: Int, gender: Gender
    ): ZScoreData {
        val dataTable = if (gender == Gender.Male) {
            anthropometryTables.weightToAgeDataTable.malePopulationTable
        } else anthropometryTables.weightToAgeDataTable.femalePopulationTable

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
        val measuredMinusMedian = measuredWeight - foundPopulationRow.median

        val sdp = when {
            measuredWeight < foundPopulationRow.median -> {
                foundPopulationRow.median - foundPopulationRow.min1SD
            }

            measuredWeight > foundPopulationRow.median -> {
                foundPopulationRow.plus1SD - foundPopulationRow.median
            }

            else -> 0.0
        }

        return ZScoreData(
            measuredMinusMedian / sdp, ZScoreCategory.WeightToAge, false
        )
    }

    override fun measureZScoreForHeightToAge(
        measuredHeight: Double, age: Int, gender: Gender
    ): ZScoreData {
        val dataTable = if (gender == Gender.Male) {
            anthropometryTables.heightToAgeDataTable.malePopulationTable
        } else anthropometryTables.heightToAgeDataTable.femalePopulationTable

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
        val measuredMinusMedian = measuredHeight - foundPopulationRow.median

        val sdp = when {
            measuredHeight < foundPopulationRow.median -> {
                foundPopulationRow.median - foundPopulationRow.min1SD
            }

            measuredHeight > foundPopulationRow.median -> {
                foundPopulationRow.plus1SD - foundPopulationRow.median
            }

            else -> 0.0
        }

        return ZScoreData(
            measuredMinusMedian / sdp, ZScoreCategory.HeightToAge, false
        )
    }

    override fun measureZScoreForWeightToHeight(
        measuredWeight: Double, height: Double, gender: Gender
    ): ZScoreData {
        val dataTable = if (gender == Gender.Male) {
            anthropometryTables.weightToHeightDataTable.malePopulationTable
        } else anthropometryTables.weightToHeightDataTable.femalePopulationTable

        var isOutOfRangePopulation = true
        var foundIndexPopulation = 0

        for (i in dataTable.indices) {
            // find the same age
            if (dataTable[i].referenceValue == height) {
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
        val measuredMinusMedian = measuredWeight - foundPopulationRow.median

        val sdp = when {
            measuredWeight < foundPopulationRow.median -> {
                foundPopulationRow.median - foundPopulationRow.min1SD
            }

            measuredWeight > foundPopulationRow.median -> {
                foundPopulationRow.plus1SD - foundPopulationRow.median
            }

            else -> 0.0
        }

        return ZScoreData(
            measuredMinusMedian / sdp, ZScoreCategory.WeightToHeight, false
        )
    }

    override fun classifyZScore(zScoreData: ZScoreData): ZScoreClassificationData =
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