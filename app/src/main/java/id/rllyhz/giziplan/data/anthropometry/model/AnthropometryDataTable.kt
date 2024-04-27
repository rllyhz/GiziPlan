package id.rllyhz.giziplan.data.anthropometry.model

import id.rllyhz.giziplan.data.anthropometry.type.MeasuredValueType
import id.rllyhz.giziplan.data.anthropometry.type.PopulationValueType

data class AnthropometryDataTable(
    val measuredValueType: MeasuredValueType,
    val populationValueType: PopulationValueType,
    val malePopulationTable: List<PopulationRow>,
    val femalePopulationTable: List<PopulationRow>,
)