package com.brx.mobileapp.util.extension

import android.view.View

fun View.visible(isVisible: Boolean = true) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE

}