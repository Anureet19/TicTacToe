package com.anureet.tictactoe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class VictoryListViewModel(application: Application): AndroidViewModel(application) {

    private val repo: VictoryListRepository = VictoryListRepository(application)

    val players: LiveData<List<Victory>>
    get() = repo.getPlayers()

}