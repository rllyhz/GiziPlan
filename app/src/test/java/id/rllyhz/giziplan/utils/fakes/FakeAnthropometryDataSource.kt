package id.rllyhz.giziplan.utils.fakes

import android.content.Context
import id.rllyhz.giziplan.createDummyDataTable
import id.rllyhz.giziplan.data.anthropometry.AnthropometryDataSource
import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryTables
import id.rllyhz.giziplan.data.anthropometry.type.MeasuredValueType
import id.rllyhz.giziplan.data.anthropometry.type.PopulationValueType

class FakeAnthropometryDataSource(
    context: Context
) : AnthropometryDataSource(context) {
    override fun createAnthropometryTables(): AnthropometryTables =
        AnthropometryTables(
            createDummyDataTable(
                MeasuredValueType.AgeInMonths,
                PopulationValueType.WeightInKilograms
            ),
            createDummyDataTable(
                MeasuredValueType.AgeInMonths,
                PopulationValueType.HeightInCentimeters
            ),
            createDummyDataTable(
                MeasuredValueType.HeightInCentimeters,
                PopulationValueType.WeightInKilograms
            ),
        )
}