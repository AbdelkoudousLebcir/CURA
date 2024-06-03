package com.example.cura

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AgeAdapter(private val ages: List<Int>) : RecyclerView.Adapter<AgeAdapter.AgeViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION

    inner class AgeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ageTextView: TextView = itemView.findViewById(R.id.ageTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_age, parent, false)
        return AgeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AgeViewHolder, position: Int) {
        holder.ageTextView.text = ages[position].toString()
        holder.ageTextView.textSize = if (position == selectedPosition) 80f else if (position == selectedPosition+1 || position == selectedPosition-1) 74f else 68f
        holder.ageTextView.setTextColor(
            if (position == selectedPosition) Color.WHITE
            else if (position == selectedPosition+1 || position == selectedPosition-1)
                Color.parseColor("#5D6A85") else   Color.parseColor("#BEC5D2")
        )

    }
    fun getSelectedAge(): Int? {
        return if (selectedPosition != RecyclerView.NO_POSITION) ages[selectedPosition] else null
    }

    override fun getItemCount(): Int = ages.size
}
