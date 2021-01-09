package com.anureet.tictactoe.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Victory::class], version = 1)
abstract class VictoryDatabase: RoomDatabase() {
    abstract fun victoryDetailDao(): VictoryDetailDao
    abstract fun victoryListDao() : VictoryListDao

    companion object {
        @Volatile
        private var instance: VictoryDatabase? = null

        fun getDatabase(context: Context) = instance
            ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    VictoryDatabase::class.java,
                    "victory_database"
                ).build().also { instance = it }
            }
    }
}