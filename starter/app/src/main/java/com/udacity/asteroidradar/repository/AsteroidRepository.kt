package com.udacity.asteroidradar.repository

import android.content.Context
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.api.AsteroidRadarApi
import com.udacity.asteroidradar.api.all
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepository(private val asteroidDao: AsteroidDao) {

    /**
     * Represent the saved asteroids given the clean-refresh cycle of the data workers
     */
    val asteroids =
        Transformations.map(asteroidDao.getByDateBetweenAsLiveData(currentDay(), plusDays(6))) {
            it.asDomainModels()
        }

    val currentDayAsteroids =
        Transformations.map(asteroidDao.getByDateAsLiveData(currentDay())) {
            it.asDomainModels()
        }

    val currentWeekAsteroids =
        Transformations.map(
            asteroidDao.getByDateBetweenAsLiveData(firstDayOfWeek(), lastDayOfWeek())
        ) {
            it.asDomainModels()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroids =
                AsteroidRadarApi.asteroids.getAsteroids(
                    currentDay(),
                    plusDays(6),
                    Constants.API_KEY
                )

            asteroidDao.upsert(*asteroids.all().asDatabaseModels().toTypedArray())
        }
    }

    suspend fun cleanAsteroids() {
        withContext(Dispatchers.IO) {
            asteroidDao.deleteByDateBelow(currentDay())
        }
    }

    suspend fun getPictureOfDay(): PictureOfDay {
        return AsteroidRadarApi.pictureOfOfDay.get(Constants.API_KEY)
    }

    companion object {
        fun from(appContext: Context): AsteroidRepository {
            return AsteroidRepository(AsteroidDatabase.get(appContext).asteroidDao)
        }
    }
}