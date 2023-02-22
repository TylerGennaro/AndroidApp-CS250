package edu.monmouth.cs250.student.finalproject.ui.chats

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.monmouth.cs250.student.finalproject.R
import edu.monmouth.cs250.student.finalproject.Room
import edu.monmouth.cs250.student.finalproject.databinding.FragmentChatsBinding
import edu.monmouth.cs250.student.finalproject.ChatsActivity

private lateinit var ctx: Context

class ChatsFragment : Fragment(), OnItemClickListener {

    private lateinit var customAdapter: ChatRoomsAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ChatRoomViewHolder>? = null
    private lateinit var binding: FragmentChatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ctx = this.requireContext()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_chatrooms)
        recyclerView.layoutManager = LinearLayoutManager(ctx)
        recyclerView.adapter = ChatRoomsAdapter(ctx, mutableListOf<Room>(), this)
    }

    override fun onViewItemClicked(room: Room) {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            .replace(R.id.nav_host_fragment_content_chats, ChatRoomFragment.newInstance(room), "ChatRoom")
            .addToBackStack(null)
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatsFragment()
    }
}