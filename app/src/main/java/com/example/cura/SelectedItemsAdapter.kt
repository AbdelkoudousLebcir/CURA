package com.example.cura

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SelectedItemsAdapter(private val selectedItems: List<check_item>, private val onRemoveClick: (check_item) -> Unit) :
    RecyclerView.Adapter<SelectedItemsAdapter.SelectedViewHolder>() {

    inner class SelectedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.selected_item_text)
        val removeButton: ImageView = itemView.findViewById(R.id.remove_button)

        fun bind(item: check_item) {
            textView.text = item.text
            removeButton.setOnClickListener {
                onRemoveClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.selected_item, parent, false)
        return SelectedViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectedViewHolder, position: Int) {
        holder.bind(selectedItems[position])
    }

    override fun getItemCount() = selectedItems.size
}
