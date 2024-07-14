package id.rllyhz.giziplan.ui.measure_results

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import id.rllyhz.giziplan.GiziPlanApplication
import id.rllyhz.giziplan.R
import id.rllyhz.giziplan.databinding.ActivityMeasureResultsBinding
import id.rllyhz.giziplan.domain.model.MeasureResultModel
import id.rllyhz.giziplan.ui.result.ResultActivity
import id.rllyhz.giziplan.ui.utils.hide
import id.rllyhz.giziplan.ui.utils.show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MeasureResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMeasureResultsBinding
    private lateinit var adapter: MeasureResultListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GiziPlan)

        binding = ActivityMeasureResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoadingUI()

        adapter = MeasureResultListAdapter().apply {
            setOnItemClickedListener { measureResultModel, i ->
                Intent(this@MeasureResultsActivity, ResultActivity::class.java).also {
                    it.putExtra(ResultActivity.intentDataIsFromMeasureResultsKey, true)
                    it.putExtra(ResultActivity.intentDataMeasureResultKey, measureResultModel)
                    startActivity(it)
                }
            }
        }
        binding.measureResultsRv.adapter = adapter
        binding.measureResultsRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        binding.measureResultsRv.setHasFixedSize(true)

        val module = (applicationContext as GiziPlanApplication).appModule ?: return
        val repository = module.getMainRepository()

        lifecycleScope.launch {
            repository.getAllMeasureResults().collectLatest {
                val data = it.data ?: return@collectLatest

                if (data.isEmpty()) {
                    withContext(Dispatchers.Main) { showEmptyDataUI() }
                } else {
                    withContext(Dispatchers.Main) { showHasDataUI(data) }
                }
            }
        }
    }

    private fun showLoadingUI() {
        with(binding) {
            measureResultsRv.hide()
            measureResultsTvEmptyMessage.hide()
            measureResultsProgressbar.show()
        }
    }

    private fun showEmptyDataUI() {
        with(binding) {
            measureResultsRv.hide()
            measureResultsProgressbar.hide()
            measureResultsTvEmptyMessage.show()
        }
    }

    private fun showHasDataUI(data: List<MeasureResultModel>) {
        with(binding) {
            measureResultsTvEmptyMessage.hide()
            measureResultsProgressbar.hide()
            measureResultsRv.show()
        }

        adapter.submitList(data)
    }
}