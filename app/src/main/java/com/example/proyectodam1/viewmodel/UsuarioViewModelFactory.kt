package com.example.proyectodam1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectodam1.repository.UsuarioRepository
import java.lang.IllegalArgumentException

class UsuarioViewModelFactory (private val repository: UsuarioRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UsuarioViewModel::class.java)){
            return UsuarioViewModel(repository)as T
        }
        throw IllegalArgumentException("No se obtuvo el ViewModel")

    }
}