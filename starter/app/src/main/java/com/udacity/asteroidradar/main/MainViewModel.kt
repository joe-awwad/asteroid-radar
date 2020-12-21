package com.udacity.asteroidradar.main

import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidFilter
import com.udacity.asteroidradar.api.PictureOfDayStatus
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel(private val asteroidRepository: AsteroidRepository) : ViewModel() {

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _pictureOfDayStatus = MutableLiveData<PictureOfDayStatus>()
    val pictureOfDayStatus: LiveData<PictureOfDayStatus>
        get() = _pictureOfDayStatus

    /**
     * Asteroids
     */
    private var asteroidFilter = AsteroidFilter.SAVED

    val asteroids = MediatorLiveData<List<Asteroid>>()

    /**
     * Filtering implementation adapted from Fred Porciúncula's blog
     * MediatorLiveData to the Rescue on https://proandroiddev.com/
     *
     */
    init {
        asteroids.addSource(asteroidRepository.asteroids) {
            if (asteroidFilter == AsteroidFilter.SAVED) {
                asteroids.value = it
            }
        }

        asteroids.addSource(asteroidRepository.currentWeekAsteroids) {
            if (asteroidFilter == AsteroidFilter.CURRENT_WEEK) {
                asteroids.value = it
            }
        }

        asteroids.addSource(asteroidRepository.currentDayAsteroids) {
            if (asteroidFilter == AsteroidFilter.CURRENT_DAY) {
                asteroids.value = it
            }
        }

        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }

    }


    /**
     * Picture of the day
     */
    init {
        viewModelScope.launch {
            try {
                _pictureOfDayStatus.value = PictureOfDayStatus.LOADING

                _pictureOfDay.value = asteroidRepository.getPictureOfDay()

                _pictureOfDayStatus.value = PictureOfDayStatus.DONE

            } catch (e: Exception) {
                _pictureOfDayStatus.value = PictureOfDayStatus.ERROR

            }
        }
    }

    /**
     * Asteroid click event
     */
    private val _eventAsteroidClicked = MutableLiveData(false)
    val eventAsteroidClicked: LiveData<Boolean>
        get() = _eventAsteroidClicked

    fun asteroidClicked() {
        _eventAsteroidClicked.value = true
    }

    fun asteroidClickEventHandled() {
        _eventAsteroidClicked.value = false
    }

    fun updateFilter(filter: AsteroidFilter) {
        asteroidFilter = filter
        when (asteroidFilter) {
            AsteroidFilter.SAVED -> asteroidRepository.asteroids.let {
                asteroids.value = it.value
            }
            AsteroidFilter.CURRENT_WEEK -> asteroidRepository.currentWeekAsteroids.let {
                asteroids.value = it.value
            }
            AsteroidFilter.CURRENT_DAY -> asteroidRepository.currentDayAsteroids.let {
                asteroids.value = it.value
            }
        }
    }
}