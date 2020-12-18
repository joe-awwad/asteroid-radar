package com.udacity.asteroidradar.repository

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.udacity.asteroidradar.currentDay
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.minusDays
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AsteroidRepositoryTest {

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private val asteroidRepository = AsteroidRepository.from(appContext)

    @After
    fun tearDown() {
        runBlocking {
            AsteroidDatabase.get(appContext).asteroidDao.deleteAll()
        }
    }

    @Test
    fun testRefreshAsteroids() {
        val asteroids = runBlocking {
            asteroidRepository.refreshAsteroids()

            return@runBlocking AsteroidDatabase.get(appContext).asteroidDao.getAll()
        }

        assertTrue(asteroids.isNotEmpty())
    }

    @Test
    fun testCleanAsteroids() {
        val asteroids = runBlocking {

            AsteroidDatabase.get(appContext).asteroidDao.upsert(
                AsteroidEntity(1L, "", currentDay(), 0.0, 0.0, 0.0, 0.0, false),
                AsteroidEntity(2L, "", minusDays(1), 0.0, 0.0, 0.0, 0.0, false)
            )

            asteroidRepository.cleanAsteroids()

            return@runBlocking AsteroidDatabase.get(appContext).asteroidDao.getByDateBetween(
                minusDays(1),
                currentDay()
            )
        }

        assertEquals(1, asteroids.size)
        assertEquals(1L, asteroids[0].id)
    }
}