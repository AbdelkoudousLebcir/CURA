package com.example.cura

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class AlphabetAdapter(private val alphabetList: List<Char>, private val onLetterClick: (Char) -> Unit) :
    RecyclerView.Adapter<AlphabetAdapter.ViewHolder>() {

    private var selectedPosition = 0

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val alphabetText: TextView = itemView.findViewById(R.id.alphabet_text)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onLetterClick(alphabetList[position])
                    setSelectedPosition(position)
                }
            }
        }

        fun bind(letter: Char, isSelected: Boolean) {
            alphabetText.text = letter.toString()
            if (isSelected) {
                alphabetText.setBackgroundResource(R.drawable.btn_grey3)
                alphabetText.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
            } else {
                alphabetText.setBackgroundResource(0)
                alphabetText.setTextColor(Color.parseColor("#5D6A85"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alphabet_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(alphabetList[position], position == selectedPosition)
    }

    override fun getItemCount() = alphabetList.size

    fun setSelectedPosition(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousPosition)
        notifyItemChanged(position)
    }

    fun getSelectedPosition(): Int {
        return selectedPosition
    }
}
