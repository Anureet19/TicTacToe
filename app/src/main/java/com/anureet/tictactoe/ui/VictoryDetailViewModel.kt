package com.anureet.tictactoe.ui

import android.app.Application
import androidx.lifecycle.*
import com.anureet.tictactoe.data.Victory
import com.anureet.tictactoe.data.VictoryDetailRepository
import kotlinx.coroutines.launch

class VictoryDetailViewModel(application: Application): AndroidViewModel(application) {
    private val repo: VictoryDetailRepository =
        VictoryDetailRepository(application)
    private val _victoryId = MutableLiveData<Long>(0)

    val victoryId: LiveData<Long>
        get() = _victoryId

    val victory: LiveData<List<Victory>>
        get() = repo.getVictory()


    fun setVictoryId(id: Long){
        if(_victoryId.value != id){
            _victoryId.value = id
        }
    }

    fun saveVictory(victory: Victory){
        viewModelScope.launch {
            if(_victoryId.value == 0L){
                _victoryId.value = repo.insertVictory(victory)
            }else{
                repo.updateVictory(victory)
            }
        }
    }

//    fun deleteTask(){
//        viewModelScope.launch {
//            task.value?.let{
//                repo.deleteVictory(it)
//            }
//        }
//    }


}