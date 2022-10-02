package com.udacity.asteroidradar.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setNewAsteroid(asteroid: Asteroid)
    @Query("select * from Asteroid order by closeApproachDate")
    fun getAllAsteroids():LiveData<List<Asteroid>>
    @Query("select * from Asteroid where closeApproachDate = :closeApproachDate")
    fun getToDayAsteroids(closeApproachDate: String):LiveData<List<Asteroid>>
    @Query("select * from Asteroid where closeApproachDate >= :closeApproachDate order by closeApproachDate")
    fun getSevenDayAsteroids(closeApproachDate: String):LiveData<List<Asteroid>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewImageOfTheDay(pictureOfDay: PictureOfDay)
    @Query("select * from PictureOfDay")
    fun getImageOfTheDay():LiveData<List<PictureOfDay>>


}