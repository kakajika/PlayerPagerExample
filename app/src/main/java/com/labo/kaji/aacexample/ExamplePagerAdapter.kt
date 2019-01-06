package com.labo.kaji.aacexample

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.android.synthetic.main.example_page.view.*
import kotlin.random.Random

class ExamplePagerAdapter : RecyclerView.Adapter<ExampleViewHolder>() {
    private val colors = (1..100).map {
        Color.rgb(
            Random.nextInt(128) + 128,
            Random.nextInt(128) + 128,
            Random.nextInt(128) + 128
        )
    }

    override fun getItemCount(): Int {
        return colors.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ExampleViewHolder(inflater.inflate(R.layout.example_page, parent, false))
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        holder.indexTextView.text = "${position + 1}"
        holder.listView.setBackgroundColor(colors[position])
        holder.listView.adapter = PageListAdapter((1..100).map { "Item $it" })
    }

    override fun onViewRecycled(holder: ExampleViewHolder) {
        holder.playerView.player = null
    }
}

class ExampleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val indexTextView: TextView = view.page_text_index
    val playerView: PlayerView = view.page_player
    val listView: RecyclerView = view.page_list.also {
        it.layoutManager = LinearLayoutManager(it.context, RecyclerView.VERTICAL, false)
    }
}