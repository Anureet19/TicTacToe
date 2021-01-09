package com.anureet.tictactoe.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.anureet.tictactoe.R
import kotlinx.android.synthetic.main.fragment_main_page.*


class MainPageFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Leads to "enter details" page
        startGameButton.setOnClickListener {
            findNavController().navigate(
                MainPageFragmentDirections.actionMainPageFragmentToPlayerDetailFragment()
            )
        }

        // Leaderboard
        leaderboardButton.setOnClickListener {
            findNavController().navigate(
                MainPageFragmentDirections.actionMainPageFragmentToLeaderboardFragment()
            )
        }

        inviteFriendsButton.setOnClickListener {
            inviteFriends()
        }

    }

    // Sharing a message to join
    private fun inviteFriends() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Join me in playing Tic Tac Toe. It's fun")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

}