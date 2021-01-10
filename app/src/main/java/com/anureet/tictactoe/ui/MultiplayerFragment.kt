package com.anureet.tictactoe.ui

import android.graphics.Typeface
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.anureet.tictactoe.R
import com.anureet.tictactoe.data.Victory
import kotlinx.android.synthetic.main.fragment_multiplayer.*


class MultiplayerFragment : Fragment() {
    private var playerOneTurn = true

    // To store moves of players
    private var playerOneMoves = mutableListOf<Int>()
    private var playerTwoMoves = mutableListOf<Int>()

    // Setting up possible wins
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

    // For animation
    private lateinit var balloonAnimation: AnimationDrawable

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

        // Calling function to check if the player's record is available or not
        // Updating the record
        IncrementIfMatchFound()

        val playerOne = MultiplayerFragmentArgs.fromBundle(
            requireArguments()
        ).Player1Name
        val playerTwo = MultiplayerFragmentArgs.fromBundle(
            requireArguments()
        ).Player2Name

        // Setting up player names on textview
        player_one_label.text = playerOne
        player_two_label.text = playerTwo

        // Setting up board
        setupBoard()
    }

    // If a player is replaying, this function increments the game count
    private fun IncrementIfMatchFound() {
        // Incrementing game count
        var flag = 0
        var playerOneFound = false
        var playerTwoFound = false

        val playerOne = MultiplayerFragmentArgs.fromBundle(
            requireArguments()
        ).Player1Name
        val playerTwo = MultiplayerFragmentArgs.fromBundle(
            requireArguments()
        ).Player2Name

        // List of all the objects present in the database
        viewModel.victory.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty() && flag == 0) {
                flag = 1
                for (i in it.indices) {
                    if (it[i].name.toLowerCase().equals(playerOne.toLowerCase())) {
                        playerOneFound = true
                        val victory = Victory(
                            it[i].id,
                            it[i].name,
                            it[i].totalGamesPlayed + 1,
                            it[i].totalGamesWon
                        )

                        viewModel.setVictoryId(it[i].id)
                        viewModel.saveVictory(victory)
                    } else if (it[i].name.toLowerCase().equals(playerTwo.toLowerCase())) {
                        playerTwoFound = true
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
                button.id = counter
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

    // Recording each move in array as button ids
    private fun recordMove(view: View){
        val button = view as ImageButton
        val id = button.id

        val playerOne = MultiplayerFragmentArgs.fromBundle(
            requireArguments()
        ).Player1Name
        val playerTwo = MultiplayerFragmentArgs.fromBundle(
            requireArguments()
        ).Player2Name

        if(playerOneTurn){
            playerOneMoves.add(id)

            button.setImageResource(R.drawable.o)
            button.isEnabled = false
            if(checkWin(playerOneMoves)){
                saveDetail(1)
                showWinMessage(playerOne)
            }else if(checkDraw()){
                showDrawMessage()
            } else{
                playerOneTurn = false

                // It will toggle the turn
                togglePlayerTurn(player_two_label, player_one_label)
            }

        } else{
            playerTwoMoves.add(id)

            button.setImageResource(R.drawable.x)
            button.isEnabled = false

            if(checkWin(playerTwoMoves)){
                saveDetail(2)
                showWinMessage(playerTwo)
            }else if(checkDraw()){
                showDrawMessage()
            } else{
                playerOneTurn = true

                // It will toggle the turn
                togglePlayerTurn(player_one_label, player_two_label)
            }
        }
    }

    private fun showDrawMessage() {
        Log.d("Draw","draw")
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
                MultiplayerFragmentDirections.actionMultiplayerFragmentToMainPageFragment()
            )
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()

        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun checkDraw(): Boolean {
        val list = playerOneMoves + playerTwoMoves
        return list.size == 9
    }

    // Saving detail if a new player is playing
    // Updating detail if the player's record exist
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

    // checking for a win by comparing it with possible wins array
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

    // Toggle the turn and highlight player's name
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

    // Shows a dialog box when a player wins
    private fun showWinMessage(player: String){
        // To add animation
        animation()

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Congratulations!")
        builder.setMessage("$player WON")
        builder.setIcon(R.drawable.cool)

        //It will restart the existing game
        builder.setPositiveButton("Restart Game"){dialogInterface, which ->
            refreshFragment()
        }

        //It will navigate to the main menu
        builder.setNegativeButton("Main Page"){dialogInterface, which ->
            findNavController().navigate(
                MultiplayerFragmentDirections.actionMultiplayerFragmentToMainPageFragment()
            )
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()

        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    // Balloon and confetti will pop up on a win
    private fun animation() {
        // Added Animation
        val balloonImage = requireActivity().findViewById<ImageView>(R.id.balloonView)
        balloonImage.setBackgroundResource(R.drawable.animation)
        balloonAnimation = balloonImage.background as AnimationDrawable

        balloonAnimation.start()
    }

    // Refreshes Fragment
    fun refreshFragment() {
        playerOneMoves = mutableListOf()
        playerTwoMoves = mutableListOf()
        val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false)
        }
        ft.detach(this).attach(this).commit()


    }


}