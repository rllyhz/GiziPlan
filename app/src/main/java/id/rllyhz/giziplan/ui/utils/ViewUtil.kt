package id.rllyhz.giziplan.ui.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import id.rllyhz.giziplan.R

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun TextView.style(
    textColorId: Int,
    textSize: Float,
) {
    setTextColor(textColorId)
    setTextSize(textSize)
}

fun getDrawableResId(name: String, forDetail: Boolean = false): Int = when (name) {
    "menu_bola_nasi.png" -> R.drawable.menu_bola_nasi
    "menu_bubur_ayam_mentega.jpg" -> R.drawable.menu_bubur_ayam_mentega
    "menu_bubur_daging_sapi.png" -> R.drawable.menu_bubur_daging_sapi
    "menu_bubur_hati_ayam.png" -> R.drawable.menu_bubur_hati_ayam
    "menu_bubur_hati_ayam_dan_wortel.png" -> R.drawable.menu_bubur_hati_ayam_dan_wortel
    "menu_bubur_hati_ayam_santan.png" -> R.drawable.menu_bubur_hati_ayam_santan
    "menu_bubur_hati_dan_ayam.jpg" -> R.drawable.menu_bubur_hati_dan_ayam
    "menu_bubur_ikan_jagung.png" -> R.drawable.menu_bubur_ikan_jagung
    "menu_bubur_patin.png" -> R.drawable.menu_bubur_patin
    "menu_bubur_pepes_hati_ayam.png" -> R.drawable.menu_bubur_pepes_hati_ayam
    "menu_bubur_singkong_kukuruyuk_saus_jeruk.png" -> R.drawable.menu_bubur_singkong_kukuruyuk_saus_jeruk
    "menu_bubur_susu_daun_bayam.png" -> R.drawable.menu_bubur_susu_daun_bayam
    "menu_bubur_susu_labu_kuning.jpg" -> R.drawable.menu_bubur_susu_labu_kuning
    "menu_bubur_tenggiri.jpg" -> R.drawable.menu_bubur_tenggiri
    "menu_bubur_udang_buncis.png" -> R.drawable.menu_bubur_udang_buncis
    "menu_bubur_udang_tahu.png" -> R.drawable.menu_bubur_udang_tahu
    "menu_kentang_dori_saus_keju.png" -> R.drawable.menu_kentang_dori_saus_keju
    "menu_mie_goreng_telur_puyuh.png" -> R.drawable.menu_mie_goreng_telur_puyuh
    "menu_mie_tek_tek.png" -> R.drawable.menu_mie_tek_tek
    "menu_nasi_ayam_jamur.jpg" -> R.drawable.menu_nasi_ayam_jamur
    "menu_nasi_ayam_katsu.jpg" -> R.drawable.menu_nasi_ayam_katsu
    "menu_nasi_ayam_katsu_lebih.png" -> R.drawable.menu_nasi_ayam_katsu_lebih
    "menu_nasi_ayam_katsu_tumis_sayuran_nano_nano_melon.png" -> R.drawable.menu_nasi_ayam_katsu_tumis_sayuran_nano_nano_melon
    "menu_nasi_bakar_ikan_tongkol_pepaya.jpg" -> R.drawable.menu_nasi_bakar_ikan_tongkol_pepaya
    "menu_nasi_bakar_tuna.jpg" -> R.drawable.menu_nasi_bakar_tuna
    "menu_nasi_bebek_goreng_mentega.png" -> R.drawable.menu_nasi_bebek_goreng_mentega
    "menu_nasi_cah_siram_sapi.png" -> R.drawable.menu_nasi_cah_siram_sapi
    "menu_nasi_gadon_daging.png" -> R.drawable.menu_nasi_gadon_daging
    "menu_nasi_goreng_udang.jpeg" -> R.drawable.menu_nasi_goreng_udang
    "menu_nasi_gurih_cah_bakso_ikan_brokoli.png" -> R.drawable.menu_nasi_gurih_cah_bakso_ikan_brokoli
    "menu_nasi_pepes_udang.png" -> R.drawable.menu_nasi_pepes_udang
    "menu_nasi_rendang_telur_tempe.png" -> R.drawable.menu_nasi_rendang_telur_tempe
    "menu_nasi_telur_campur.png" -> R.drawable.menu_nasi_telur_campur
    "menu_nasi_telur_daging_kukus.jpg" -> R.drawable.menu_nasi_telur_daging_kukus
    "menu_nasi_tim_ayam_lele_cincang_sari_jeruk.png" -> R.drawable.menu_nasi_tim_ayam_lele_cincang_sari_jeruk
    "menu_nasi_tim_ayam_lodeh.jpg" -> R.drawable.menu_nasi_tim_ayam_lodeh
    "menu_nasi_tim_hati_ayam.jpg" -> R.drawable.menu_nasi_tim_hati_ayam
    "menu_nasi_tim_ikan_telur_puyuh_wortel.jpg" -> R.drawable.menu_nasi_tim_ikan_telur_puyuh_wortel
    "menu_nasi_tim_ikan_telur_sayuran.png" -> R.drawable.menu_nasi_tim_ikan_telur_sayuran
    "menu_nasi_tim_salmon.jpg" -> R.drawable.menu_nasi_tim_salmon
    "menu_nugget_sayuran.jpg" -> R.drawable.menu_nugget_sayuran
    "menu_omelet_mie_ceria.jpg" -> R.drawable.menu_omelet_mie_ceria
    "menu_pepes_tahu.jpg" -> R.drawable.menu_pepes_tahu
    "menu_roti_goreng_isi_ragout_ayam_sayuran.jpg" -> R.drawable.menu_roti_goreng_isi_ragout_ayam_sayuran
    "menu_sate_ayam.png" -> R.drawable.menu_sate_ayam
    "menu_soto_lamongan_jeruk.jpg" -> R.drawable.menu_soto_lamongan_jeruk
    "menu_sup_ayam.jpg" -> R.drawable.menu_sup_ayam
    "menu_sup_ikan_air_tawar_labu_kuning.jpg" -> R.drawable.menu_sup_ikan_air_tawar_labu_kuning
    "menu_tim_ayam_kecap.jpg" -> R.drawable.menu_tim_ayam_kecap
    "menu_tim_daging_sapi.jpg" -> R.drawable.menu_tim_daging_sapi
    "menu_tim_ikan_menado.jpg" -> R.drawable.menu_tim_ikan_menado
    "menu_tim_kuning_semur_hati_ayam.png" -> R.drawable.menu_tim_kuning_semur_hati_ayam
    "menu_tim_makaroni.png" -> R.drawable.menu_tim_makaroni
    "menu_tim_sambal_hati_sapi.jpg" -> R.drawable.menu_tim_sambal_hati_sapi
    "menu_tim_semur_sapi_tempe.png" -> R.drawable.menu_tim_semur_sapi_tempe
    "menu_tim_soto_daging.png" -> R.drawable.menu_tim_soto_daging
    "menu_tim_tuna.png" -> R.drawable.menu_tim_tuna
    "menu_tim_udang_brokoli.jpg" -> R.drawable.menu_tim_udang_brokoli
    "menu_tumis_tahu_udang.png" -> R.drawable.menu_tumis_tahu_udang

    else -> if (forDetail) R.drawable.preview_image else R.drawable.bg_image_preview
}

fun Int.toDp(context: Context): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
).toInt()