package com.udacity.asteroidradar.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Asteroid::class, PictureOfDay::class], version = 5)
abstract class RoomDB : RoomDatabase() {

    abstract fun getDAO():DAO

      companion object{
    private var instance: RoomDB? = null
    @Synchronized
    fun getInstance(context: Context): RoomDB? {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                RoomDB::class.java,
                "Asteroid"
            ).fallbackToDestructiveMigration().build()
        }
        return instance
    }}
}