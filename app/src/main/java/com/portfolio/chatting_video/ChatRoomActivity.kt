package com.portfolio.chatting_video

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.portfolio.chatting_video.Model.ChatModel
import com.portfolio.chatting_video.adapter.ChatReceive
import com.portfolio.chatting_video.adapter.ChatSent
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val TAG = ChatRoomActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        auth = FirebaseAuth.getInstance()

        val myUID = auth.uid
        val youName = intent.getStringExtra("youName")
        val youUID = intent.getStringExtra("youUID")

        val adapter = GroupAdapter<GroupieViewHolder>()

        adapter.add(ChatReceive())
        adapter.add(ChatSent())
        adapter.add(ChatReceive())
        adapter.add(ChatSent())
        adapter.add(ChatReceive())
        adapter.add(ChatSent())
        adapter.add(ChatReceive())

        recyclerView_chat.adapter = adapter

        val db = FirebaseFirestore.getInstance()

        btn_send.setOnClickListener {
            val message = message_chat.text.toString()
            message_chat.setText("")

            val chat = ChatModel(myUID.toString(), youUID.toString(), message)

            db.collection("message")
                .add(chat)
                .addOnSuccessListener {
                    Log.d(TAG, "성공")
                }
                .addOnFailureListener {
                    Log.d(TAG, "실패")
                }
        }
    }
}