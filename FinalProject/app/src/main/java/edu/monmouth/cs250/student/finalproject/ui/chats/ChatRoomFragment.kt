package edu.monmouth.cs250.student.finalproject.ui.chats

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import edu.monmouth.cs250.student.finalproject.Message
import edu.monmouth.cs250.student.finalproject.R
import edu.monmouth.cs250.student.finalproject.Room
import edu.monmouth.cs250.student.finalproject.User
import edu.monmouth.cs250.student.finalproject.databinding.FragmentChatRoomBinding

class ChatRoomFragment : Fragment() {

    private lateinit var ctx: Context
    private lateinit var _binding: FragmentChatRoomBinding
    private val binding get() = _binding
    private lateinit var room: Room
    private lateinit var messages: MutableList<Message?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ctx = this.requireContext()
        messages = mutableListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChatRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerviewChatHistory.layoutManager = LinearLayoutManager(ctx)
        binding.recyclerviewChatHistory.adapter = ChatHistoryAdapter(ctx, messages)

        binding.chatroomBack.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.nav_host_fragment_content_chats, ChatsFragment(), "Chats")
                .commit()
        }

        binding.chatroomTitle.text = room.title

        binding.buttonSend.setOnClickListener{
            addMessage()
        }
    }

    fun initRoom(room: Room) {
        this.room = room
    }

    private fun addMessage() {
        val text: String = binding.inputMessage.text.toString()
        val poster: String = User.username
        val posterUID: String = User.UID
        registerMessage(Message(poster, posterUID, text))
    }


    private fun registerMessage(msg: Message) {
        messages.add(msg)
        binding.recyclerviewChatHistory.adapter!!.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance(room: Room): Fragment {
            val fragment = ChatRoomFragment()
            fragment.initRoom(room)
            return fragment
        }
    }
}