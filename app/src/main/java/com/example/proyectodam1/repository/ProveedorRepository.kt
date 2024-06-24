package com.example.proyectodam1.repository

import com.example.proyectodam1.model.Proveedor
import com.example.proyectodam1.network.ProveedorDataSource

class ProveedorRepository(private val dataSource: ProveedorDataSource) {

    fun obtenerProveedores(rs : (List<Proveedor>) -> Unit) {
        dataSource.obtenerProveedores {
            rs(it)
        }
    }

    fun agregarProveedores(proveedor: Proveedor, rs : (Boolean) -> Unit) {
        dataSource.agregarProveedores(proveedor) {
            rs(it)
        }
    }

    fun actualizarProveedores(id : String, proveedor: Proveedor, rs: (Boolean) -> Unit) {
        dataSource.actualizarProveedores(id, proveedor) {
            rs(it)
        }
    }

    fun obtenerProveedorxId(id : String, rs: (proveedor : Proveedor?) -> Unit) {
        dataSource.obtenerProveedorxId(id) {
            rs(it)
        }
    }
}