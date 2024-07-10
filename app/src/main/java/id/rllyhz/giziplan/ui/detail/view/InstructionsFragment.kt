package id.rllyhz.giziplan.ui.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import id.rllyhz.giziplan.R
import id.rllyhz.giziplan.databinding.FragmentViewInstructorsBinding
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.utils.parseUnorderedStringListData
import id.rllyhz.giziplan.utils.capitalize

class InstructionsFragment : Fragment() {
    private var _binding: FragmentViewInstructorsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val argDetailMenu = "detailMenu"

        fun newInstance(detailMenu: MenuModel): InstructionsFragment =
            InstructionsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(argDetailMenu, detailMenu)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewInstructorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailMenu = arguments?.getParcelable<MenuModel>(argDetailMenu)!!
        val instructions = detailMenu.instruction
        val parsedListData = parseUnorderedStringListData(instructions)

        if (!parsedListData.hasSubLabel) {
            val instructionsData = parsedListData.data[0]

            val labelTextView = LayoutInflater.from(requireContext())
                .inflate(R.layout.view_description_label, null) as TextView
            labelTextView.text = "Tahapan:".capitalize()
            binding.fragmentInstructionsRoot.addView(labelTextView)

            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            ).apply { setMargins(0, 0, 0, 24) }.also { params ->
                labelTextView.layoutParams = params
            }

            for (instructionRowText in instructionsData.listData) {
                val tableRow = LayoutInflater.from(requireContext())
                    .inflate(R.layout.view_description_table_row, null) as TableRow
                tableRow.findViewById<TextView>(R.id.table_row_bullet).text = "-"
                tableRow.findViewById<TextView>(R.id.table_row_text).text =
                    instructionRowText.capitalize()

                binding.fragmentInstructionsRoot.addView(tableRow)
            }

            return
        }

        // has sub label
        parsedListData.data.forEachIndexed { index, instructionsData ->
            if (index > 0) {
                View(requireContext()).apply {
                    LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        32,
                    ).also {
                        layoutParams = it
                    }
                }.also { binding.fragmentInstructionsRoot.addView(it) }
            }

            val labelTextView = LayoutInflater.from(requireContext())
                .inflate(R.layout.view_description_label, null) as TextView
            labelTextView.text = (instructionsData.label + ":").capitalize()
            binding.fragmentInstructionsRoot.addView(labelTextView)

            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            ).apply { setMargins(0, 0, 0, 24) }.also { params ->
                labelTextView.layoutParams = params
            }

            for (instructionRowText in instructionsData.listData) {
                val tableRow = LayoutInflater.from(requireContext())
                    .inflate(R.layout.view_description_table_row, null) as TableRow
                tableRow.findViewById<TextView>(R.id.table_row_bullet).text = "-"
                tableRow.findViewById<TextView>(R.id.table_row_text).text =
                    instructionRowText.capitalize()

                binding.fragmentInstructionsRoot.addView(tableRow)
            }
        }
    }
}