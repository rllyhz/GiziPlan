package id.rllyhz.giziplan.ui.result

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import id.rllyhz.giziplan.databinding.ActivityResultBinding
import id.rllyhz.giziplan.ui.menu.MenuListAdapter
import id.rllyhz.giziplan.ui.utils.hide
import id.rllyhz.giziplan.ui.utils.show
import id.rllyhz.giziplan.utils.createDummyMenuData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private lateinit var menuAdapter: MenuListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuAdapter = MenuListAdapter().apply {
            setOnItemClickedListener { menuModel, _ ->
                Toast.makeText(
                    this@ResultActivity, menuModel.name,
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        binding.resultRvMenuRecommendation.adapter = menuAdapter
        binding.resultRvMenuRecommendation.setHasFixedSize(true)

        lifecycleScope.launch {
            showResult()
        }
    }

    private suspend fun showResult() = withContext(Dispatchers.IO) {
        delay(1000)

        withContext(Dispatchers.Main) {
            menuAdapter.submitList(createDummyMenuData(20))
            binding.resultLayoutLoading.hide()
            binding.resultLayoutContent.show()
        }
    }
}