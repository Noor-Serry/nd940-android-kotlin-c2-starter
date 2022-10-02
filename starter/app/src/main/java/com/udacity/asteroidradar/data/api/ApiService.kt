package com.udacity.asteroidradar.data.api

import com.udacity.asteroidradar.data.room.PictureOfDay
import retrofit2.Call
import retrofit2.http.GET


interface ApiService {
    @GET("apod?api_key=ziBqPUoN9OTlf9DM8rgzpMKPXhnSkvih3fLwFT46")
    fun imageOfTheDay(): Call<PictureOfDay>
}