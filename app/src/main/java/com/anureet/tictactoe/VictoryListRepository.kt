package com.anureet.tictactoe

import android.app.Application
import androidx.lifecycle.LiveData

class VictoryListRepository(context: Application) {
    private val victoryListDao: VictoryListDao = VictoryDatabase.getDatabase(context).victoryListDao()

    fun getPlayers(): LiveData<List<Victory>> {
        return victoryListDao.getPlayers()
    }
}