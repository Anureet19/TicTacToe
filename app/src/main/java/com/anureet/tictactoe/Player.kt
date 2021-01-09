package com.anureet.tictactoe

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Player (
    @ColumnInfo(name = "name")
    var name: String,

//    @ColumnInfo(name = "totalGamesPlayed")
//    var totalGamesPlayed: Int,

    @ColumnInfo(name = "totalGamesWon")
    var totalGamesWon: Int
)