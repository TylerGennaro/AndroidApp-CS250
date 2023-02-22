package edu.monmouth.cs250.student.finalproject.ui.chats

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.monmouth.cs250.student.finalproject.Message
import edu.monmouth.cs250.student.finalproject.R

class ChatHistoryAdapter (private val context: Context, var messages: MutableList<Message?>) : RecyclerView.Adapter<ChatHistoryViewHolder>() {

    init {
        messages.add(Message("John", "", "Hey there"))
    }

    override fun getItemCount(): Int {
        return messages.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHistoryViewHolder {

        val chatItemLayout = LayoutInflater.from(parent.context)
        val chatItemView = chatItemLayout.inflate(R.layout.chat_view, parent, false)
        return ChatHistoryViewHolder(chatItemView)
    }

    // get the data for viewHolder for CustomViewHolder

    override fun onBindViewHolder(holder: ChatHistoryViewHolder, position: Int) {
        val msg = messages[position]
        holder.bind(msg!!, context)
    }
}

class ChatHistoryViewHolder (itemView: View): RecyclerView.ViewHolder (itemView) {

    var poster: TextView = itemView.findViewById(R.id.text_username)
    var message: TextView = itemView.findViewById(R.id.text_message)

    fun bind (msg: Message, context: Context) {
        Log.i("Message History", "Added message")
        poster.text = msg.poster
        message.text = msg.message
    }
}