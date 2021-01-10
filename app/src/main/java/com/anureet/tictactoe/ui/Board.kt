package com.anureet.tictactoe.ui

import com.anureet.tictactoe.data.Coordinates

class Board {

    //Strings for PLAYER and COMPUTER
    companion object {
        const val PLAYER = "O"
        const val COMPUTER = "X"
    }

    //This is our internal board
    //and for this we used a 3 by 3 array of Strings
    val board = Array(3) { arrayOfNulls<String>(3) }

    //This property is giving us
    //a list of all the empty cells
    val availableCells: List<Coordinates>
        get() {
            val cells = mutableListOf<Coordinates>()
            for (i in board.indices) {
                for (j in board.indices) {
                    if (board[i][j].isNullOrEmpty()) {
                        cells.add(Coordinates(i, j))
                    }
                }
            }
            return cells
        }

    //this property will tell
    //if the game is over or not
    val isGameOver: Boolean
        get() = hasComputerWon() || hasPlayerWon() || availableCells.isEmpty()


    //These functions are checking
    //Weather the computer or player has won or not
    fun hasComputerWon(): Boolean {
        if (board[0][0] == board[1][1] &&
            board[0][0] == board[2][2] &&
            board[0][0] == COMPUTER ||
            board[0][2] == board[1][1] &&
            board[0][2] == board[2][0] &&
            board[0][2] == COMPUTER
        ) {
            return true
        }

        for (i in board.indices) {
            if (
                board[i][0] == board[i][1] &&
                board[i][0] == board[i][2] &&
                board[i][0] == COMPUTER ||
                board[0][i] == board[1][i] &&
                board[0][i] == board[2][i] &&
                board[0][i] == COMPUTER
            ) {
                return true
            }
        }

        return false
    }

    fun hasPlayerWon(): Boolean {

        if (board[0][0] == board[1][1] &&
            board[0][0] == board[2][2] &&
            board[0][0] == PLAYER ||
            board[0][2] == board[1][1] &&
            board[0][2] == board[2][0] &&
            board[0][2] == PLAYER
        ) {
            return true
        }

        for (i in board.indices) {
            if (
                board[i][0] == board[i][1] &&
                board[i][0] == board[i][2] &&
                board[i][0] == PLAYER ||
                board[0][i] == board[1][i] &&
                board[0][i] == board[2][i] &&
                board[0][i] == PLAYER
            ) {
                return true
            }
        }

        return false
    }


    //in this var we will store the computersMove
    var computersMove: Coordinates? = null

    //this is our minimax function to cal]late
    //the best move for the computer
    fun minimax(depth: Int, player: String): Int {
        if (hasComputerWon()) return +1
        if (hasPlayerWon()) return -1

        if (availableCells.isEmpty()) return 0

        var min = Integer.MAX_VALUE
        var max = Integer.MIN_VALUE

        for (i in availableCells.indices) {
            val cell = availableCells[i]
            if (player == COMPUTER) {
                placeMove(cell, COMPUTER)
                val currentScore = minimax(depth + 1, PLAYER)
                max = Math.max(currentScore, max)

                if (currentScore >= 0) {
                    if (depth == 0) computersMove = cell
                }

                if (currentScore == 1) {
                    board[cell.row][cell.col] = ""
                    break
                }

                if (i == availableCells.size - 1 && max < 0) {
                    if (depth == 0) computersMove = cell
                }

            } else if (player == PLAYER) {
                placeMove(cell, PLAYER)
                val currentScore = minimax(depth + 1, COMPUTER)
                min = Math.min(currentScore, min)

                if (min == -1) {
                    board[cell.row][cell.col] = ""
                    break
                }
            }
            board[cell.row][cell.col] = ""
        }

        return if (player == COMPUTER) max else min
    }

    //this function is placing a move in the given cell
    fun placeMove(cell: Coordinates, player: String) {
        board[cell.row][cell.col] = player
    }

}