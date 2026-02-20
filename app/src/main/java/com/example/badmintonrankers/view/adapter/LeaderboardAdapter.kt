package com.example.badmintonrankers.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.badmintonrankers.R
import com.example.badmintonrankers.data.model.Players

class LeaderboardAdapter () :
    ListAdapter<Players, LeaderboardAdapter.LeaderboardViewHolder>(LeaderboardCallBack){

    class LeaderboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var currItem : Players? = null

        fun bind(players: Players){
            currItem = players
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeaderboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_leaderboard,parent,false)
        return LeaderboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object LeaderboardCallBack : DiffUtil.ItemCallback<Players>(){
    override fun areItemsTheSame(oldItem: Players, newItem: Players): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Players, newItem: Players): Boolean {
        return oldItem == newItem
    }

}