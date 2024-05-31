package com.example.cura

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class HeightAdapter(private val heights: List<Int>) :
    RecyclerView.Adapter<HeightAdapter.HeightViewHolder>() {

    inner class HeightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHeight: TextView = itemView.findViewById(R.id.tvHeight)
        val ivIndicator: ImageView = itemView.findViewById(R.id.ivIndicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeightViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_height, parent, false)
        return HeightViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeightViewHolder, position: Int) {
        val height = heights[position]
        holder.tvHeight.text = height.toString()

        val layoutParams = holder.ivIndicator.layoutParams as ViewGroup.MarginLayoutParams
        when {
            height % 10 == 0 -> {
                holder.ivIndicator.setImageResource(R.drawable.large_indicator)
                layoutParams.bottomMargin = 20 // Margin for multiples of 10
                holder.tvHeight.visibility = View.VISIBLE
            }

            height % 5 == 0 -> {
                holder.ivIndicator.setImageResource(R.drawable.large_indicator)
                layoutParams.bottomMargin = 85 // Margin for multiples of 5
                holder.tvHeight.visibility = View.GONE
            }

            else -> {
                holder.ivIndicator.setImageResource(R.drawable.small_indicator)
                layoutParams.bottomMargin = 80 // Default margin

                holder.tvHeight.visibility = View.GONE
            }
        }
        holder.ivIndicator.layoutParams = layoutParams
    }

    override fun getItemCount(): Int {
        return heights.size
    }
}
