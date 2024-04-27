package id.rllyhz.giziplan.utils.fakes

import id.rllyhz.giziplan.data.anthropometry.core.AnthropometryDao
import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryDataTable
import id.rllyhz.giziplan.data.anthropometry.type.PopulationValueType
import id.rllyhz.giziplan.data.anthropometry.type.ReferenceValueType
import id.rllyhz.giziplan.utils.createDummyDataTable

class FakeAnthropometryDao : AnthropometryDao {
    override suspend fun getWeightToAgeDataTable(): AnthropometryDataTable =
        createDummyDataTable(
            ReferenceValueType.AgeInMonths,
            PopulationValueType.WeightInKilograms
        )

    override suspend fun getHeightToAgeDataTable(): AnthropometryDataTable =
        createDummyDataTable(
            ReferenceValueType.AgeInMonths,
            PopulationValueType.HeightInCentimeters
        )

    override suspend fun getWeightToHeightDataTable(): AnthropometryDataTable =
        createDummyDataTable(
            ReferenceValueType.HeightInCentimeters,
            PopulationValueType.WeightInKilograms
        )
}