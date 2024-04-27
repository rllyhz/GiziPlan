package id.rllyhz.giziplan.data.anthropometry.core

import android.content.Context
import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryDataTable
import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.data.anthropometry.type.PopulationValueType
import id.rllyhz.giziplan.data.anthropometry.type.ReferenceValueType
import id.rllyhz.giziplan.data.anthropometry.utils.toPopulationRow
import id.rllyhz.giziplan.utils.getHeightToAgePopulationTableData
import id.rllyhz.giziplan.utils.getWeightToHeightGreaterThan24PopulationTableData
import id.rllyhz.giziplan.utils.getWeightToHeightLessThan24PopulationTableData

class AnthropometryCSV(
    private val context: Context
) {
    fun getDao(): AnthropometryDao = object : AnthropometryDao {
        override suspend fun getWeightToAgeDataTable(): AnthropometryDataTable =
            AnthropometryDataTable(
                ReferenceValueType.AgeInMonths,
                PopulationValueType.WeightInKilograms,
                getHeightToAgePopulationTableData(context, Gender.Male).toPopulationRow(
                    ReferenceValueType.AgeInMonths, PopulationValueType.WeightInKilograms
                ),
                getHeightToAgePopulationTableData(context, Gender.Female).toPopulationRow(
                    ReferenceValueType.AgeInMonths, PopulationValueType.WeightInKilograms
                ),
            )

        override suspend fun getHeightToAgeDataTable(): AnthropometryDataTable =
            AnthropometryDataTable(
                ReferenceValueType.AgeInMonths,
                PopulationValueType.HeightInCentimeters,
                getHeightToAgePopulationTableData(context, Gender.Male).toPopulationRow(
                    ReferenceValueType.AgeInMonths, PopulationValueType.HeightInCentimeters
                ),
                getHeightToAgePopulationTableData(context, Gender.Female).toPopulationRow(
                    ReferenceValueType.AgeInMonths, PopulationValueType.HeightInCentimeters
                ),
            )

        override suspend fun getWeightToHeightLessThan24DataTable(): AnthropometryDataTable =
            AnthropometryDataTable(
                ReferenceValueType.HeightInCentimeters,
                PopulationValueType.WeightInKilograms,
                getWeightToHeightLessThan24PopulationTableData(
                    context,
                    Gender.Male
                ).toPopulationRow(
                    ReferenceValueType.HeightInCentimeters, PopulationValueType.WeightInKilograms
                ),
                getWeightToHeightLessThan24PopulationTableData(
                    context,
                    Gender.Female
                ).toPopulationRow(
                    ReferenceValueType.HeightInCentimeters, PopulationValueType.WeightInKilograms
                ),
            )

        override suspend fun getWeightToHeightGreaterThan24DataTable(): AnthropometryDataTable =
            AnthropometryDataTable(
                ReferenceValueType.HeightInCentimeters,
                PopulationValueType.WeightInKilograms,
                getWeightToHeightGreaterThan24PopulationTableData(
                    context,
                    Gender.Male
                ).toPopulationRow(
                    ReferenceValueType.HeightInCentimeters, PopulationValueType.WeightInKilograms
                ),
                getWeightToHeightGreaterThan24PopulationTableData(
                    context,
                    Gender.Female
                ).toPopulationRow(
                    ReferenceValueType.HeightInCentimeters, PopulationValueType.WeightInKilograms
                ),
            )
    }
}