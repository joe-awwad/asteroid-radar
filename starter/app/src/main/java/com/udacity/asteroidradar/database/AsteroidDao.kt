package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(vararg asteroids: AsteroidEntity)

    @Query("SELECT * FROM asteroid_table WHERE id = :id")
    suspend fun getById(id: Long): Asteroid

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate BETWEEN :startDate AND :endDate ORDER BY closeApproachDate ASC")
    suspend fun getByDateBetween(startDate: String, endDate: String): List<AsteroidEntity>

    @Query("SELECT * FROM asteroid_table")
    suspend fun getAll(): List<AsteroidEntity>

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate BETWEEN :startDate AND :endDate ORDER BY closeApproachDate ASC")
    fun getByDateBetweenAsLiveData(
        startDate: String,
        endDate: String
    ): LiveData<List<AsteroidEntity>>

    @Delete
    suspend fun delete(vararg asteroids: AsteroidEntity)

    @Query("DELETE FROM asteroid_table")
    suspend fun deleteAll()

    @Query("DELETE FROM asteroid_table WHERE closeApproachDate < :date")
    suspend fun deleteByDateBelow(date: String)
}