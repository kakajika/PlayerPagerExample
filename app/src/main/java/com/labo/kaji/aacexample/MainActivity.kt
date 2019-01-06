package com.labo.kaji.aacexample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

enum class ContentType {
    PAGER_SNAP_HELPER,
    RECYCLER_VIEW_PAGER,
    LOOP_RECYCLER_VIEW_PAGER
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setContentView(R.layout.activity_main)
        openContent(ContentType.PAGER_SNAP_HELPER)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_option_snaphelper -> openContent(ContentType.PAGER_SNAP_HELPER)
            R.id.menu_option_recyclerviewpager -> openContent(ContentType.RECYCLER_VIEW_PAGER)
            R.id.menu_option_looprecyclerviewpager -> openContent(ContentType.LOOP_RECYCLER_VIEW_PAGER)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openContent(type: ContentType) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main, when (type) {
                ContentType.PAGER_SNAP_HELPER -> PagerSnapHelperFragment()
                ContentType.RECYCLER_VIEW_PAGER -> RecyclerViewPagerFragment()
                ContentType.LOOP_RECYCLER_VIEW_PAGER -> LoopRecyclerViewPagerFragment()
            })
            .commit()
    }

}
