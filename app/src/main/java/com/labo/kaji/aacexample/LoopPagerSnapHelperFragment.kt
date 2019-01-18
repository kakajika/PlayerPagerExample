package com.labo.kaji.aacexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.labo.kaji.aacexample.adapter.ExamplePagerAdapter
import com.labo.kaji.aacexample.widget.SidePreloadLayoutManager
import kotlinx.android.synthetic.main.loop_pager_snap_helper.view.*

class LoopPagerSnapHelperFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.loop_pager_snap_helper, container, false).also {
            val pager = it.pager
            pager.setHasFixedSize(true)
            val layoutManager = SidePreloadLayoutManager(it.context)
            pager.layoutManager = layoutManager
            pager.adapter = ExamplePagerAdapter()

            pager.addOnScrollListener(object : PagerScrollHandler(pager) {
                override fun getCurrentPagePosition(): Int {
                    return pager.getCurrentPosition()
                }
            })
        }
    }
}
