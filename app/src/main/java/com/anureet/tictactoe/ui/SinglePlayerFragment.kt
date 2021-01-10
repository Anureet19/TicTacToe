package com.anureet.tictactoe.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.anureet.tictactoe.R
import com.anureet.tictactoe.data.Coordinates
import kotlinx.android.synthetic.main.fragment_multiplayer.*


class SinglePlayerFragment : Fragment() {

    //creating the board instance
    var InternalBoard = Board()

    private val buttonArray = Array(3) { arrayOfNulls<ImageButton>(3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_player, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        player_one_label.text = "Computer"
        val playerOne = SinglePlayerFragmentArgs.fromBundle(
            requireArguments()
        ).Player1Name
        player_two_label.text = playerOne

        setupBoard()
    }
    // Setting up board
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

            for(j in 1..3){
                val button = ImageButton(requireActivity())
                buttonArray[i-1][j-1] = button
                button.id = counter
                button.setBackgroundResource(R.drawable.shadow_button)

                button.layoutParams = params2
                params2.weight = 1.0F
                button.setOnClickListener{
                    TakeAction(i-1,j-1)
                }
                linearLayout.addView(button)
                counter++
            }
            board.addView(linearLayout)
        }
    }

    private fun TakeAction(row:Int, col:Int) {

        //checking if the game is not over
        if (!InternalBoard.isGameOver) {

            //creating a new cell with the clicked index
            val cell = Coordinates(row,col)

            //placing the move for player
            InternalBoard.placeMove(cell, Board.PLAYER)

            //calling minimax to calculate the computers move
            InternalBoard.minimax(0, Board.COMPUTER)

            //performing the move for computer
            InternalBoard.computersMove?.let {
                InternalBoard.placeMove(it, Board.COMPUTER)
            }

            //mapping the internal board to visual board
            reflectingChanges()
        }

        //Displaying the results
        //according to the game status
        when {
            InternalBoard.hasComputerWon() -> showLooseMessage()
            InternalBoard.hasPlayerWon() ->showWinMessage()
            InternalBoard.isGameOver -> showDrawMessage()
        }

    }

    private fun showWinMessage() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Congratulations!")
        builder.setMessage("You WON")
        builder.setIcon(R.drawable.cool)

        //It will restart the existing game
        builder.setPositiveButton("Restart Game"){dialogInterface, which ->
            refreshFragment()
        }

        //It will navigate to the main menu
        builder.setNegativeButton("Main Page"){dialogInterface, which ->
            findNavController().navigate(
                SinglePlayerFragmentDirections.actionSinglePlayerFragmentToMainPageFragment()            )
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()

        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    private fun showLooseMessage() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Sorry You Loose!")
        builder.setMessage("Computer WON")
        builder.setIcon(R.drawable.crying)

        //It will restart the existing game
        builder.setPositiveButton("Restart Game") { dialogInterface, which ->
            refreshFragment()
        }

        //It will navigate to the main menu
        builder.setNegativeButton("Main Page") { dialogInterface, which ->
            findNavController().navigate(
                SinglePlayerFragmentDirections.actionSinglePlayerFragmentToMainPageFragment()            )
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()

        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun showDrawMessage(){
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Draw!")
        builder.setMessage("This match is a draw!")
        builder.setIcon(R.drawable.handshake)

        //It will restart the existing game
        builder.setPositiveButton("Restart Game"){dialogInterface, which ->
            refreshFragment()
        }

        //It will navigate to the main menu
        builder.setNegativeButton("Main Page"){dialogInterface, which ->
            findNavController().navigate(
               SinglePlayerFragmentDirections.actionSinglePlayerFragmentToMainPageFragment()
            )
        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()

        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun reflectingChanges() {
        for (i in InternalBoard.board.indices) {
            for (j in InternalBoard.board.indices) {
                when (InternalBoard.board[i][j]) {
                    Board.PLAYER -> {
                        buttonArray[i][j]?.setImageResource(R.drawable.o)
                        buttonArray[i][j]?.isEnabled = false
                    }
                    Board.COMPUTER -> {
                        buttonArray[i][j]?.setImageResource(R.drawable.x)
                        buttonArray[i][j]?.isEnabled = false
                    }
                    else -> {
                        buttonArray[i][j]?.setImageResource(0)
                        buttonArray[i][j]?.isEnabled = true
                    }
                }
            }
        }
    }

    // Refreshes Fragment
    fun refreshFragment() {
        InternalBoard = Board()
        reflectingChanges()

    }


}