package com.udacity.asteroidradar.utils.workManager

import android.content.Context
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MyWork(var context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {


     private val repository = Repository(context)

    override fun doWork(): Result {
            repository.saveImageOfTheDay()
            repository.getDataOfAsteroidsFromApi()

            return Result.success()
    }





}