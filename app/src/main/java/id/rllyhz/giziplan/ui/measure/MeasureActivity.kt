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
    private val minAge = 6 // 6 months
    private val minWeight = 1.9 // 4 kg
    private val minHeight = 45.0 // 45.0 cm

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
                        parent: AdapterView<*>?, view: View?, position: Int, id: Long
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
                } else {
                    val height = measureEtHeight.text.toString().toDoubleOrNull() ?: 0.0
                    if (height < minHeight) {
                        measureEtHeight.error = getString(R.string.message_min_height_required)
                        isValid = false
                    }
                }

                if (measureEtWeight.text.isNullOrEmpty() || measureEtWeight.text.isBlank()) {
                    measureEtWeight.error = getString(R.string.message_weight_required)
                    isValid = false
                } else {
                    val weight = measureEtWeight.text.toString().toDoubleOrNull() ?: 0.0
                    if (weight < minWeight) {
                        measureEtWeight.error = getString(R.string.message_min_weight_required)
                        isValid = false
                    }
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

                val age = validateAgeValue(measureEtAge.text.toString().toIntOrNull() ?: 0)
                val height = validateHeightValue(
                    measureEtHeight.text.toString().toDoubleOrNull() ?: 0.0, age
                )
                val weight = validateWeightValue(
                    measureEtWeight.text.toString().toDoubleOrNull() ?: 0.0, age
                )
                val gender = selectedGender

                Intent(this@MeasureActivity, ResultActivity::class.java).also { i ->
                    i.putExtra(ResultActivity.intentDataWeight, weight)
                    i.putExtra(ResultActivity.intentDataHeight, height)
                    i.putExtra(ResultActivity.intentDataAge, age)
                    i.putExtra(ResultActivity.intentDataGender, gender)
                    startActivity(i)
                }
            }

            measureEtAge.requestFocus()
        }
    }

    private fun validateAgeValue(age: Int): Int = if (age < 6) 6
    else if (age > 59) 59
    else age

    private fun validateHeightValue(height: Double, age: Int): Double {
        val result: Double

        if (age >= 24) {
            result = if (height < 65.0) {
                65.0
            } else if (height > 120.0) {
                120.0
            } else {
                height
            }
        } else {
            result = if (height < 45.0) {
                45.0
            } else if (height > 110.0) {
                110.0
            } else {
                height
            }
        }

        return result
    }

    private fun validateWeightValue(weight: Double, age: Int): Double {
        val result: Double

        if (age >= 24) {
            result = if (weight < 5.9) {
                5.9
            } else if (weight > 30.1) {
                30.1
            } else {
                weight
            }
        } else {
            result = if (weight < 1.9) {
                1.9
            } else if (weight > 24.1) {
                24.1
            } else {
                weight
            }
        }

        return result
    }
}