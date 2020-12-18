package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Database(entities = [AsteroidEntity::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {

    abstract val asteroidDao: AsteroidDao

    companion object {
        private lateinit var INSTANCE: AsteroidDatabase

        fun get(context: Context): AsteroidDatabase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AsteroidDatabase::class.java,
                        "asteroid-database"
                    )
                        .build()
                }
                return INSTANCE
            }
        }
    }
}


