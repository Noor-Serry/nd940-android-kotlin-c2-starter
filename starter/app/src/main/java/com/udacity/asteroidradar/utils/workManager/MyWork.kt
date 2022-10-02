package com.udacity.asteroidradar.utils.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.data.Repository



class MyWork(var context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        with(Repository(context)){
            getDataOfAsteroidsFromApi()
            saveImageOfTheDay()
        }
        return Result.success()
    }




}