package com.labo.kaji.aacexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.labo.kaji.aacexample.adapter.ExamplePagerAdapter
import com.labo.kaji.aacexample.widget.PagerTabSynchronizer
import com.labo.kaji.aacexample.widget.SidePreloadLayoutManager
import kotlinx.android.synthetic.main.pager_snap_helper.view.*


class PagerSnapHelperFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pager_snap_helper, container, false).also {
            val layoutManager = SidePreloadLayoutManager(it.context)
            val snapHelper = PagerSnapHelper()

            fun getCurrentPosition(): Int {
                return snapHelper
                    .findSnapView(layoutManager)
                    ?.let { v -> layoutManager.getPosition(v) }
                    ?: RecyclerView.NO_POSITION
            }

            it.pager.apply {
                setHasFixedSize(true)
                this.layoutManager = layoutManager
                adapter = ExamplePagerAdapter()

                snapHelper.attachToRecyclerView(this)
                addOnScrollListener(object : PagerScrollHandler(pager) {
                    override fun getCurrentPagePosition(): Int {
                        return getCurrentPosition()
                    }
                })
            }

            PagerTabSynchronizer.setup(it.tabs, it.pager, ::getCurrentPosition)
        }
    }
}
