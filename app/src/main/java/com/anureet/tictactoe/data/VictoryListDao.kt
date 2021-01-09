package com.anureet.tictactoe.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.anureet.tictactoe.data.Victory

@Dao
interface VictoryListDao {
    @Query("SELECT * FROM `victory` ORDER BY totalGamesWon/totalGamesPlayed DESC")
    fun getPlayers(): LiveData<List<Victory>>
}