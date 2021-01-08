package com.anureet.tictactoe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "victory")
data class Victory (@PrimaryKey(autoGenerate = true) val id: Long,
                    val name: String,
                    val totalGamesPlayed: Int,
                    val totalGamesWon: Int


)