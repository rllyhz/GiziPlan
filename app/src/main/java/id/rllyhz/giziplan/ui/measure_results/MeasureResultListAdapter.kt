package id.rllyhz.giziplan.ui.measure_results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.rllyhz.giziplan.R
import id.rllyhz.giziplan.databinding.ItemMeasureResultBinding
import id.rllyhz.giziplan.domain.model.MeasureResultModel
import id.rllyhz.giziplan.utils.toDateNumericalString
import id.rllyhz.giziplan.utils.toDateString

class MeasureResultListAdapter :
    ListAdapter<MeasureResultModel, MeasureResultListAdapter.MeasureResultViewHolder>(Comparator) {

    private var itemClickedListener: ((MeasureResultModel, Int) -> Unit)? = null

    fun setOnItemClickedListener(cb: ((MeasureResultModel, Int) -> Unit)?) {
        itemClickedListener = cb
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasureResultViewHolder =
        MeasureResultViewHolder(
            ItemMeasureResultBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    override fun onBindViewHolder(holder: MeasureResultViewHolder, position: Int) {
        currentList.getOrNull(position)?.let {
            holder.bind(it, position)
        }
    }

    inner class MeasureResultViewHolder(
        private val binding: ItemMeasureResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(measureResult: MeasureResultModel, position: Int) {
            with(binding) {
                measureResultTvTitle.text = root.context.resources.getString(
                    R.string.measure_result_title_template, measureResult.id
                )
                measureResultTvDescription.text = root.context.resources.getString(
                    R.string.measure_result_description_template,
                    measureResult.createdAt.toDateNumericalString().toDateString()
                )

                measureResultItemCardBackground.setOnClickListener {
                    itemClickedListener?.invoke(measureResult, position)
                }
            }
        }
    }

    object Comparator : DiffUtil.ItemCallback<MeasureResultModel>() {
        override fun areItemsTheSame(
            oldItem: MeasureResultModel, newItem: MeasureResultModel
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: MeasureResultModel, newItem: MeasureResultModel
        ): Boolean = oldItem == newItem
    }
}