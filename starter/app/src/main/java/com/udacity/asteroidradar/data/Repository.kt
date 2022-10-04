package com.udacity.asteroidradar.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.data.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.room.Asteroid
import com.udacity.asteroidradar.data.room.DAO
import com.udacity.asteroidradar.data.room.PictureOfDay
import com.udacity.asteroidradar.data.room.RoomDB
import com.udacity.asteroidradar.utils.Constants
import com.udacity.asteroidradar.utils.Constants.convertImageToString
import com.udacity.asteroidradar.utils.Constants.request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.time.LocalDate

class Repository( var context: Context) {
    var dao = RoomDB.getInstance(context)!!.getDAO()

      fun getToDayAsteroids(): LiveData<List<Asteroid>>{
         return dao.getToDayAsteroids(LocalDate.now().toString())
     }

    fun getDataOfAsteroidsFromApi()  {
        var client = OkHttpClient.Builder().build()
        client.newCall(request).enqueue(object : okhttp3.Callback{
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                showError(e.message!!)
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val jsonString = response.body()!!.string()
               saveDataInLocalDataBase(parseAsteroidsJsonResult(JSONObject(jsonString)))
                 } })
    }

    fun  showError(error : String){
        GlobalScope.launch(Dispatchers.Main) {
            Toast.makeText(context, error, Toast.LENGTH_LONG).show() }
    }

     private  fun saveDataInLocalDataBase(asteroids:ArrayList<Asteroid>){
         GlobalScope.launch(Dispatchers.IO) {
              for(asteroid in asteroids)
                  dao.setNewAsteroid(asteroid) }
     }

     fun saveImageOfTheDay(){
        val call : Call<PictureOfDay> = Constants.apiService.imageOfTheDay()
        call.enqueue(RetrofitCallBack(dao))
    }

    fun deleteOldData(){
        GlobalScope.launch(Dispatchers.IO){
            dao.deleteOldData(LocalDate.now().toString()) }
    }


   private class RetrofitCallBack(var dao : DAO  ) : Callback<PictureOfDay>{

        override fun onResponse(call: Call<PictureOfDay>, response: Response<PictureOfDay>) {
            if(response.body()?.mediaType!!.equals("image")){
                GlobalScope.launch(Dispatchers.IO) {
                    var bitmap =  Picasso.get().load(response.body()?.image).get()
                    var image  =  convertImageToString(bitmap)
                    dao.insertNewImageOfTheDay(PictureOfDay("image",image, response.body()!!.explanation))}}
        }

        override fun onFailure(call: Call<PictureOfDay>, t: Throwable) {

        }
    }


}



