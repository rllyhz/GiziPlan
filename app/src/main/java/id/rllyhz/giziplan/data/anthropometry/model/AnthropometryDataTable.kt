package id.rllyhz.giziplan.data.anthropometry.model

import id.rllyhz.giziplan.data.anthropometry.type.ReferenceValueType
import id.rllyhz.giziplan.data.anthropometry.type.PopulationValueType

data class AnthropometryDataTable(
    val referenceValueType: ReferenceValueType,
    val populationValueType: PopulationValueType,
    val malePopulationTable: List<PopulationRow>,
    val femalePopulationTable: List<PopulationRow>,
)