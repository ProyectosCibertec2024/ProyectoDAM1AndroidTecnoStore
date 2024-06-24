package com.example.proyectodam1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectodam1.repository.RolRepository

class RolViewModelFactory(private val repository: RolRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RolViewModelFactory::class.java)) {
            return RolViewModel(repository) as T
        }
        throw IllegalArgumentException("view model not fund")
    }
}