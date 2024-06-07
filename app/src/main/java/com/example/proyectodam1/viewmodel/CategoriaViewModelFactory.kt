package com.example.proyectodam1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectodam1.repository.CategoriaRepository

class CategoriaViewModelFactory(private val repository: CategoriaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CategoriaViewModel::class.java)) {
            return CategoriaViewModel(repository) as T
        }
        throw IllegalArgumentException("not fund view model")
    }
}