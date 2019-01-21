package com.labo.kaji.aacexample.widget

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ViewHolderDelegate

class LoopRecyclerViewAdapter<VH : RecyclerView.ViewHolder>(
    private val originalAdapter: RecyclerView.Adapter<VH>
) : RecyclerView.Adapter<VH>() {

    private val itemChangeDelegates = mutableMapOf<Int, RecyclerView.AdapterDataObserver>()

    init {
        setHasStableIds(originalAdapter.hasStableIds())
    }

    fun getActualItemCount(): Int {
        return originalAdapter.itemCount
    }

    fun getActualItemViewType(position: Int): Int {
        return originalAdapter.getItemViewType(position)
    }

    fun getActualItemId(position: Int): Long {
        return originalAdapter.getItemId(position)
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
            originalAdapter.itemCount
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return originalAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(observer)
        originalAdapter.registerAdapterDataObserver(observer)
    }

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.unregisterAdapterDataObserver(observer)
        originalAdapter.unregisterAdapterDataObserver(observer)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        originalAdapter.onBindViewHolder(holder, getActualPosition(position))
        ViewHolderDelegate.setPosition(holder, position)
        val itemChangeObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                if (getActualPosition(position) in positionStart..positionStart+itemCount) {
                    notifyItemChanged(position)
                }
            }
        }
        originalAdapter.registerAdapterDataObserver(itemChangeObserver)
        itemChangeDelegates[position] = itemChangeObserver
    }

    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        itemChangeDelegates.remove(holder.layoutPosition)
            ?.let(originalAdapter::unregisterAdapterDataObserver)
        originalAdapter.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: VH): Boolean {
        return originalAdapter.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        originalAdapter.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        originalAdapter.onViewDetachedFromWindow(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        originalAdapter.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        originalAdapter.onDetachedFromRecyclerView(recyclerView)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getActualItemCount() > 0) {
            originalAdapter.getItemViewType(getActualPosition(position))
        } else {
            0
        }
    }

    override fun getItemId(position: Int): Long {
        return originalAdapter.getItemId(getActualPosition(position))
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
        originalAdapter.setHasStableIds(hasStableIds)
    }
}
