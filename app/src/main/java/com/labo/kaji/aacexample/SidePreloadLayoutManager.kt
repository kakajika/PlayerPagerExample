package com.labo.kaji.aacexample

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 両脇のページを先読みするLinearLayoutManager
 */
class SidePreloadLayoutManager(context: Context) : LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {
    private val space = context.resources.displayMetrics.widthPixels / 2

    override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        return space
    }
}
