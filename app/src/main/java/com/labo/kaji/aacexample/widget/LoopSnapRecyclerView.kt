package com.labo.kaji.aacexample.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class LoopSnapRecyclerView : RecyclerView {

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ): super(context, attrs, defStyleAttr)

    private val snapHelper = PagerSnapHelper().also {
        it.attachToRecyclerView(this)
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(ensureAdapter(adapter))
        super.scrollToPosition(getMiddlePosition())
    }

    override fun swapAdapter(adapter: Adapter<*>?, removeAndRecycleExistingViews: Boolean) {
        super.swapAdapter(ensureAdapter(adapter), removeAndRecycleExistingViews)
        super.scrollToPosition(getMiddlePosition())
    }

    private fun ensureAdapter(
        adapter: RecyclerView.Adapter<*>?
    ): RecyclerView.Adapter<*>? {
        return if (adapter is LoopSnapRecyclerViewAdapter) {
            adapter
        } else if (adapter != null) {
            LoopSnapRecyclerViewAdapter(adapter)
        } else {
            null
        }
    }

    override fun smoothScrollToPosition(position: Int) {
        super.smoothScrollToPosition(transformInnerPositionIfNeed(position))
    }

    override fun scrollToPosition(position: Int) {
        super.scrollToPosition(transformInnerPositionIfNeed(position))
    }

    fun getActualCurrentPosition(): Int {
        val position = getCurrentPosition()
        return transformToActualPosition(position)
    }

    fun transformToActualPosition(position: Int): Int {
        return if (adapter == null || adapter!!.itemCount < 0) {
            0
        } else {
            position % getActualItemCountFromAdapter()
        }
    }

    private fun getActualItemCountFromAdapter(): Int {
        return (adapter as LoopSnapRecyclerViewAdapter).getActualItemCount()
    }

    private fun transformInnerPositionIfNeed(position: Int): Int {
        val actualItemCount = getActualItemCountFromAdapter()
        if (actualItemCount == 0) {
            return actualItemCount
        }
        val currentPosition = getCurrentPosition()
        val actualCurrentPosition = currentPosition % actualItemCount
        val bakPosition1 = currentPosition - actualCurrentPosition + position % actualItemCount
        val bakPosition2 = (currentPosition
                - actualCurrentPosition
                - actualItemCount) + position % actualItemCount
        val bakPosition3 = (currentPosition - actualCurrentPosition
                + actualItemCount
                + position % actualItemCount)
        // get position which is closer to current position
        return if (Math.abs(bakPosition1 - currentPosition) > Math.abs(bakPosition2 - currentPosition)) {
            if (Math.abs(bakPosition2 - currentPosition) > Math.abs(bakPosition3 - currentPosition)) {
                bakPosition3
            } else bakPosition2
        } else {
            if (Math.abs(bakPosition1 - currentPosition) > Math.abs(bakPosition3 - currentPosition)) {
                bakPosition3
            } else bakPosition1
        }
    }

    fun getCurrentPosition(): Int {
        val snapView = snapHelper.findSnapView(layoutManager)
        return if (snapView != null) {
            layoutManager!!.getPosition(snapView)
        } else {
            RecyclerView.NO_POSITION
        }
    }

    private fun getMiddlePosition(): Int {
        var middlePosition = Integer.MAX_VALUE / 2
        val actualItemCount = getActualItemCountFromAdapter()
        if (actualItemCount > 0 && middlePosition % actualItemCount != 0) {
            middlePosition -= middlePosition % actualItemCount
        }
        return middlePosition
    }
}
