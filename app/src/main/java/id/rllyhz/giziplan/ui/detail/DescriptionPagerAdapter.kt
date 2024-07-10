package id.rllyhz.giziplan.ui.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.rllyhz.giziplan.R
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.ui.detail.view.IngredientsFragment
import id.rllyhz.giziplan.ui.detail.view.InstructionsFragment

class DescriptionPagerAdapter(
    activity: AppCompatActivity,
    private val detailMenu: MenuModel
) : FragmentStateAdapter(activity) {
    companion object {
        val tabTitles = listOf(
            R.string.ingridients_label,
            R.string.instructions_label,
        )
    }

    override fun getItemCount(): Int =
        tabTitles.size

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> IngredientsFragment.newInstance(detailMenu)
            else -> InstructionsFragment.newInstance(detailMenu)
        }
}