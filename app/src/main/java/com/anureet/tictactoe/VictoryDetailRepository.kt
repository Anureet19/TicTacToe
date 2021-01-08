package com.anureet.tictactoe

import android.app.Application
import androidx.lifecycle.LiveData

class VictoryDetailRepository(context: Application) {
    private val victoryDetailDao: VictoryDetailDao = VictoryDatabase.getDatabase(context).victoryDetailDao()

    fun getVictory(): LiveData<List<Victory>> {
        return victoryDetailDao.getVictory()
    }

    suspend fun insertVictory(victory: Victory): Long{
        return victoryDetailDao.insertVictory(victory)
    }
    suspend fun updateVictory(victory: Victory){
        victoryDetailDao.updateVictory(victory)
    }
    suspend fun deleteVictory(victory: Victory){
        victoryDetailDao.deleteVictory(victory)
    }
}