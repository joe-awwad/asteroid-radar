package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.PictureOfDayStatus
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel(asteroidRepository: AsteroidRepository) : ViewModel() {

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _pictureOfDayStatus = MutableLiveData<PictureOfDayStatus>()
    val pictureOfDayStatus: LiveData<PictureOfDayStatus>
        get() = _pictureOfDayStatus

    init {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }

    }

    val asteroids = asteroidRepository.asteroids

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

    private val _eventAsteroidClicked = MutableLiveData(false)
    val eventAsteroidClicked: LiveData<Boolean>
        get() = _eventAsteroidClicked

    fun asteroidClicked() {
        _eventAsteroidClicked.value = true
    }

    fun asteroidClickEventHandled() {
        _eventAsteroidClicked.value = false
    }
}