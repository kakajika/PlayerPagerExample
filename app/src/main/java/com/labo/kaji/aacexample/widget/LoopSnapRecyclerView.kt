package com.labo.kaji.aacexample.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class LoopSnapRecyclerView : LoopRecyclerView {

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ): super(context, attrs, defStyleAttr)

    private val snapHelper = LinearSnapHelper().also {
        it.attachToRecyclerView(this)
    }

    override fun getCurrentPosition(): Int {
        val snapView = snapHelper.findSnapView(layoutManager)
        return if (snapView != null) {
            layoutManager!!.getPosition(snapView)
        } else {
            RecyclerView.NO_POSITION
        }
    }
}
