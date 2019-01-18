package com.labo.kaji.aacexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.labo.kaji.aacexample.widget.SidePreloadLayoutManager
import kotlinx.android.synthetic.main.loop_recycler_view_pager.view.*

class LoopRecyclerViewPagerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.loop_recycler_view_pager, container, false).also {
            val pager = it.pager
            pager.layoutManager = SidePreloadLayoutManager(it.context)
            pager.adapter = ExamplePagerAdapter()

            val useMultiPlayer = arguments?.getBoolean(ARG_MULTI_PLAYER, false) ?: false
            if (useMultiPlayer) {
                pager.addOnScrollListener(object : MultiPlayerPagerScrollHandler(pager) {
                    override fun getCurrentPagePosition(): Int {
                        return pager.currentPosition
                    }
                })
            } else {
                pager.addOnScrollListener(object : PagerScrollHandler(pager) {
                    override fun getCurrentPagePosition(): Int {
                        return pager.currentPosition
                    }
                })
            }
        }
    }

    companion object {
        private const val ARG_MULTI_PLAYER = "multi_player"

        fun withMultiPlayer(): LoopRecyclerViewPagerFragment {
            return LoopRecyclerViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_MULTI_PLAYER, true)
                }
            }
        }
    }

}
