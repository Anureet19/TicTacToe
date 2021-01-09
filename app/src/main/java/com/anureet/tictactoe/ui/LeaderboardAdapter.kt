package com.anureet.tictactoe.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anureet.tictactoe.R
import com.anureet.tictactoe.data.Victory
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_player.*

// Adapter for leaderboard recyclerview
class LeaderboardAdapter(private val listener: (Long) -> Unit):
    ListAdapter<Victory, LeaderboardAdapter.ViewHolder>(
        DiffCallback()
    ){

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_player, parent, false)

        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder (override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        init{
            itemView.setOnClickListener{
                listener.invoke(getItem(adapterPosition).id)
            }
        }

        fun bind(player: Victory){
            with(player){
                player_name.text = player.name
                player_score.text = ""+player.totalGamesWon+"/"+player.totalGamesPlayed
//                    ((player.totalGamesWon/player.totalGamesPlayed)*100.0).toString() + "%"
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Victory>() {
    override fun areItemsTheSame(oldItem: Victory, newItem: Victory): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Victory, newItem: Victory): Boolean {
        return oldItem == newItem
    }
}