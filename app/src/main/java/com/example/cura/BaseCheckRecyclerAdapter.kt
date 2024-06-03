package com.example.cura

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
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

abstract class BaseCheckRecyclerAdapter(
    protected val itemList: List<check_item>
) : RecyclerView.Adapter<BaseCheckRecyclerAdapter.ViewHolder>() {

    abstract fun onItemCheckChanged()

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
                onItemCheckChanged()
            }

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemList[position].isChecked = isChecked
                    onItemCheckChanged()
                }
                updateUI(isChecked)
            }
        }

        fun bind(item: check_item) {
            textView.text = item.text
            checkBox.isChecked = item.isChecked
            imageView.visibility = if (item.imageResource != 0) View.VISIBLE else View.GONE
            if (item.imageResource != 0) {
                imageView.setImageResource(item.imageResource)
                imageView.colorFilter = if (item.isChecked) {
                    // Create a ColorFilter from the color resource
                    val color = ContextCompat.getColor(itemView.context, R.color.selected_image)
                    PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
                } else {
                    null
                }
            }
            updateUI(item.isChecked)
        }


        private fun updateUI(isChecked: Boolean) {
            textView.setTextColor(if (isChecked) Color.WHITE else Color.parseColor("#242E49"))
            container.setBackgroundResource(if (isChecked) R.drawable.item_checked else 0)
            cardView.cardElevation = if (isChecked) 0f else 4f
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
