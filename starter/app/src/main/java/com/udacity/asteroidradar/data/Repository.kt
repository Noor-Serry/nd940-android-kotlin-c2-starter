package com.udacity.asteroidradar.data

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.preference.PreferenceManager
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.data.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.room.Asteroid
import com.udacity.asteroidradar.data.room.PictureOfDay
import com.udacity.asteroidradar.data.room.RoomDB


import com.udacity.asteroidradar.utils.Constants
import com.udacity.asteroidradar.utils.Constants.convertImageToString

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.io.IOException
import java.time.LocalDate

class Repository(var context: Context) {
      val dao = RoomDB.getInstance(context)!!.getDAO()


    fun getDataOfAsteroidsFromApi() {
        val url = Constants.BASE_URL +"feed?start_date=${LocalDate.now()}&end_date=${LocalDate.now().plusDays(7)}&api_key=ziBqPUoN9OTlf9DM8rgzpMKPXhnSkvih3fLwFT46"
        var request = Request.Builder().url(url).build()
        var client = OkHttpClient()
        client.newCall(request).enqueue(object : okhttp3.Callback{
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()}
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val jsonString = response.body()!!.string()
               saveDataInLocalDataBase(parseAsteroidsJsonResult(JSONObject(jsonString)))
                upDateDate()
            }
        })
    }


    private fun upDateDate(){
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        var editor = sharedPreferences.edit()
        editor.putString("lastDate","${LocalDate.now()}")
        editor.apply()
    }

     @RequiresApi(Build.VERSION_CODES.O)
     private  fun saveDataInLocalDataBase(asteroids:ArrayList<Asteroid>){
                 GlobalScope.launch(Dispatchers.IO) {
                     for(asteroid in asteroids)
                         dao.setNewAsteroid(asteroid)
                 }
     }



     fun saveImageOfTheDay(){

        val call : Call<PictureOfDay> = Constants.apiService.imageOfTheDay()
        call.enqueue(object : Callback<PictureOfDay> {
            override fun onResponse(call: Call<PictureOfDay>, response: Response<PictureOfDay>) {
                if(response.body()?.mediaType!!.equals("image")){
                    GlobalScope.launch(Dispatchers.IO) {
                    var bitmap =  Picasso.get().load(response.body()?.image).get()
                    var image  =  convertImageToString(bitmap)
                    dao.insertNewImageOfTheDay(PictureOfDay("image",image, response.body()!!.explanation))}}
            }

            override fun onFailure(call: Call<PictureOfDay>, t: Throwable) {
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(context,t.message,Toast.LENGTH_LONG).show()}
            } })
    }


}

