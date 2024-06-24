package com.example.proyectodam1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectodam1.repository.ProveedorRepository

class ProveedorViewModelFactory(private val repository: ProveedorRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProveedorViewModel::class.java)) {
            return ProveedorViewModel(repository) as T
        }
        throw IllegalArgumentException("not fund view model");
    }
}