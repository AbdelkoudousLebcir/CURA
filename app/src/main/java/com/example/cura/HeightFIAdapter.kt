package com.example.cura


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HeightFIAdapter(
    private val heights: List<Int>,
    private val isMetric: Boolean
) : RecyclerView.Adapter<HeightFIAdapter.HeightFIViewHolder>() {

    inner class HeightFIViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHeight: TextView = itemView.findViewById(R.id.tvHeight)
        val ivIndicator: ImageView = itemView.findViewById(R.id.ivIndicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeightFIViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_height, parent, false)
        return HeightFIViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeightFIViewHolder, position: Int) {
        val height = heights[position]
        holder.tvHeight.text = if (isMetric) {
            "$height"
        } else {
            val feet = height / 12
            val inches = height % 12
            "$feet' $inches\""
        }

        val layoutParams = holder.ivIndicator.layoutParams as ViewGroup.MarginLayoutParams
        when {
            (isMetric && height % 10 == 0) || (!isMetric && height % 12 == 0) -> {
                holder.ivIndicator.setImageResource(R.drawable.large_indicator)
                layoutParams.bottomMargin = 20 // Margin for multiples of 10 cm or 12 inches (1 foot)
                holder.tvHeight.visibility = View.VISIBLE
            }
            (isMetric && height % 5 == 0) || (!isMetric && height % 6 == 0) -> {
                holder.ivIndicator.setImageResource(R.drawable.large_indicator)
                layoutParams.bottomMargin = 85 // Margin for multiples of 5 cm or 6 inches (half foot)
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
