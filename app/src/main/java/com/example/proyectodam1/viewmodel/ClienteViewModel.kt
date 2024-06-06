package com.example.proyectodam1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.proyectodam1.model.Cliente
import com.example.proyectodam1.repository.ClienteRepository

class ClienteViewModel(private val repository: ClienteRepository) : ViewModel() {

    fun obtenerClientes(clientes : (List<Cliente>) -> Unit) {
        repository.listadoClientes { list ->
            clientes(list)
        }
    }


}