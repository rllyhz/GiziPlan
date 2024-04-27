package id.rllyhz.giziplan.domain.usecase.anthropometry

import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryTables
import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.domain.model.NutritionalStatusCategory
import id.rllyhz.giziplan.domain.model.ZScoreCategory

class AnthropometryInteractor(
    private val anthropometryTables: AnthropometryTables,
    private val nutritionalStatusCategory: NutritionalStatusCategory,
) : AnthropometryUseCase {
    override fun measureZScoreForWeightToAge(
        measuredWeight: Double, age: Int, gender: Gender
    ): Double? {
        val dataTable = if (gender == Gender.Male) {
            anthropometryTables.weightToAgeDataTable.malePopulationTable
        } else anthropometryTables.weightToAgeDataTable.femalePopulationTable

        var outOfRange = true
        var foundIndexPopulation = 0

        for (i in dataTable.indices) {
            // find the same age
            if (dataTable[i].referenceValue.toInt() == age) {
                outOfRange = false
                foundIndexPopulation = i
                break
            }
        }

        if (outOfRange) {
            return null
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

        return measuredMinusMedian / sdp
    }

    override fun measureZScoreForHeightToAge(
        measuredHeight: Double, age: Int, gender: Gender
    ): Double? {
        val dataTable = if (gender == Gender.Male) {
            anthropometryTables.heightToAgeDataTable.malePopulationTable
        } else anthropometryTables.heightToAgeDataTable.femalePopulationTable

        var outOfRange = true
        var foundIndexPopulation = 0

        for (i in dataTable.indices) {
            // find the same age
            if (dataTable[i].referenceValue.toInt() == age) {
                outOfRange = false
                foundIndexPopulation = i
                break
            }
        }

        if (outOfRange) {
            return null
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

        return measuredMinusMedian / sdp
    }

    override fun measureZScoreForWeightToHeight(
        measuredWeight: Double, height: Double, gender: Gender
    ): Double? {
        val dataTable = if (gender == Gender.Male) {
            anthropometryTables.weightToHeightDataTable.malePopulationTable
        } else anthropometryTables.weightToHeightDataTable.femalePopulationTable

        var outOfRange = true
        var foundIndexPopulation = 0

        for (i in dataTable.indices) {
            // find the same age
            if (dataTable[i].referenceValue == height) {
                outOfRange = false
                foundIndexPopulation = i
                break
            }
        }

        if (outOfRange) {
            return null
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

        return measuredMinusMedian / sdp
    }

    override fun classifyZScore(category: ZScoreCategory, zScore: Double): String =
        when (category) {
            ZScoreCategory.WeightToAge -> {
                if (zScore < -3.0) "berat_badan_sangat_kurang"
                if (zScore >= -3.0 && zScore < -2.0) "berat_badan_kurang"
                if (zScore >= -2.0 && zScore <= 1.0) "berat_badan_normal"
                if (zScore > 1.0) "risiko_berat_badan_lebih"
                else "unknown"
            }

            ZScoreCategory.HeightToAge -> {
                if (zScore < -3.0) "sangat_pendek"
                if (zScore >= -3.0 && zScore < -2.0) "pendek"
                if (zScore >= -2.0 && zScore <= 3.0) "normal"
                if (zScore > 3.0) "tinggi"
                else "unknown"
            }

            ZScoreCategory.WeightToHeight -> {
                if (zScore < -3.0) "gizi_buruk"
                if (zScore >= -3.0 && zScore < -2.0) "gizi_kurang"
                if (zScore >= -2.0 && zScore <= 1.0) "normal"
                if (zScore > 1.0 && zScore <= 2.0) "berisiko_gizi_lebih"
                if (zScore > 2.0 && zScore <= 3.0) "gizi_lebih"
                if (zScore > 3.0) "obesitas"
                else "unknown"
            }
        }
}