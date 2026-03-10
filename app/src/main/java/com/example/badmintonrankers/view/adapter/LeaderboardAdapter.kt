package com.example.badmintonrankers.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.badmintonrankers.R
import com.example.badmintonrankers.data.model.Players

class LeaderboardAdapter () :
    ListAdapter<Players, LeaderboardAdapter.LeaderboardViewHolder>(LeaderboardCallBack){

    class LeaderboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var rankings : TextView = itemView.findViewById(R.id.rankings)
        val rankImage : ImageView = itemView.findViewById(R.id.memberRank)
        val memberName : TextView = itemView.findViewById(R.id.memberName)
        val memberMatches : TextView = itemView.findViewById(R.id.memberMatches)
        val memberWinRate : TextView = itemView.findViewById(R.id.memberWinRate)
        val memberMmr : TextView = itemView.findViewById(R.id.memberMmr)

        var currItem : Players? = null

        fun bind(players: Players, position: Int){
            currItem = players
            rankings.text = (position + 1) .toString()
            val imageId = itemView.context.resources.getIdentifier(
                players.rankImageLarge,
                "drawable",
                itemView.context.packageName
            )
            Glide.with(itemView.context).load(imageId).error(R.drawable.account).centerCrop().into(rankImage)
            memberName.text = players.displayName
            memberMatches.text = players.matches.toString()
            memberWinRate.text = "${players.wr}%"
            memberMmr.text = players.mmr.toString()
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
        holder.bind(getItem(position), position)
        when (position) {
            0 -> holder.rankings.setTextColor(
                holder.itemView.context.getColor(R.color.gold)
            )
            1 -> holder.rankings.setTextColor(
                holder.itemView.context.getColor(R.color.silver)
            )
            2 -> holder.rankings.setTextColor(
                holder.itemView.context.getColor(R.color.bronze)
            )
        }
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