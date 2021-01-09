package com.anureet.tictactoe.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.anureet.tictactoe.R
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

        beginGameButton.isEnabled = false
        validateText()
    }

    private fun validateText(){
        val nameTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){
                val playerOne = player1Name.text.toString()
                val playerTwo = player2Name.text.toString()

                when {
                    playerOne.isEmpty() -> {
                        beginGameButton.isEnabled = false
                        player1Name.error = "Player Name cannot be empty!"
            //                    Toast.makeText(activity,"Player Name cannot be empty!",Toast.LENGTH_SHORT).show()
                    }
                    playerTwo.isEmpty() -> {
                        beginGameButton.isEnabled = false
                        player2Name.error = "Player Name cannot be empty!"
                    }
                    else -> {
                        val playerOne = player1Name.text.toString()
                        val playerTwo = player2Name.text.toString()
                        beginGameButton.isEnabled = true
            //                    val names: List<String> = listOf(playerOne,playerTwo)
                        beginGameButton.setOnClickListener {
                            findNavController().navigate(
                                PlayerDetailFragmentDirections.actionPlayerDetailFragmentToMultiplayerFragment(
                                    playerOne, playerTwo
                                )
                            )
                        }
                    }
                }
            }
        }
        player1Name.addTextChangedListener(nameTextWatcher)
        player2Name.addTextChangedListener(nameTextWatcher)

    }


}