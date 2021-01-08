package com.anureet.tictactoe

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.fragment_multiplayer.*

class MultiplayerFragment : Fragment() {
    private var playerOneTurn = true
    private var playerOneMoves = mutableListOf<Int>()
    private var playerTwoMoves = mutableListOf<Int>()


    private val possibleWins: Array<List<Int>> = arrayOf(
        //horizontal lines
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9),

        //vertical lines
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        listOf(3, 6, 9),

        //diagonal lines
        listOf(1, 5, 9),
        listOf(3, 5, 7)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multiplayer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupBoard()
    }

    private fun setupBoard(){
        var counter = 1

        val params1 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0
        )
        val params2 = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        for(i in 1..3){
            val linearLayout = LinearLayout(activity!!)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.layoutParams = params1
            params1.weight  = 1.0F

            for(j in 1..3){
                val button = Button(activity!!)
                button.id = counter
                button.textSize = 42.0F
                button.setTextColor(ContextCompat.getColor(activity!!, R.color.colorAccent))

                button.layoutParams = params2
                params2.weight = 1.0F
                button.setOnClickListener{
                    recordMove(it)
                }
                linearLayout.addView(button)
                counter++
            }
            board.addView(linearLayout)
        }
    }

    private fun recordMove(view: View){
        val button = view as Button
        val id = button.id

        if(playerOneTurn){
            playerOneMoves.add(id)

            button.text = "O"
            button.isEnabled = false
            if(checkWin(playerOneMoves)){
                showWinMessage(player_one)
            } else{
                playerOneTurn = false
                togglePlayerTurn(player_two_label, player_one_label)
            }

        } else{
            playerTwoMoves.add(id)

            button.text = "X"
            button.isEnabled = false
            if(checkWin(playerTwoMoves)){
                showWinMessage(player_two)
            } else{
                playerOneTurn = true
                togglePlayerTurn(player_one_label, player_two_label)
            }
        }
    }



    private fun checkWin(moves: List<Int>): Boolean{
        var won = false
        if(moves.size >= 3){
            run loop@{
                possibleWins.forEach {
                    if (moves.containsAll(it)) {
                        won = true
                        return@loop
                    }
                }
            }
        }
        return won
    }

    private fun togglePlayerTurn(playerOn: TextView, playerOff: TextView){
        playerOff.setTextColor(
            ContextCompat.getColor(activity!!, R.color.colorPrimary))
        playerOff.setTypeface(null, Typeface.NORMAL)
        playerOn.setTextColor(
            ContextCompat.getColor(activity!!, R.color.colorAccent))
        playerOn.setTypeface(null, Typeface.BOLD)
    }

    private fun showWinMessage(player: EditText){
        var playerName = player.text.toString()
        if(playerName.isBlank()){
            playerName = player.hint.toString()
        }
//        Toast.makeText(this, "Congratulations! $playerName You Won", Toast.LENGTH_SHORT).show()

    }

}