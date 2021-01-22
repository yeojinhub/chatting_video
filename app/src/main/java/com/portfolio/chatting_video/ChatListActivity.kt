package com.portfolio.chatting_video

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.portfolio.chatting_video.adapter.UserItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chat_list.*

class ChatListActivity : AppCompatActivity() {

    // Access a Cloud Firestore instance from your Activity
    val db = FirebaseFirestore.getInstance()

    private val TAG = ChatListActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        val adapter = GroupAdapter<GroupieViewHolder>()

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    adapter.add(UserItem(document.get("name").toString(), document.get("uid").toString()))
                    //Log.d(TAG, documen t.get("username").toString())
                    //Log.d(TAG, "${document.id} => ${document.data}")
                }
                recyclerView_list.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        adapter.setOnItemClickListener { item, view ->
            val name : String = (item as UserItem).name
            val uid : String = (item.uid)

            val intent = Intent(this, ChatRoomActivity::class.java)
            intent.putExtra("youName", name)
            intent.putExtra("youUID", uid)
            startActivity(intent)
        }
    }
}