package com.example.proyectodam1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectodam1.repository.VentaRepository

class VentaViewModelFactory(private val repository : VentaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(VentaViewModel::class.java)) {
            return VentaViewModel(repository) as T
        }
        throw IllegalArgumentException("not fund view model")
    }
}