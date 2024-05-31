package com.example.cura

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class checkRecyclerAdapter(private val itemList: List<check_item>) :
    RecyclerView.Adapter<checkRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.item_card)
        val container: LinearLayout = itemView.findViewById(R.id.item_container)
        val textView: TextView = itemView.findViewById(R.id.item_text)
        val checkBox: CheckBox = itemView.findViewById(R.id.item_checkbox)
        val imageView: ImageView = itemView.findViewById(R.id.item_image)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                val item = itemList[position]
                item.isChecked = !item.isChecked
                notifyItemChanged(position)
            }

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemList[position].isChecked = isChecked
                }
                if (isChecked) {
                    textView.setTextColor(Color.WHITE)
                    container.setBackgroundResource(R.drawable.item_checked)
                    imageView.setColorFilter(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.selected_image
                        ), android.graphics.PorterDuff.Mode.SRC_IN
                    )
                    cardView.cardElevation = 0f
                } else {
                    container.setBackgroundResource(0)
                    imageView.clearColorFilter()
                    textView.setTextColor(Color.parseColor("#242E49"))
                    cardView.cardElevation = 4f
                }
            }
        }

        fun bind(item: check_item) {
            textView.text = item.text
            checkBox.isChecked = item.isChecked
            if (item.imageResource != 0) {
                imageView.visibility = View.VISIBLE
                imageView.setImageResource(item.imageResource)
                if (item.isChecked) {
                    imageView.setColorFilter(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.selected_image
                        ), android.graphics.PorterDuff.Mode.SRC_IN
                    )
                } else {
                    imageView.clearColorFilter()
                }
            } else {
                imageView.visibility = View.GONE
            }
            if (item.isChecked) {
                textView.setTextColor(Color.WHITE)
                container.setBackgroundResource(R.drawable.item_checked)
                cardView.cardElevation = 0f
            } else {
                textView.setTextColor(Color.parseColor("#242E49"))
                container.setBackgroundResource(0)
                cardView.cardElevation = 4f
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.check_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount() = itemList.size

    fun getSelectedItems(): List<check_item> {
        return itemList.filter { it.isChecked }
    }

}
