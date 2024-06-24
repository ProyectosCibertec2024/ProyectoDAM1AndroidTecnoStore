package com.example.proyectodam1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.proyectodam1.model.Proveedor
import com.example.proyectodam1.repository.ProveedorRepository

class ProveedorViewModel(private val repository: ProveedorRepository) : ViewModel() {
    fun obtenerProveedores(rs : (List<Proveedor>) -> Unit) {
        repository.obtenerProveedores {
            rs(it)
        }
    }

    fun agregarProveedores(proveedor: Proveedor, rs : (Boolean) -> Unit) {
        repository.agregarProveedores(proveedor) {
            rs(it)
        }
    }

    fun actualizarProveedores(id : String, proveedor: Proveedor, rs: (Boolean) -> Unit) {
        repository.actualizarProveedores(id, proveedor) {
            rs(it)
        }
    }

    fun obtenerProveedorxId(id : String, rs: (proveedor : Proveedor?) -> Unit) {
        repository.obtenerProveedorxId(id) {
            rs(it)
        }
    }
}