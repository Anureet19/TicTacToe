package com.anureet.tictactoe.ui

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

        startGameButton.setOnClickListener {
            findNavController().navigate(
                MainPageFragmentDirections.actionMainPageFragmentToPlayerDetailFragment()
            )
        }

        leaderboardButton.setOnClickListener {
            findNavController().navigate(
                MainPageFragmentDirections.actionMainPageFragmentToLeaderboardFragment()
            )
        }

    }

}