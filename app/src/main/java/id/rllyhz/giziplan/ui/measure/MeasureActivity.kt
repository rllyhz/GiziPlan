package id.rllyhz.giziplan.ui.measure

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.rllyhz.giziplan.R
import id.rllyhz.giziplan.databinding.ActivityMeasureBinding
import id.rllyhz.giziplan.ui.result.ResultActivity

class MeasureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMeasureBinding

    private var selectedGender = 0
    private val minAge = 4 // 4 months
    private val minWeight = 4 // 4 kg
    private val minHeight = 4 // 4 cm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeasureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            ArrayAdapter.createFromResource(
                this@MeasureActivity,
                R.array.gender,
                R.layout.dropdown_popup_item,
            ).also { adapter ->
                measureSpinnerGender.adapter = adapter
            }

            measureSpinnerGender.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        selectedGender = position
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        selectedGender = 0
                    }
                }

            measureBtnMeasure.setOnClickListener {
                var isValid = true

                if (measureEtAge.text.isNullOrEmpty() || measureEtAge.text.isBlank()) {
                    measureEtAge.error = getString(R.string.message_age_required)
                    isValid = false
                } else {
                    val age = measureEtAge.text.toString().toIntOrNull() ?: 0
                    if (age < minAge) {
                        measureEtAge.error = getString(R.string.message_min_age_required)
                        isValid = false
                    }
                }

                if (measureEtHeight.text.isNullOrEmpty() || measureEtHeight.text.isBlank()) {
                    measureEtHeight.error = getString(R.string.message_height_required)
                    isValid = false
                }

                if (measureEtWeight.text.isNullOrEmpty() || measureEtWeight.text.isBlank()) {
                    measureEtWeight.error = getString(R.string.message_weight_required)
                    isValid = false
                }

                if (selectedGender == 0) {
                    Toast.makeText(
                        this@MeasureActivity,
                        getString(R.string.message_gender_required),
                        Toast.LENGTH_SHORT
                    ).show()
                    isValid = false
                }

                if (!isValid) return@setOnClickListener

                Intent(this@MeasureActivity, ResultActivity::class.java).also { i ->
                    startActivity(i)
                }
            }

            measureEtAge.requestFocus()
        }
    }
}