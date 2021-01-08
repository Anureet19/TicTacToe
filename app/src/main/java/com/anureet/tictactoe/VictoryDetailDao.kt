package com.anureet.tictactoe

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VictoryDetailDao {
    @Query("SELECT * FROM `victory`")
    fun getVictory(): LiveData<List<Victory>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVictory(victory: Victory): Long

    @Update
    suspend fun updateVictory(victory: Victory)

    @Delete
    suspend fun deleteVictory(victory: Victory)
}