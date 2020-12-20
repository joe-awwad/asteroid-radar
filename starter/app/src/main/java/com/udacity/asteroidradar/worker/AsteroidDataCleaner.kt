package com.udacity.asteroidradar.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.repository.AsteroidRepository
import retrofit2.HttpException

class AsteroidDataCleaner(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "Clean Asteroids"
    }

    override suspend fun doWork(): Result {
        val repository = AsteroidRepository.from(applicationContext)

        return try {
            repository.cleanAsteroids()
            Result.success()

        } catch (e: HttpException) {
            Result.retry()
        }
    }
}