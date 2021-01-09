package com.anureet.tictactoe.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.anureet.tictactoe.data.Victory
import com.anureet.tictactoe.data.VictoryDatabase
import com.anureet.tictactoe.data.VictoryListDao

class VictoryListRepository(context: Application) {
    private val victoryListDao: VictoryListDao = VictoryDatabase.getDatabase(context).victoryListDao()

    fun getPlayers(): LiveData<List<Victory>> {
        return victoryListDao.getPlayers()
    }
}