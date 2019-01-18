package com.labo.kaji.aacexample

import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.labo.kaji.aacexample.adapter.ExampleViewHolder
import com.labo.kaji.aacexample.ext.muted

abstract class MultiPlayerPagerScrollHandler(
    private val pager: RecyclerView
) : PagerScrollHandler(pager) {

    override fun playVideoAtPage(position: Int) {
        PlayerProvider.mutePlayer()

        pager.children
            .mapNotNull { pager.getChildViewHolder(it) }
            .filterIsInstance<ExampleViewHolder>()
            .onEach { it.playerView.player = null }
            .forEach {
                when (it.adapterPosition) {
                    position -> {
                        it.playerView.player = PlayerProvider
                            .getPlayer(it.adapterPosition)
                            .apply { muted = false }
                    }
                    position - 1, position + 1 -> {
                        it.playerView.player = PlayerProvider
                            .getPlayer(it.adapterPosition)
                            .apply { muted = true }
                    }
                }
            }
    }
}
