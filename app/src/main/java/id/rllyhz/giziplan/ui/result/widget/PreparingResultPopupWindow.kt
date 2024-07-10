package id.rllyhz.giziplan.ui.result.widget

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.PopupWindow
import androidx.cardview.widget.CardView
import id.rllyhz.giziplan.R

class PreparingResultPopupWindow(
    private val context: Context,
    private val rootView: ViewGroup,
) : PopupWindow(context) {

    init {
        contentView = LayoutInflater.from(context)
            .inflate(R.layout.view_popup_preparing_result, null) as CardView
    }

    fun show() {
        width = LayoutParams.MATCH_PARENT
        height = LayoutParams.WRAP_CONTENT

        showAtLocation(rootView, Gravity.CENTER, 0, 0)
    }
}