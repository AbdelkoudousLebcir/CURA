package com.example.cura

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val messageTextSent: TextView = itemView.findViewById(R.id.myMessage)
    val messageTextReceived: TextView = itemView.findViewById(R.id.botMessage)
}
