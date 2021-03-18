package com.sagar.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class SpacesItemDecoration(
    private val space: Int,
    private val bottomMargin: Int? = null,
    private val totalChildCount: Int = 0,
    private val span: Int
) :
    ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.left = space / 2
        outRect.right = space / 2
        outRect.bottom =
            if (
                totalChildCount != 0 &&
                parent.getChildLayoutPosition(view) >= totalChildCount - span
            )
                bottomMargin ?: space
            else
                space

        if (
            parent.getChildLayoutPosition(view) == 0 ||
            parent.getChildLayoutPosition(view) == 1
        )
            outRect.top = space
        else
            outRect.top = 0
    }
}