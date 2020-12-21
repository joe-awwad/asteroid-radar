package com.udacity.asteroidradar.service

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidRadarApi
import com.udacity.asteroidradar.asDomainModels
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AsteroidServiceTest {

    @Test
    fun shouldGetAsteroidsFromApi() {

        val asteroids = runBlocking {

            return@runBlocking AsteroidRadarApi.asteroids.getAsteroids(
                startDate = "2020-10-01",
                endDate = "2020-10-01",
                apiKey = Constants.API_KEY
            ).asteroids["2020-10-01"]?.asDomainModels()
        }

        Assert.assertEquals(19, asteroids?.size)
    }

}