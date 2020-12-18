package com.udacity.asteroidradar.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AsteroidContainer(
    @Json(name = "near_earth_objects")
    val asteroids: Map<String, List<NetworkAsteroid>>
)

@JsonClass(generateAdapter = true)
data class NetworkAsteroid(
    val id: Long,

    @Json(name = "name")
    val codename: String,

    @Json(name = "absolute_magnitude_h")
    val absoluteMagnitude: Double,

    @Json(name = "estimated_diameter")
    val estimatedDiameter: EstimatedDiameter,

    @Json(name = "close_approach_data")
    val closeApproachData: List<CloseApproachData>,

    @Json(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardous: Boolean
)

@JsonClass(generateAdapter = true)
data class CloseApproachData(

    @Json(name = "close_approach_date")
    val closeApproachDate: String,

    @Json(name = "relative_velocity")
    val relativeVelocity: RelativeVelocity,

    @Json(name = "miss_distance")
    val missDistance: MissDistance
)

@JsonClass(generateAdapter = true)
data class RelativeVelocity(@Json(name = "kilometers_per_second") val kilometersPerSecond: Double)

@JsonClass(generateAdapter = true)
data class MissDistance(val astronomical: Double)

@JsonClass(generateAdapter = true)
data class EstimatedDiameter(@Json(name = "kilometers") val diameter: EstimatedDiameterMinMax)

@JsonClass(generateAdapter = true)
data class EstimatedDiameterMinMax(

    @Json(name = "estimated_diameter_min")
    val min: Double,

    @Json(name = "estimated_diameter_max")
    val max: Double
)