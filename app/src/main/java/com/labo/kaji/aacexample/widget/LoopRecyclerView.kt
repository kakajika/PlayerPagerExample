package com.labo.kaji.aacexample.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

open class LoopRecyclerView : RecyclerView {

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ): super(context, attrs, defStyleAttr)

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(ensureAdapter(adapter))
        super.scrollToPosition(getMiddlePosition())
    }

    override fun swapAdapter(adapter: Adapter<*>?, removeAndRecycleExistingViews: Boolean) {
        super.swapAdapter(ensureAdapter(adapter), removeAndRecycleExistingViews)
        super.scrollToPosition(getMiddlePosition())
    }

    private fun ensureAdapter(adapter: RecyclerView.Adapter<*>?): RecyclerView.Adapter<*>? {
        return when {
            adapter is LoopRecyclerViewAdapter -> adapter
            adapter != null -> LoopRecyclerViewAdapter(adapter)
            else -> null
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
        return (adapter as LoopRecyclerViewAdapter).getActualItemCount()
    }

    private fun transformInnerPositionIfNeed(position: Int): Int {
        val actualItemCount = getActualItemCountFromAdapter()
        if (actualItemCount == 0) {
            return 0
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
            } else {
                bakPosition2
            }
        } else {
            if (Math.abs(bakPosition1 - currentPosition) > Math.abs(bakPosition3 - currentPosition)) {
                bakPosition3
            } else {
                bakPosition1
            }
        }
    }

    open fun getCurrentPosition(): Int {
        return if (layoutManager!!.canScrollHorizontally()) {
            ViewUtils.getCenterXChildPosition(this)
        } else {
            ViewUtils.getCenterYChildPosition(this)
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

private object ViewUtils {
    fun getCenterXChildPosition(recyclerView: RecyclerView): Int {
        for (child in recyclerView.children) {
            if (isChildInCenterX(recyclerView, child)) {
                return recyclerView.getChildAdapterPosition(child)
            }
        }
        return recyclerView.childCount
    }

    fun getCenterYChildPosition(recyclerView: RecyclerView): Int {
        for (child in recyclerView.children) {
            if (isChildInCenterY(recyclerView, child)) {
                return recyclerView.getChildAdapterPosition(child)
            }
        }
        return recyclerView.childCount
    }

    fun isChildInCenterX(recyclerView: RecyclerView, view: View): Boolean {
        val childCount = recyclerView.childCount
        val lvLocationOnScreen = IntArray(2)
        val vLocationOnScreen = IntArray(2)
        recyclerView.getLocationOnScreen(lvLocationOnScreen)
        val middleX = lvLocationOnScreen[0] + recyclerView.width / 2
        if (childCount > 0) {
            view.getLocationOnScreen(vLocationOnScreen)
            if (vLocationOnScreen[0] <= middleX && vLocationOnScreen[0] + view.width >= middleX) {
                return true
            }
        }
        return false
    }

    fun isChildInCenterY(recyclerView: RecyclerView, view: View): Boolean {
        val childCount = recyclerView.childCount
        val lvLocationOnScreen = IntArray(2)
        val vLocationOnScreen = IntArray(2)
        recyclerView.getLocationOnScreen(lvLocationOnScreen)
        val middleY = lvLocationOnScreen[1] + recyclerView.height / 2
        if (childCount > 0) {
            view.getLocationOnScreen(vLocationOnScreen)
            if (vLocationOnScreen[1] <= middleY && vLocationOnScreen[1] + view.height >= middleY) {
                return true
            }
        }
        return false
    }
}
