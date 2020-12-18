package com.udacity.asteroidradar

import com.udacity.asteroidradar.api.NetworkAsteroid
import com.udacity.asteroidradar.database.AsteroidEntity

fun List<AsteroidEntity>.asDomainModels(): List<Asteroid> {
    return map {
        it.asDomainModel()
    }
}

fun List<Asteroid>.asDatabaseModels(): List<AsteroidEntity> {
    return map {
        it.asDatabaseModel()
    }
}

@JvmName("asDomainModelsNetworkAsteroid")
fun List<NetworkAsteroid>.asDomainModels(): List<Asteroid> {
    return map {
        it.asDomainModel()
    }
}

@JvmName("asDatabaseModelsNetworkAsteroid")
fun List<NetworkAsteroid>.asDatabaseModels(): List<AsteroidEntity> {
    return map {
        it.asDatabaseModel()
    }
}

fun AsteroidEntity.asDomainModel(): Asteroid {
    return Asteroid(
        id = id,
        codename = codename,
        closeApproachDate = closeApproachDate,
        absoluteMagnitude = absoluteMagnitude,
        estimatedDiameter = estimatedDiameter,
        relativeVelocity = relativeVelocity,
        distanceFromEarth = distanceFromEarth,
        isPotentiallyHazardous = isPotentiallyHazardous
    )
}

fun Asteroid.asDatabaseModel(): AsteroidEntity {
    return AsteroidEntity(
        id = id,
        codename = codename,
        closeApproachDate = closeApproachDate,
        absoluteMagnitude = absoluteMagnitude,
        estimatedDiameter = estimatedDiameter,
        relativeVelocity = relativeVelocity,
        distanceFromEarth = distanceFromEarth,
        isPotentiallyHazardous = isPotentiallyHazardous
    )
}

fun NetworkAsteroid.asDomainModel(): Asteroid {
    return Asteroid(
        id = id,
        codename = codename,
        closeApproachDate = closeApproachData[0].closeApproachDate,
        absoluteMagnitude = absoluteMagnitude,
        estimatedDiameter = estimatedDiameter.diameter.max,
        relativeVelocity = closeApproachData[0].relativeVelocity.kilometersPerSecond,
        distanceFromEarth = closeApproachData[0].missDistance.astronomical,
        isPotentiallyHazardous = isPotentiallyHazardous
    )
}

fun NetworkAsteroid.asDatabaseModel(): AsteroidEntity {
    return AsteroidEntity(
        id = id,
        codename = codename,
        closeApproachDate = closeApproachData[0].closeApproachDate,
        absoluteMagnitude = absoluteMagnitude,
        estimatedDiameter = estimatedDiameter.diameter.max,
        relativeVelocity = closeApproachData[0].relativeVelocity.kilometersPerSecond,
        distanceFromEarth = closeApproachData[0].missDistance.astronomical,
        isPotentiallyHazardous = isPotentiallyHazardous
    )
}