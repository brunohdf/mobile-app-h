package com.brx.mobileapp.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.brx.mobileapp.R
import kotlinx.android.synthetic.main.error_view.view.*


class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {

    var title: String = ""
        set(value) {
            field = value
            error_title.text = value
        }

    var message: String = ""
        set(value) {
            field = value
            error_message.text = value
        }

    var image: Int = R.drawable.ic_error
        set(value) {
            field = value
            error_image.setImageDrawable(ContextCompat.getDrawable(context, value))
        }

    init {
        initView()
    }

    private fun initView() = View.inflate(context, R.layout.error_view, this)
}