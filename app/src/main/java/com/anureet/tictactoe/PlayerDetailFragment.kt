package com.anureet.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_player_detail.*

class PlayerDetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val playerOne = player1Name.text.toString()
        val playerTwo = player2Name.text.toString()

        beginGameButton.setOnClickListener {
            findNavController().navigate(
                PlayerDetailFragmentDirections.actionPlayerDetailFragmentToMultiplayerFragment(playerOne,playerTwo)
            )
        }
    }

}