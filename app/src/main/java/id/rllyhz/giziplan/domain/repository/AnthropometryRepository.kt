package id.rllyhz.giziplan.domain.repository

import id.rllyhz.giziplan.data.anthropometry.model.AnthropometryTables
import id.rllyhz.giziplan.data.anthropometry.model.PopulationRow
import id.rllyhz.giziplan.data.anthropometry.type.Gender

interface AnthropometryRepository {

    suspend fun getAllAnthropometryTables(): AnthropometryTables

    suspend fun getWeightToAgePopulation(gender: Gender): List<PopulationRow>

    suspend fun getHeightToAgePopulation(gender: Gender): List<PopulationRow>

    suspend fun getWeightToHeightPopulation(gender: Gender): List<PopulationRow>
}