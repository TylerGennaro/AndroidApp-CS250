package edu.monmouth.cs250.student.finalproject.ui.chats

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.monmouth.cs250.student.finalproject.R
import edu.monmouth.cs250.student.finalproject.Room

class ChatRoomsAdapter (private val context: Context, var rooms: MutableList<Room>, val itemViewListener: OnItemClickListener) : RecyclerView.Adapter<ChatRoomViewHolder>() {

    private var roomList = mutableListOf<Room>()

    init {
        roomList = rooms
        roomList.add(Room("TGennaro", "", "Amazing first chat"))
        roomList.add(Room("TGennaro", "", "A private chat"))
    }

    override fun getItemCount(): Int {
        return roomList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {

        val roomItemLayout = LayoutInflater.from(parent.context)
        val roomItemView = roomItemLayout.inflate(R.layout.chatroom_view, parent, false)
        return ChatRoomViewHolder(roomItemView)
    }

    // get the data for viewHolder for CustomViewHolder

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        val room = roomList[position]
        holder.bind(room!!, context, itemViewListener)
    }
}

class ChatRoomViewHolder (itemView: View): RecyclerView.ViewHolder (itemView) {

    var title: TextView = itemView.findViewById(R.id.text_roomtitle)
    var creator: TextView = itemView.findViewById(R.id.text_roomcreator)

    fun bind (room: Room, context: Context, itemViewListener: OnItemClickListener) {
        title.text = room.title
        creator.text = room.creator

        itemView.setOnClickListener{
            itemViewListener.onViewItemClicked(room)
        }
    }
}

interface OnItemClickListener {
    fun onViewItemClicked(room: Room)
}