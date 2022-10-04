package com.udacity.asteroidradar.utils

import android.graphics.Bitmap
import android.util.Base64
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.data.api.ApiService
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.ByteArrayOutputStream
import java.time.LocalDate

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/neo/rest/v1/"
    const val IMAGE_BASE_URL = "https://api.nasa.gov/planetary/"
    val apiService = retrofit.create(ApiService::class.java)
    val request = Request.Builder().url(url).build()
    fun convertImageToString(bm: Bitmap):String{
        var byteArrayOutputStream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

}
val date = LocalDate.now()
val url = "${Constants.BASE_URL}feed?start_date=${date}&end_date=${date.plusDays(7)}&${BuildConfig.API_KEY}"
val retrofit : Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(Constants.IMAGE_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())).build()
}
