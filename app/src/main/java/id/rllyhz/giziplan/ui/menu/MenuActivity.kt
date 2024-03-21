package id.rllyhz.giziplan.ui.menu

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.rllyhz.giziplan.databinding.ActivityMenuBinding
import id.rllyhz.giziplan.utils.DataUtil

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    private lateinit var menuAdapter: MenuListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuAdapter = MenuListAdapter().apply {
            submitList(DataUtil.createDummyMenuData(20))

            setOnItemClickedListener { menuModel, _ ->
                Toast.makeText(
                    this@MenuActivity, menuModel.title,
                    Toast.LENGTH_SHORT,
                )
                    .show()
            }
        }

        binding.menuRvMenu.adapter = menuAdapter
        binding.menuRvMenu.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        binding.menuRvMenu.setHasFixedSize(true)
    }
}