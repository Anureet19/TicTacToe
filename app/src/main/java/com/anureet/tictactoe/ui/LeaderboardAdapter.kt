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
                player_score.text = player.totalGamesWon.toString()
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
//    (private val children : List<Player>)
//    : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>(){
//
//    override fun onCreateViewHolder(parent: ViewGroup,
//                                    viewType: Int): ViewHolder {
//
//        val v =  LayoutInflater.from(parent.context)
//            .inflate(R.layout.list_player,parent,false)
//        return ViewHolder(v)
//    }
//
//    override fun getItemCount(): Int {
//        return children.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder,
//                                  position: Int) {
//        val child = children[position]
//        holder.playerName.text = child.name
//        holder.playerScore.text = child.totalGamesWon.toString()
//    }
//
//
//    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
//
//        val playerName : TextView = itemView.playerName
//        val playerScore: TextView = itemView.playerScore
//
//    }
//}