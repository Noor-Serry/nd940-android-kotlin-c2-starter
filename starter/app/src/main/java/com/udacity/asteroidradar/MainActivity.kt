package com.udacity.asteroidradar


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.udacity.asteroidradar.utils.workManager.MyWork
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startWorkManger()

    }

    private fun startWorkManger() {
        var constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED).setRequiresCharging(true).build()
        val request = PeriodicWorkRequestBuilder<MyWork>(1, TimeUnit.DAYS)
            .setConstraints(constraints).build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork("updateDate", ExistingPeriodicWorkPolicy.KEEP, request)
    }

}
