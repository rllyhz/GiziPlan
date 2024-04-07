package id.rllyhz.giziplan.domain.repository

import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.model.RecommendationResultModel
import kotlinx.coroutines.flow.Flow

interface GiziRepository {
    fun getAllMenus(): Flow<List<MenuModel>>

    fun getMenuById(menuId: Int): MenuModel? = null

    fun getAllRecommendationResults(): Flow<List<RecommendationResultModel>>
}