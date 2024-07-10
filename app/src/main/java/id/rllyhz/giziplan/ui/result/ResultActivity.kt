package id.rllyhz.giziplan.ui.result

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import id.rllyhz.giziplan.GiziPlanApplication
import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.databinding.ActivityResultBinding
import id.rllyhz.giziplan.domain.model.classification.GoodNutritionalStatus
import id.rllyhz.giziplan.domain.model.classification.NormalHeight
import id.rllyhz.giziplan.domain.model.classification.NormalWeight
import id.rllyhz.giziplan.domain.model.classification.Obese
import id.rllyhz.giziplan.domain.model.classification.PossibleRiskOfOverweight
import id.rllyhz.giziplan.domain.model.classification.RiskOfOverweight
import id.rllyhz.giziplan.domain.model.classification.SeverelyStunted
import id.rllyhz.giziplan.domain.model.classification.SeverelyUnderweight
import id.rllyhz.giziplan.domain.model.classification.SeverelyWasted
import id.rllyhz.giziplan.domain.model.classification.Stunted
import id.rllyhz.giziplan.domain.model.classification.Tall
import id.rllyhz.giziplan.domain.model.classification.Underweight
import id.rllyhz.giziplan.domain.model.classification.Wasted
import id.rllyhz.giziplan.ui.detail.DetailMenuActivity
import id.rllyhz.giziplan.ui.menu.MenuListAdapter
import id.rllyhz.giziplan.ui.utils.hide
import id.rllyhz.giziplan.ui.utils.show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultActivity : AppCompatActivity() {
    companion object {
        const val intentDataWeight = "intentDataWeight"
        const val intentDataHeight = "intentDataHeight"
        const val intentDataAge = "intentDataAge"
        const val intentDataGender = "intentDataGender"
    }

    private lateinit var binding: ActivityResultBinding
    private lateinit var viewModel: ResultViewModel

    private lateinit var menuAdapter: MenuListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appModule = (applicationContext as GiziPlanApplication).appModule!!

        viewModel = ViewModelProvider(
            this,
            ResultViewModel.Factory(
                appModule.getMainRepository(),
                appModule.getAnthropometryRepository()
            )
        )[ResultViewModel::class.java]

        menuAdapter = MenuListAdapter().apply {
            setOnItemClickedListener { menuModel, _ ->
                Intent(this@ResultActivity, DetailMenuActivity::class.java).also {
                    it.putExtra(DetailMenuActivity.intentDetailMenuDataKey, menuModel)
                    startActivity(it)
                }
            }
        }

        binding.resultRvMenuRecommendation.adapter = menuAdapter
        binding.resultRvMenuRecommendation.setHasFixedSize(true)

        lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                withContext(Dispatchers.Main) {
                    if (isLoading) {
                        binding.resultLayoutLoading.show()
                        binding.resultLayoutContent.hide()
                    } else {
                        showResult()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val weight = intent.getDoubleExtra(intentDataWeight, 0.0)
        val height = intent.getDoubleExtra(intentDataHeight, 0.0)
        val age = intent.getIntExtra(intentDataAge, 0)
        val gender = intent.getIntExtra(intentDataGender, 0)

        viewModel.getRecommendationOf(
            weight,
            height,
            age,
            if (gender == 0) Gender.Male else Gender.Female
        )
    }

    private fun showResult() {
        with(binding) {

            resultStatusBbuValue.text =
                when (viewModel.weightToAgeClassificationData.classificationResult.getClassificationId()) {
                    SeverelyUnderweight.getClassificationId() -> "Berat Badan\nSangat Kurang"
                    Underweight.getClassificationId() -> "Berat Badan\nKurang"
                    NormalWeight.getClassificationId() -> "Normal"
                    RiskOfOverweight.getClassificationId() -> "Berat Badan Lebih"
                    else -> "Normal"
                }

            resultStatusPbuValue.text =
                when (viewModel.heightToAgeClassificationData.classificationResult.getClassificationId()) {
                    SeverelyStunted.getClassificationId() -> "Sangat\nPendek"
                    Stunted.getClassificationId() -> "Pendek"
                    NormalHeight.getClassificationId() -> "Normal"
                    Tall.getClassificationId() -> "Tinggi"
                    else -> "Normal"
                }

            resultStatusBbpbValue.text =
                when (viewModel.weightToHeightClassificationData.classificationResult.getClassificationId()) {
                    SeverelyWasted.getClassificationId() -> "Gizi\nBuruk"
                    Wasted.getClassificationId() -> "Gizi\nKurang"
                    GoodNutritionalStatus.getClassificationId() -> "Gizi\nBaik"
                    PossibleRiskOfOverweight.getClassificationId() -> "Gizi\nLebih"
                    Obese.getClassificationId() -> "Obesitas"
                    else -> "Gizi\nBaik"
                }

            resultLayoutLoading.hide()
            resultLayoutContent.show()

            menuAdapter.submitList(viewModel.recommendationMenu)
        }
    }
}