package com.udacity.asteroidradar



import android.os.Bundle
import androidx.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.udacity.asteroidradar.utils.workManager.MyWork
import java.time.LocalDate


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        upDateData()

    }

    private fun upDateData(){
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(!sharedPreferences.getString("lastDate","2022-10-1").equals("${LocalDate.now()}"))
            startWorkManger()
    }

    private fun startWorkManger(){
        var constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED).setRequiresCharging(true).build()
        val request = OneTimeWorkRequest.Builder(MyWork::class.java)
            .setConstraints(constraints).build()
        WorkManager.getInstance(this).enqueue(request)
    }

}
