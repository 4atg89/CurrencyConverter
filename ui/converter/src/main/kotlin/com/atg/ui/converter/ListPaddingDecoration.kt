package com.atg.ui.converter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView

/**
 * Simple decorator class that adds paddings between cards
 * should be used instead of paddings in xml
 */
class ListPaddingDecoration private constructor(
    private val space: Int,
    private val orientation: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.isFirstItem(view)) {
            outRect.setEmpty()
            return
        }
        when (orientation) {
            VERTICAL -> outRect.vertical(space)
            HORIZONTAL -> outRect.horizontal(space)
        }
    }

    private fun Rect.vertical(space: Int) {
        top = space
    }

    private fun Rect.horizontal(space: Int) {
        left = space
    }

    private fun RecyclerView.isLastItem(view: View): Boolean {
        return adapter?.itemCount?.dec() == getChildAdapterPosition(view)
    }

    private fun RecyclerView.isFirstItem(view: View): Boolean {
        return 0 == getChildAdapterPosition(view)
    }

    companion object {

        fun horizontal(dp: Int): ListPaddingDecoration =
            ListPaddingDecoration(dp, RecyclerView.HORIZONTAL)

        fun vertical(dp: Int): ListPaddingDecoration =
            ListPaddingDecoration(dp, RecyclerView.VERTICAL)
    }
}