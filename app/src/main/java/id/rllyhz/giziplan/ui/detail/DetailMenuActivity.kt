package id.rllyhz.giziplan.ui.detail

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import id.rllyhz.giziplan.R
import id.rllyhz.giziplan.databinding.ActivityDetailMenuBinding
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.ui.utils.getDrawableResId
import id.rllyhz.giziplan.ui.utils.hide
import id.rllyhz.giziplan.ui.utils.show
import id.rllyhz.giziplan.utils.concatIfSeparatedBy

class DetailMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMenuBinding
    private lateinit var adapter: DescriptionPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GiziPlan)
        window.statusBarColor = ContextCompat.getColor(this, R.color.grey_color)

        binding = ActivityDetailMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailMenu = intent.getParcelableExtra<MenuModel>(intentDetailMenuDataKey)!!
        adapter = DescriptionPagerAdapter(this, detailMenu)

        with(binding) {
            val imgPath = detailMenu.imagePath ?: ""
            detailMenuIvMenuImage.setImageResource(getDrawableResId(imgPath, true))
            detailMenuIvMenuImage.scaleType = ImageView.ScaleType.CENTER_CROP

            detailMenuTvMenuTitle.text = detailMenu.name

            if (detailMenu.description == null) {
                detailMenuTvMenuNotes.hide()
            } else {
                detailMenuTvMenuNotes.text = detailMenu.description
                    .concatIfSeparatedBy(";")

                detailMenuTvMenuNotes.show()
            }

            println(detailMenu.description)

            detailMenuTvEnergyValue.text =
                resources.getString(R.string.energy_template, detailMenu.energyKiloCal.toString())
            detailMenuTvProteinValue.text =
                resources.getString(R.string.protein_template, detailMenu.proteinGr.toString())
            detailMenuTvFatValue.text =
                resources.getString(R.string.fat_template, detailMenu.fatGr.toString())

            detailMenuViewPager.adapter = adapter
            TabLayoutMediator(detailMenuTabLayout, detailMenuViewPager) { tab, position ->
                tab.text = resources.getString(DescriptionPagerAdapter.tabTitles[position])
            }.attach()

            detailMenuIvBack.setOnClickListener {
                finish()
            }
        }
    }

    companion object {
        const val intentDetailMenuDataKey = "intentDetailMenuDataKey"
    }
}