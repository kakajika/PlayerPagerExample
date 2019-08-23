package com.labo.kaji.aacexample.widget

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class LoopPagerRecyclerView : LoopRecyclerView {

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ): super(context, attrs, defStyleAttr)

    private val snapHelper = PagerSnapHelper().also {
        it.attachToRecyclerView(this)
    }

    private val gestureDetector by lazy {
        GestureDetector(context, XScrollDetector())
    }

    override fun getCurrentPosition(): Int {
        val snapView = snapHelper.findSnapView(layoutManager)
        return if (snapView != null) {
            layoutManager!!.getPosition(snapView)
        } else {
            RecyclerView.NO_POSITION
        }
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(e) and gestureDetector.onTouchEvent(e)
    }

    class XScrollDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            return abs(distanceY) < abs(distanceX)
        }
    }
}
