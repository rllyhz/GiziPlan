package id.rllyhz.giziplan.ui.menu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import id.rllyhz.giziplan.GiziPlanApplication
import id.rllyhz.giziplan.databinding.ActivityMenuBinding
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.ui.detail.DetailMenuActivity
import id.rllyhz.giziplan.ui.utils.hide
import id.rllyhz.giziplan.ui.utils.show
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var menuAdapter: MenuListAdapter

    private lateinit var viewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as GiziPlanApplication).appModule?.getMainRepository()?.let {
            viewModel = ViewModelProvider(
                this, MenuViewModel.Factory(it)
            )[MenuViewModel::class.java]
        }

        menuAdapter = MenuListAdapter().apply {
            setOnItemClickedListener { menuModel, _ ->
                Intent(this@MenuActivity, DetailMenuActivity::class.java).also {
                    it.putExtra(DetailMenuActivity.intentDetailMenuDataKey, menuModel)
                    startActivity(it)
                }
            }
        }

        binding.menuRvMenu.adapter = menuAdapter
        binding.menuRvMenu.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        binding.menuRvMenu.setHasFixedSize(true)

        showLoadingUI()

        lifecycleScope.launch {
            viewModel.loadMenu().collectLatest { dataState ->
                val menu = dataState.data
                val error = dataState.error

                when {
                    error != null -> showErrorUI()
                    else -> menu?.let { showHasDataUI(it) }
                }
            }
        }
    }

    private fun showErrorUI() {
        binding.menuRvMenu.hide()
        binding.menuProgressbar.hide()
        binding.menuTvHeading.show()
        binding.menuFadingView.hide()
        binding.menuTvErrorMessage.show()
    }

    private fun showLoadingUI() {
        binding.menuTvErrorMessage.hide()
        binding.menuRvMenu.hide()
        binding.menuFadingView.hide()
        binding.menuTvHeading.show()
        binding.menuProgressbar.show()
    }

    private fun showHasDataUI(newMenuData: List<MenuModel>) {
        menuAdapter.submitList(newMenuData)

        binding.menuTvErrorMessage.hide()
        binding.menuProgressbar.hide()
        binding.menuTvHeading.show()
        binding.menuFadingView.show()
        binding.menuRvMenu.show()
    }
}