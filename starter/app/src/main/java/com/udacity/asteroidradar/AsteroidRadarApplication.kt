package com.udacity.asteroidradar

import android.app.Application
import androidx.work.*
import com.udacity.asteroidradar.worker.AsteroidDataCleaner
import com.udacity.asteroidradar.worker.AsteroidDataRefresher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AsteroidRadarApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        initializeAsteroidManagementSystem()
    }

    private fun initializeAsteroidManagementSystem() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        setupAsteroidRefresher()

        setupAsteroidCleaner()
    }

    private fun setupAsteroidRefresher() {
        val refreshRequest = PeriodicWorkRequestBuilder<AsteroidDataRefresher>(1, TimeUnit.DAYS)
            .setConstraints(workConstraints())
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            AsteroidDataRefresher.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP, refreshRequest
        )
    }

    private fun setupAsteroidCleaner() {
        val cleanRequest = PeriodicWorkRequestBuilder<AsteroidDataCleaner>(1, TimeUnit.DAYS)
            .setConstraints(workConstraints())
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            AsteroidDataCleaner.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP, cleanRequest
        )
    }

    private fun workConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()
    }
}