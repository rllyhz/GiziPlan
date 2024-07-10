package id.rllyhz.giziplan.ui.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.TextView

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

fun Int.toDp(context: Context): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()