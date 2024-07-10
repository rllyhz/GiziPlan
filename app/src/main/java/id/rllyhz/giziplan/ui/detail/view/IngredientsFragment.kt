package id.rllyhz.giziplan.ui.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import id.rllyhz.giziplan.R
import id.rllyhz.giziplan.databinding.FragmentViewIngredientsBinding
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.utils.parseUnorderedStringListData
import id.rllyhz.giziplan.utils.capitalize

class IngredientsFragment : Fragment() {
    private var _binding: FragmentViewIngredientsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val argDetailMenu = "detailMenu"

        fun newInstance(detailMenu: MenuModel): IngredientsFragment =
            IngredientsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(argDetailMenu, detailMenu)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailMenu = arguments?.getParcelable<MenuModel>(argDetailMenu)!!
        val ingredients = detailMenu.ingredients
        val parsedListData = parseUnorderedStringListData(ingredients)

        if (!parsedListData.hasSubLabel) {
            val ingredientsData = parsedListData.data[0]

            val labelTextView = LayoutInflater.from(requireContext())
                .inflate(R.layout.view_description_label, null) as TextView
            labelTextView.text = "Bahan:".capitalize()
            binding.fragmentIngredientsRoot.addView(labelTextView)

            LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
            ).apply { setMargins(0, 0, 0, 24) }.also { params ->
                labelTextView.layoutParams = params
            }

            for (ingredientRowText in ingredientsData.listData) {
                val tableRow = LayoutInflater.from(requireContext())
                    .inflate(R.layout.view_description_table_row, null) as TableRow
                tableRow.findViewById<TextView>(R.id.table_row_bullet).text = "-"
                tableRow.findViewById<TextView>(R.id.table_row_text).text =
                    ingredientRowText.capitalize()

                binding.fragmentIngredientsRoot.addView(tableRow)
            }

            return
        }

        // has sub label
        parsedListData.data.forEachIndexed { index, ingredientsData ->
            if (index > 0) {
                View(requireContext()).apply {
                    LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        32,
                    ).also {
                        layoutParams = it
                    }
                }.also { binding.fragmentIngredientsRoot.addView(it) }
            }

            val labelTextView = LayoutInflater.from(requireContext())
                .inflate(R.layout.view_description_label, null) as TextView
            labelTextView.text = (ingredientsData.label + ":").capitalize()
            binding.fragmentIngredientsRoot.addView(labelTextView)

            LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
            ).apply { setMargins(0, 0, 0, 24) }.also { params ->
                labelTextView.layoutParams = params
            }

            for (ingredientRowText in ingredientsData.listData) {
                val tableRow = LayoutInflater.from(requireContext())
                    .inflate(R.layout.view_description_table_row, null) as TableRow
                tableRow.findViewById<TextView>(R.id.table_row_bullet).text = "-"
                tableRow.findViewById<TextView>(R.id.table_row_text).text =
                    ingredientRowText.capitalize()

                binding.fragmentIngredientsRoot.addView(tableRow)
            }
        }
    }
}