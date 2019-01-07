package com.labo.kaji.aacexample

import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.labo.kaji.aacexample.ext.muted

abstract class PagerScrollHandler(
    private val pager: RecyclerView
) : RecyclerView.OnScrollListener() {

    init {
        pager.post { playVideoAtPage(getCurrentPagePosition()) }
    }

    abstract fun getCurrentPagePosition(): Int

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        when (newState) {
            RecyclerView.SCROLL_STATE_IDLE -> {
                animatePageScale(1.0f)
                playVideoAtPage(getCurrentPagePosition())
            }
            RecyclerView.SCROLL_STATE_DRAGGING -> {
                animatePageScale(0.9f)
            }
            RecyclerView.SCROLL_STATE_SETTLING -> {
            }
        }
    }

    private fun animatePageScale(scale: Float) {
        for (page in pager.children) {
            if (page.scaleX != scale) {
                page.animate()
                    .scaleX(scale)
                    .scaleY(scale)
            }
        }
    }

    protected open fun playVideoAtPage(position: Int) {
        PlayerProvider.mutePlayer()

        pager.children
            .mapNotNull { pager.getChildViewHolder(it) }
            .filterIsInstance<ExampleViewHolder>()
            .onEach { it.playerView.player = null }
            .forEach {
                if (it.adapterPosition == position) {
                    it.playerView.player = PlayerProvider
                        .getPlayer(0)
                        .apply { muted = false }
                }
            }
    }

}
