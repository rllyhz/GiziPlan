package id.rllyhz.giziplan.ui.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.rllyhz.giziplan.databinding.MenuItemBinding
import id.rllyhz.giziplan.domain.model.MenuModel

class MenuListAdapter : ListAdapter<MenuModel, MenuListAdapter.MenuViewHolder>(Comparator) {

    private var itemClickedListener: ((MenuModel, Int) -> Unit)? = null

    fun setOnItemClickedListener(cb: ((MenuModel, Int) -> Unit)?) {
        itemClickedListener = cb
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder =
        MenuViewHolder(
            MenuItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        currentList.getOrNull(position)?.let {
            holder.bind(it, position)
        }
    }

    inner class MenuViewHolder(
        private val binding: MenuItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(menu: MenuModel, position: Int) {
            with(binding) {
                menuItemTvMenuTitle.text = menu.title
                menuItemTvMenuDescription.text = menu.description

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