package com.labo.kaji.aacexample.widget

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ViewHolderDelegate

class LoopSnapRecyclerViewAdapter<VH : RecyclerView.ViewHolder>(
    private val rawAdapter: RecyclerView.Adapter<VH>
) : RecyclerView.Adapter<VH>() {

    init {
        setHasStableIds(rawAdapter.hasStableIds())
    }

    fun getActualItemCount(): Int {
        return rawAdapter.itemCount
    }

    fun getActualItemViewType(position: Int): Int {
        return rawAdapter.getItemViewType(position)
    }

    fun getActualItemId(position: Int): Long {
        return rawAdapter.getItemId(position)
    }

    fun getActualPosition(position: Int): Int {
        val actualItemCount = getActualItemCount()
        return if (actualItemCount in 1..position) {
            position % actualItemCount
        } else {
            position
        }
    }

    override fun getItemCount(): Int {
        return if (getActualItemCount() > 0) {
            Integer.MAX_VALUE
        } else {
            rawAdapter.itemCount
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return rawAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(observer)
        rawAdapter.registerAdapterDataObserver(observer)
    }

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.unregisterAdapterDataObserver(observer)
        rawAdapter.unregisterAdapterDataObserver(observer)
    }

    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        rawAdapter.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: VH): Boolean {
        return rawAdapter.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        rawAdapter.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        rawAdapter.onViewDetachedFromWindow(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rawAdapter.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        rawAdapter.onDetachedFromRecyclerView(recyclerView)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getActualItemCount() > 0) {
            rawAdapter.getItemViewType(getActualPosition(position))
        } else {
            0
        }
    }

    override fun getItemId(position: Int): Long {
        return rawAdapter.getItemId(getActualPosition(position))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        rawAdapter.onBindViewHolder(holder, getActualPosition(position))
        ViewHolderDelegate.setPosition(holder, position)
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
        rawAdapter.setHasStableIds(hasStableIds)
    }
}
