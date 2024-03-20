package id.rllyhz.giziplan.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.rllyhz.giziplan.R
import id.rllyhz.giziplan.databinding.ActivityMainBinding
import id.rllyhz.giziplan.ui.measure.MeasureActivity
import id.rllyhz.giziplan.ui.menu.MenuActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val backPressedCallback = object : OnBackPressedCallback(enabled = true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setTheme(R.style.Theme_GiziPlan)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainBtnGoMeasure.setOnClickListener {
            Intent(this@MainActivity, MeasureActivity::class.java).also { i ->
                startActivity(i)
            }
        }

        binding.mainBtnShowMenu.setOnClickListener {
            Intent(this@MainActivity, MenuActivity::class.java).also { i ->
                startActivity(i)
            }
        }


        onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }
}