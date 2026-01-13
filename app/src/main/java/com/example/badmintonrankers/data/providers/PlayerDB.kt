package com.example.badmintonrankers.data.providers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.badmintonrankers.data.model.Players

@Database(entities = [Players::class], version = 1, exportSchema = false)
abstract class PlayerDB: RoomDatabase() {

    abstract fun playerDao() : PlayerDao
    companion object{
        @Volatile
        private var INSTANCE: PlayerDB? = null

        fun getDatabase(context: Context): PlayerDB{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlayerDB::class.java,
                    name = "badmin_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}