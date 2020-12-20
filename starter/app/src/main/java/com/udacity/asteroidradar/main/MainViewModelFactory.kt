package com.udacity.asteroidradar.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.repository.AsteroidRepository

class MainViewModelFactory(private val asteroidRepository: AsteroidRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(asteroidRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class ${modelClass.canonicalName}")
    }
}