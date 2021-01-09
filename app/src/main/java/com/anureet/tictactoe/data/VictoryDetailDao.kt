package com.anureet.tictactoe.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.anureet.tictactoe.data.Victory

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