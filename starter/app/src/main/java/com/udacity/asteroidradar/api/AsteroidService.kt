package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.asDomainModels
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

enum class PictureOfDayStatus {
    LOADING, DONE, ERROR
}

interface AsteroidService {

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): AsteroidContainer

}


interface PictureOfDayService {
    @GET("planetary/apod")
    suspend fun get(@Query("api_key") apiKey: String): PictureOfDay
}

fun AsteroidContainer.all(): List<Asteroid> {
    return asteroids.values.flatMap { it.asDomainModels() }
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(Constants.BASE_URL)
    .build()

object AsteroidRadarApi {
    val asteroids: AsteroidService = retrofit.create(AsteroidService::class.java)

    val pictureOfOfDay: PictureOfDayService = retrofit.create(PictureOfDayService::class.java)
}
