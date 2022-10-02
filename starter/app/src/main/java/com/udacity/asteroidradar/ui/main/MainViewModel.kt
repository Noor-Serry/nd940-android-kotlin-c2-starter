package com.udacity.asteroidradar.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.udacity.asteroidradar.data.Repository
import java.time.LocalDate


class MainViewModel(application : Application) : AndroidViewModel(application) {
    private val repository = Repository(application)
    var toDayAsteroids = repository.dao.getToDayAsteroids(LocalDate.now().toString())
    var sevenDayAsteroid = repository.dao.getSevenDayAsteroids(LocalDate.now().toString())
    var allAsteroids = repository.dao.getAllAsteroids()
    var imageOfTheDay = repository.dao.getImageOfTheDay()
init {

}

}