package com.labo.kaji.aacexample.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.labo.kaji.aacexample.R
import kotlinx.android.synthetic.main.example_tab_item.view.*

class ExampleTabAdapter(
    private val tabSelectedListener: OnTabSelectedListener?
) : RecyclerView.Adapter<ExampleTabViewHolder>() {

    interface OnTabSelectedListener {
        fun onTabSelected(index: Int)
    }

    private var selectedIndex = 0

    fun selectTabAt(index: Int) {
        if (index < itemCount && index != selectedIndex) {
            selectedIndex = index
            notifyDataSetChanged()
            tabSelectedListener?.onTabSelected(index)
        }
    }

    override fun getItemCount(): Int {
        return ExamplePagerAdapter.colors.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleTabViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ExampleTabViewHolder.create(inflater, parent)
    }

    override fun onBindViewHolder(holder: ExampleTabViewHolder, position: Int) {
        holder.titleTextView.text = "Page ${position + 1}"
        holder.tint(ExamplePagerAdapter.colors[position])
        holder.itemView.isSelected = (position == selectedIndex)

        holder.itemView.setOnClickListener {
            selectTabAt(position)
        }
    }

    override fun onViewRecycled(holder: ExampleTabViewHolder) {
        super.onViewRecycled(holder)
        holder.itemView.setOnClickListener(null)
    }
}

class ExampleTabViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val titleTextView: TextView = view.tab_item_title

    fun tint(@ColorInt color: Int) {
        ViewCompat.setBackgroundTintList(titleTextView, ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_selected)),
            intArrayOf(color)
        ))
    }

    companion object {
        fun create(inflater: LayoutInflater, parent: ViewGroup): ExampleTabViewHolder {
            return ExampleTabViewHolder(inflater.inflate(R.layout.example_tab_item, parent, false))
        }
    }
}
