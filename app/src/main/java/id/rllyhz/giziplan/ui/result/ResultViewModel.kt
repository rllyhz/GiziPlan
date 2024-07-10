package id.rllyhz.giziplan.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.model.classification.GoodNutritionalStatus
import id.rllyhz.giziplan.domain.model.classification.NormalHeight
import id.rllyhz.giziplan.domain.model.classification.NormalWeight
import id.rllyhz.giziplan.domain.model.classification.Obese
import id.rllyhz.giziplan.domain.model.classification.Overweight
import id.rllyhz.giziplan.domain.model.classification.PossibleRiskOfOverweight
import id.rllyhz.giziplan.domain.model.classification.SeverelyWasted
import id.rllyhz.giziplan.domain.model.classification.Wasted
import id.rllyhz.giziplan.domain.model.zscore.ZScoreCategory
import id.rllyhz.giziplan.domain.model.zscore.ZScoreClassificationData
import id.rllyhz.giziplan.domain.model.zscore.ZScoreData
import id.rllyhz.giziplan.domain.repository.AnthropometryRepository
import id.rllyhz.giziplan.domain.repository.DatabaseRepository
import id.rllyhz.giziplan.domain.usecase.anthropometry.AnthropometryInteractor
import id.rllyhz.giziplan.recommender.Recommender
import id.rllyhz.giziplan.recommender.getMenuOverviews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class ResultViewModel(
    private val dbRepository: DatabaseRepository,
    private val anthropometryRepository: AnthropometryRepository,
) : ViewModel() {
    private val initialZScoreData = ZScoreData(0.0, ZScoreCategory.WeightToHeight, true)

    var weightToAgeClassificationData = ZScoreClassificationData(initialZScoreData, NormalWeight)
    var heightToAgeClassificationData = ZScoreClassificationData(initialZScoreData, NormalHeight)
    var weightToHeightClassificationData =
        ZScoreClassificationData(initialZScoreData, GoodNutritionalStatus)

    var recommendationMenu: List<MenuModel> = emptyList()

    val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)

    fun getRecommendationOf(
        weight: Double,
        height: Double,
        age: Int,
        gender: Gender,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val interactor = AnthropometryInteractor(anthropometryRepository)

            // measure anthropometry
            val weightToAgeResult = interactor.measureZScoreForWeightToAge(weight, age, gender)
            val heightToAgeResult = interactor.measureZScoreForHeightToAge(height, age, gender)
            val weightToHeightResult =
                interactor.measureZScoreForWeightToHeight(weight, height, age < 24, gender)

            // classify anthropometry
            weightToAgeClassificationData = interactor.classifyZScore(weightToAgeResult)
            heightToAgeClassificationData = interactor.classifyZScore(heightToAgeResult)
            weightToHeightClassificationData = interactor.classifyZScore(weightToHeightResult)

            // get recommendation
            val allMenus = dbRepository.getAllMenus().last().data ?: return@launch

            dbRepository.getAllMenus().collectLatest {
                if (it.error != null) return@collectLatest
                if (it.data.isNullOrEmpty()) return@collectLatest

                val nutritionStatus =
                    when (weightToHeightClassificationData.classificationResult.getClassificationId()) {
                        SeverelyWasted.getClassificationId() -> "buruk"
                        Wasted.getClassificationId() -> "buruk"
                        GoodNutritionalStatus.getClassificationId() -> "normal"
                        PossibleRiskOfOverweight.getClassificationId() -> "lebih"
                        Overweight.getClassificationId() -> "lebih"
                        Obese.getClassificationId() -> "lebih"
                        else -> "normal"
                    }

                val indexes =
                    Recommender.getRecommendation(nutritionStatus, age, getMenuOverviews())
                        .shuffled()
                val temp = arrayListOf<MenuModel>()
                indexes.forEach { idx ->
                    temp.add(allMenus[idx])
                }

                recommendationMenu = temp

                isLoading.emit(false)
            }
        }
    }

    class Factory(
        private val dbRepository: DatabaseRepository,
        private val anthropometryRepository: AnthropometryRepository,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ResultViewModel(dbRepository, anthropometryRepository) as T
        }
    }
}