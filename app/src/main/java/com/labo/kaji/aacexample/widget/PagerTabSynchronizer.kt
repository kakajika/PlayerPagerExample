package com.labo.kaji.aacexample.widget

import androidx.recyclerview.widget.RecyclerView
import com.labo.kaji.aacexample.adapter.ExampleTabAdapter

object PagerTabSynchronizer {
    fun setup(tabs: RecyclerView, pager: RecyclerView, getCurrentPage: () -> Int) {
        var tabPosition: Int = 0

        val tabAdapter = ExampleTabAdapter(object : ExampleTabAdapter.OnTabSelectedListener {
            override fun onTabSelected(index: Int) {
                if (tabPosition != index) {
                    tabPosition = index
                    pager.smoothScrollToPosition(index)
                    tabs.smoothScrollToPosition(index)
                }
            }
        })
        tabs.apply {
            layoutManager = CenterLayoutManager(tabs.context, RecyclerView.HORIZONTAL)
            adapter = tabAdapter
            post { smoothScrollToPosition(0) }
        }

        pager.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var dragged: Boolean = false

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> dragged = true
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        if (!dragged) {
                            return
                        }
                        dragged = false

                        val newPosition = getCurrentPage()
                        if (tabPosition != newPosition) {
                            tabPosition = newPosition
                            tabs.smoothScrollToPosition(newPosition)
                            tabAdapter.selectTabAt(newPosition)
                        }
                    }
                }
            }
        })
    }
}
