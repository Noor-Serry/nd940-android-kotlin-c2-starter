package com.udacity.asteroidradar.data.api

import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.data.room.PictureOfDay
import retrofit2.Call
import retrofit2.http.GET


interface ApiService {

     @GET("apod?"+BuildConfig.API_KEY)
    fun imageOfTheDay(): Call<PictureOfDay>

}