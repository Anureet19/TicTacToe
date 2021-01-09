package com.anureet.tictactoe.ui

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anureet.tictactoe.R
import com.anureet.tictactoe.data.Victory
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

    private lateinit var viewModel: VictoryDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(VictoryDetailViewModel::class.java)

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

        IncrementIfMatchFound()

        val playerOne = MultiplayerFragmentArgs.fromBundle(
            requireArguments()
        ).Player1Name
        val playerTwo = MultiplayerFragmentArgs.fromBundle(
            requireArguments()
        ).Player2Name

        player_one_label.text = playerOne
        player_two_label.text = playerTwo

        // Setting up board
        setupBoard()
    }

    private fun IncrementIfMatchFound() {
        // Incrementing game count
        var flag = 0

        val playerOne = MultiplayerFragmentArgs.fromBundle(
            requireArguments()
        ).Player1Name
        val playerTwo = MultiplayerFragmentArgs.fromBundle(
            requireArguments()
        ).Player2Name
        viewModel.victory.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty() && flag == 0) {
                flag = 1
                for (i in it.indices) {
                    if (it[i].name.toLowerCase().equals(playerOne.toLowerCase())) {
                        val victory = Victory(
                            it[i].id,
                            it[i].name,
                            it[i].totalGamesPlayed + 1,
                            it[i].totalGamesWon
                        )

                        viewModel.setVictoryId(it[i].id)
                        viewModel.saveVictory(victory)
                    } else if (it[i].name.toLowerCase().equals(playerTwo.toLowerCase())) {
                        val victory = Victory(
                            it[i].id,
                            it[i].name,
                            it[i].totalGamesPlayed + 1,
                            it[i].totalGamesWon
                        )

                        viewModel.setVictoryId(it[i].id)
                        viewModel.saveVictory(victory)
                    }
                }
            }
        })
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
            val linearLayout = LinearLayout(requireActivity())
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.layoutParams = params1
            params1.weight  = 1.0F
//            params1.setMargins(5,5,5,5)

            for(j in 1..3){
                val button = ImageButton(requireActivity())
                button.id = counter
//                button.textSize = 64.0F
//                button.setTextColor(ContextCompat.getColor(requireActivity(),
//                    R.color.outside
//                ))

                button.setBackgroundResource(R.drawable.shadow_button)

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
        val button = view as ImageButton
        val id = button.id

        if(playerOneTurn){
            playerOneMoves.add(id)

//            button.text = "O"
            button.setImageResource(R.drawable.o)
            button.isEnabled = false
            if(checkWin(playerOneMoves)){
                saveDetail(1)
//                showWinMessage(player_one)
            } else{
                playerOneTurn = false
                togglePlayerTurn(player_two_label, player_one_label)
            }

        } else{
            playerTwoMoves.add(id)

//            button.text = "X"
            button.setImageResource(R.drawable.x)
            button.isEnabled = false
            if(checkWin(playerTwoMoves)){
                saveDetail(2)
//                showWinMessage(player_two)
            } else{
                playerOneTurn = true
                togglePlayerTurn(player_one_label, player_two_label)
            }
        }
    }

    private fun saveDetail(playerNum: Int) {
        var match = false
        var flag = 0

        val playerOne = MultiplayerFragmentArgs.fromBundle(
            requireArguments()
        ).Player1Name
        val playerTwo = MultiplayerFragmentArgs.fromBundle(
            requireArguments()
        ).Player2Name

        var name = if(playerNum==1){
            playerOne
        }else{
            playerTwo
        }

        viewModel.victory.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty() && flag==0){
                flag=1
                for(i in it.indices){
                    if(it[i].name.toLowerCase().equals(name.toLowerCase())){
                        match = true
                        val victory = Victory(
                            it[i].id,
                            it[i].name,
                            it[i].totalGamesPlayed,
                            it[i].totalGamesWon + 1
                        )
                        Log.d("Match", it.toString())
                        viewModel.setVictoryId(it[i].id)
                        viewModel.saveVictory(victory)
                        break
                    }
                }
                if(!match){
                    viewModel.setVictoryId(0)
                    val victory = Victory(
                        viewModel.victoryId.value!!,
                        name,
                        1,
                        1
                    )
                    viewModel.saveVictory(victory)
                    Log.d("No Match", it.toString())
                }
            }else if(it.isEmpty() && flag==0){
                flag=1
                viewModel.setVictoryId(0)
                val victory = Victory(
                    viewModel.victoryId.value!!,
                    name,
                    1,
                    1
                )
                viewModel.saveVictory(victory)
                Log.d("Empty", it.toString())
            }

        })

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
            ContextCompat.getColor(requireActivity(),
                R.color.grey
            ))
        playerOff.setTypeface(null, Typeface.NORMAL)
        playerOn.setTextColor(
            ContextCompat.getColor(requireActivity(),
                R.color.white
            ))
        playerOn.setTypeface(null, Typeface.BOLD)
    }

    private fun showWinMessage(player: EditText){
        var playerName = player.text.toString()
        if(playerName.isBlank()){
            playerName = player.hint.toString()
        }

    }


}