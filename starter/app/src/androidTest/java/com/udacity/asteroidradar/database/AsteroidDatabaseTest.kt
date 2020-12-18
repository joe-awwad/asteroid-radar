package com.udacity.asteroidradar.database

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.udacity.asteroidradar.Asteroid
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AsteroidDatabaseTest {

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() {
        runBlocking {
            AsteroidDatabase.get(appContext).asteroidDao.upsert(ASTERIX)
        }
    }

    @After
    fun tearDown() {
        runBlocking {
            AsteroidDatabase.get(appContext).asteroidDao.deleteAll()
        }
    }

    @Test
    fun shouldReadFromAsteroidDatabaseGivenDateRange() {
        val asteroids = runBlocking {
            return@runBlocking getAsteroidsIn(START_DATE, END_DATE)
        }

        assertEquals(ASTERIX_ASTEROID_NAME, asteroids[0].codename)
    }

    @Test
    fun shouldReadFromAsteroidDatabaseGivenId() {
        val asteroid = runBlocking {
            return@runBlocking getAsteroid(ASTEROID_ID)
        }

        assertEquals(ASTERIX_ASTEROID_NAME, asteroid.codename)
    }

    @Test
    fun shouldUpdateAsteroidInDatabase() {
        val asteroid = runBlocking {
            AsteroidDatabase.get(appContext).asteroidDao.upsert(OBELIX)
            return@runBlocking getAsteroid(ASTEROID_ID)
        }

        assertEquals(OBELIX_ASTEROID_NAME, asteroid.codename)
    }

    @Test
    fun shouldDeleteFromAsteroidDatabase() {
        val asteroid = runBlocking {
            AsteroidDatabase.get(appContext).asteroidDao.delete(ASTERIX)

            return@runBlocking getAsteroid(ASTEROID_ID)
        }

        assertNull(asteroid)
    }


    @Test
    fun shouldDeleteAllEntriesBelowOctober2020() {
        val asteroids = runBlocking {
            AsteroidDatabase.get(appContext).asteroidDao.upsert(
                AsteroidEntity(
                    2L,
                    "2",
                    START_DATE,
                    ZERO, ZERO, ZERO, ZERO, false
                )
            )

            AsteroidDatabase.get(appContext).asteroidDao.deleteByDateBelow(OCTOBER_2020)

            return@runBlocking getAsteroidsIn(START_DATE, END_DATE)
        }

        assertEquals(1, asteroids.size)
        assertEquals(ASTERIX_ASTEROID_NAME, asteroids[0].codename)
    }

    private suspend fun getAsteroid(id: Long): Asteroid {
        return AsteroidDatabase.get(appContext).asteroidDao.getById(id)
    }

    private suspend fun getAsteroidsIn(startDate: String, endDate: String): List<AsteroidEntity> {
        return AsteroidDatabase.get(appContext)
            .asteroidDao
            .getByDateBetween(startDate, endDate)
    }

    companion object {
        private const val ASTEROID_ID = 1L
        private const val ASTERIX_ASTEROID_NAME = "asterix"
        private const val OBELIX_ASTEROID_NAME = "obelix"
        private const val CLOSE_APPROACH_DATE = "2020-10-10"
        private const val OCTOBER_2020 = "2020-10-01"
        private const val START_DATE = "2020-09-01"
        private const val END_DATE = "2020-10-31"
        private const val ZERO = 0.0

        val ASTERIX = AsteroidEntity(
            ASTEROID_ID,
            ASTERIX_ASTEROID_NAME,
            CLOSE_APPROACH_DATE,
            ZERO, ZERO, ZERO, ZERO, false
        )

        // Same id as ASTERIX; makes updates easier
        val OBELIX = AsteroidEntity(
            ASTEROID_ID,
            OBELIX_ASTEROID_NAME,
            CLOSE_APPROACH_DATE,
            ZERO, ZERO, ZERO, ZERO, false
        )
    }
}