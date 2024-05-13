package com.example.cura

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val myMessage: TextView = itemView.findViewById(R.id.myMessage)
        val botMessage: TextView = itemView.findViewById(R.id.botMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatMessage = messages[position]
        if (chatMessage.isUser) {
            holder.myMessage.text = chatMessage.message
            holder.myMessage.visibility = View.VISIBLE
            holder.botMessage.visibility = View.GONE
        } else {
            holder.botMessage.text = chatMessage.message
            holder.botMessage.visibility = View.VISIBLE
            holder.myMessage.visibility = View.GONE
        }
    }

    override fun getItemCount() = messages.size
}
