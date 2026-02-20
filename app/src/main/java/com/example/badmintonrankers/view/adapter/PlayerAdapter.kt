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

class PlayerAdapter(
    val onClick: (Players) -> Unit
) : ListAdapter<Players, PlayerAdapter.PlayerViewHolder>(PlayersCallBack) {

    class PlayerViewHolder(itemView: View, val onClick: (Players) -> Unit):RecyclerView.ViewHolder(itemView){
        val memberRank : ImageView = itemView.findViewById(R.id.memberRank)
        val memberName : TextView = itemView.findViewById(R.id.memberName)

        var currItem : Players? = null

        init {
            itemView.setOnClickListener{
                currItem?.let{
                    onClick(it)
                }
            }
        }


        fun bind(players: Players){
            currItem = players
            memberName.text = players.displayName
            val imageId = itemView.context.resources.getIdentifier(
                players.rankImageLarge,
                "drawable",
                itemView.context.packageName
            )
            Glide.with(itemView.context).load(imageId).error(R.drawable.account).centerCrop().into(memberRank)

        }

    }
    fun setFilteredList(list: List<Players>){
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_members,parent,false)
        return PlayerViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object PlayersCallBack : DiffUtil.ItemCallback<Players>(){
    override fun areItemsTheSame(oldItem: Players, newItem: Players): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Players, newItem: Players): Boolean {
        return oldItem == newItem
    }

}