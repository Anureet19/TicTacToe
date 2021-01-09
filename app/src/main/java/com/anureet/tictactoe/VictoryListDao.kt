package com.anureet.tictactoe

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface VictoryListDao {
    @Query("SELECT * FROM `victory` ORDER BY totalGamesWon DESC")
    fun getPlayers(): LiveData<List<Victory>>
}