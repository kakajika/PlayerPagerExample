package com.labo.kaji.aacexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.pager_snap_helper.view.*


class PagerSnapHelperFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pager_snap_helper, container, false).also {
            val pager = it.pager
            pager.setHasFixedSize(true)
            val layoutManager = SidePreloadLayoutManager(it.context)
            pager.layoutManager = layoutManager
            pager.adapter = ExamplePagerAdapter()

            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(pager)
            pager.addOnScrollListener(object : PagerScrollHandler(pager, PlayerProvider.buildPlayer(it.context)) {
                override fun getCurrentPagePosition(): Int {
                    return snapHelper
                        .findSnapView(layoutManager)
                        ?.let { v -> layoutManager.getPosition(v) }
                        ?: RecyclerView.NO_POSITION
                }
            })
        }
    }
}
