package com.anureet.tictactoe.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.anureet.tictactoe.data.Victory

@Dao
interface VictoryListDao {
    // Calculating winning ratio
    @Query("SELECT * FROM `victory` ORDER BY totalGamesWon/totalGamesPlayed DESC LIMIT 10")
    fun getPlayers(): LiveData<List<Victory>>
}