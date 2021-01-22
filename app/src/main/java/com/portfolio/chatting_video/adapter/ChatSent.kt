package com.portfolio.chatting_video.adapter

import com.portfolio.chatting_video.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class ChatSent() : Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    }

    override fun getLayout(): Int {
        return R.layout.chatroom_sent
    }

}