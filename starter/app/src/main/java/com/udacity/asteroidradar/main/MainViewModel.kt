package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel(asteroidRepository: AsteroidRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }
    }

    val asteroids = asteroidRepository.asteroids

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