package com.example.proyectodam1.repository

import com.example.proyectodam1.model.Rol
import com.example.proyectodam1.network.RolDataSource

class RolRepository(private val dataSource: RolDataSource) {
    fun obtenerRoles(rs : (List<Rol>) -> Unit) {
        dataSource.obtenerRoles {
            rs(it)
        }
    }

    fun agregarRol(rol : Rol, rs : (Boolean) -> Unit) {
        dataSource.agregarRol(rol) {
            rs(it)
        }
    }

    fun actualizarRol(id : String, rol: Rol, rs: (Boolean) -> Unit) {
        dataSource.actualizarRol(id, rol) {
            rs(it)
        }
    }
}