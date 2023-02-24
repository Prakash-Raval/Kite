package com.example.kite.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import kotlinx.coroutines.NonDisposableHandle.parent


class RecyclerViewItemDecoration(private val space: Int, private val lastRight: Int) :
    ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = space
        outRect.right = space
        outRect.bottom = space
        outRect.top = space
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.left = lastRight
        } else {
            outRect.left = space
        }
        if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) {
            outRect.right = lastRight
        } else {
            outRect.right = space
        }
    }



}