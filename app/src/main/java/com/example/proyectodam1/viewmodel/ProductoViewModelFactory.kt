package com.example.proyectodam1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectodam1.repository.ProductoRepository

class ProductoViewModelFactory(private val repository: ProductoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductoViewModel::class.java)) {
            return ProductoViewModel(repository) as T
        }
        throw IllegalArgumentException("not fund view model")
    }
}