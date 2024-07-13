package id.rllyhz.giziplan.ui.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.rllyhz.giziplan.databinding.ItemMenuBinding
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.ui.utils.getDrawableResId

class MenuListAdapter : ListAdapter<MenuModel, MenuListAdapter.MenuViewHolder>(Comparator) {

    private var itemClickedListener: ((MenuModel, Int) -> Unit)? = null

    fun setOnItemClickedListener(cb: ((MenuModel, Int) -> Unit)?) {
        itemClickedListener = cb
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder =
        MenuViewHolder(
            ItemMenuBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        currentList.getOrNull(position)?.let {
            holder.bind(it, position)
        }
    }

    inner class MenuViewHolder(
        private val binding: ItemMenuBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(menu: MenuModel, position: Int) {
            with(binding) {
                menuItemTvMenuTitle.text = menu.name
                menuItemTvMenuDescription.text = when (menu.ageCategory.uppercase()) {
                    "A" -> "Untuk umur 6-8 bulan"
                    "B" -> "Untuk umur 9-11 bulan"
                    "C" -> "Untuk umur 12-23 bulan"
                    "D" -> "Untuk umur 24-59 bulan"
                    else -> "Untuk umur 6-8 bulan"
                }

                menu.imagePath?.let { imgPath ->
                    val drawableResId = getDrawableResId(imgPath)
                    val drawablePreview = ContextCompat.getDrawable(root.context, drawableResId)
                    menuItemIvPreview.setImageDrawable(drawablePreview)
                    menuItemIvPreview.scaleType = ImageView.ScaleType.CENTER_CROP
                }

                menuItemCardBackground.setOnClickListener {
                    itemClickedListener?.invoke(menu, position)
                }
            }
        }
    }

    object Comparator : DiffUtil.ItemCallback<MenuModel>() {
        override fun areItemsTheSame(oldItem: MenuModel, newItem: MenuModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MenuModel, newItem: MenuModel): Boolean =
            oldItem == newItem
    }
}