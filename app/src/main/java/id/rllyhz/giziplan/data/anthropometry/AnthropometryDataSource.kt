package id.rllyhz.giziplan.data.anthropometry

import android.content.Context
import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryDataTable
import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryTables
import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.data.anthropometry.type.MeasuredValueType
import id.rllyhz.giziplan.data.anthropometry.type.PopulationValueType
import id.rllyhz.giziplan.data.anthropometry.utils.toPopulationRow
import id.rllyhz.giziplan.domain.model.zscore.ZScoreCategory
import id.rllyhz.giziplan.utils.getWeightToAgePopulationTableData

open class AnthropometryDataSource(
    private val context: Context
) {
    open fun createAnthropometryTables(): AnthropometryTables = AnthropometryTables(
        weightToAgeDataTable(context),
        heightToAgeDataTable(context),
        weightToHeightDataTable(context),
    )

    open fun classifyZScore(category: ZScoreCategory, value: Double) {}

    private fun weightToAgeDataTable(context: Context) = AnthropometryDataTable(
        MeasuredValueType.AgeInMonths,
        PopulationValueType.WeightInKilograms,
        getWeightToAgePopulationTableData(context, Gender.Male).toPopulationRow(
            MeasuredValueType.AgeInMonths, PopulationValueType.WeightInKilograms
        ),
        getWeightToAgePopulationTableData(context, Gender.Female).toPopulationRow(
            MeasuredValueType.AgeInMonths, PopulationValueType.WeightInKilograms
        ),
    )

    private fun heightToAgeDataTable(context: Context) = AnthropometryDataTable(
        MeasuredValueType.AgeInMonths,
        PopulationValueType.HeightInCentimeters,
        getWeightToAgePopulationTableData(context, Gender.Male).toPopulationRow(
            MeasuredValueType.AgeInMonths, PopulationValueType.HeightInCentimeters
        ),
        getWeightToAgePopulationTableData(context, Gender.Female).toPopulationRow(
            MeasuredValueType.AgeInMonths, PopulationValueType.HeightInCentimeters
        ),
    )

    private fun weightToHeightDataTable(context: Context) = AnthropometryDataTable(
        MeasuredValueType.HeightInCentimeters,
        PopulationValueType.WeightInKilograms,
        getWeightToAgePopulationTableData(context, Gender.Male).toPopulationRow(
            MeasuredValueType.HeightInCentimeters,
            PopulationValueType.WeightInKilograms,
        ),
        getWeightToAgePopulationTableData(context, Gender.Female).toPopulationRow(
            MeasuredValueType.HeightInCentimeters,
            PopulationValueType.WeightInKilograms,
        ),
    )
}