package com.labo.kaji.aacexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.loop_recycler_view_pager.view.*

class LoopRecyclerViewPagerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.loop_recycler_view_pager, container, false).also {
            val pager = it.pager
            pager.layoutManager = SidePreloadLayoutManager(it.context)
            pager.adapter = ExamplePagerAdapter()
            pager.addOnScrollListener(object : PagerScrollHandler(pager, PlayerProvider.buildPlayer(it.context)) {
                override fun getCurrentPagePosition(): Int {
                    return pager.currentPosition
                }
            })
        }
    }

}
