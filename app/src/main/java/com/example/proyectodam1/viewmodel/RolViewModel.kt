package com.example.proyectodam1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.proyectodam1.model.Rol
import com.example.proyectodam1.repository.RolRepository

class RolViewModel(private val repository: RolRepository) : ViewModel() {

    fun obtenerRoles(rs : (List<Rol>) -> Unit) {
        repository.obtenerRoles {
            rs(it)
        }
    }

    fun agregarRol(rol : Rol, rs : (Boolean) -> Unit) {
        repository.agregarRol(rol) {
            rs(it)
        }
    }

    fun actualizarRol(id : String, rol: Rol, rs: (Boolean) -> Unit) {
        repository.actualizarRol(id, rol) {
            rs(it)
        }
    }

}