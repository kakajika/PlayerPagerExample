package com.labo.kaji.aacexample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

enum class ContentType {
    PAGER_SNAP_HELPER,
    LOOP_PAGER_SNAP_HELPER,
    RECYCLER_VIEW_PAGER,
    LOOP_RECYCLER_VIEW_PAGER,
    LOOP_RECYCLER_VIEW_PAGER_MULTI
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        PlayerProvider.createPlayers(this)
        setContentView(R.layout.activity_main)
        openContent(ContentType.PAGER_SNAP_HELPER)
    }

    override fun onStart() {
        super.onStart()
        PlayerProvider.resumePlayer()
    }

    override fun onStop() {
        super.onStop()
        PlayerProvider.stopPlayer()
    }

    override fun onDestroy() {
        PlayerProvider.releasePlayer()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_option_snaphelper -> openContent(ContentType.PAGER_SNAP_HELPER)
            R.id.menu_option_loop_snaphelper -> openContent(ContentType.LOOP_PAGER_SNAP_HELPER)
            R.id.menu_option_recyclerviewpager -> openContent(ContentType.RECYCLER_VIEW_PAGER)
            R.id.menu_option_looprecyclerviewpager -> openContent(ContentType.LOOP_RECYCLER_VIEW_PAGER)
            R.id.menu_option_looprecyclerviewpager_multi -> openContent(ContentType.LOOP_RECYCLER_VIEW_PAGER_MULTI)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openContent(type: ContentType) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main, when (type) {
                ContentType.PAGER_SNAP_HELPER -> PagerSnapHelperFragment()
                ContentType.LOOP_PAGER_SNAP_HELPER -> LoopPagerSnapHelperFragment()
                ContentType.RECYCLER_VIEW_PAGER -> RecyclerViewPagerFragment()
                ContentType.LOOP_RECYCLER_VIEW_PAGER -> LoopRecyclerViewPagerFragment()
                ContentType.LOOP_RECYCLER_VIEW_PAGER_MULTI -> LoopRecyclerViewPagerFragment.withMultiPlayer()
            })
            .commit()
    }

}
