package com.brx.mobileapp.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {

        val halfSpace = space / 2
        if (parent.paddingLeft != halfSpace) {
            parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace)
            parent.clipToPadding = false
        }

        outRect.top = halfSpace
        outRect.bottom = halfSpace
        outRect.left = halfSpace
        outRect.right = halfSpace
    }
}