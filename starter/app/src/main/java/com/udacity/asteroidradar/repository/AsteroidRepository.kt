package com.udacity.asteroidradar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.all
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepository(private val asteroidDao: AsteroidDao) {

    val asteroids =
        Transformations.map(asteroidDao.getByDateBetweenAsLiveData(currentDay(), plusDays(6))) {
            it.asDomainModels()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroids =
                AsteroidApi.asteroids.getAsteroids(currentDay(), plusDays(6), Constants.API_KEY)

            asteroidDao.upsert(*asteroids.all().asDatabaseModels().toTypedArray())
        }
    }

    suspend fun cleanAsteroids() {
        withContext(Dispatchers.IO) {
            asteroidDao.deleteByDateBelow(currentDay())
        }
    }

    companion object {
        fun from(appContext: Context): AsteroidRepository {
            return AsteroidRepository(AsteroidDatabase.get(appContext).asteroidDao)
        }
    }
}