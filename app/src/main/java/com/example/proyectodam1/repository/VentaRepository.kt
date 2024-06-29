package com.example.proyectodam1.repository

import com.example.proyectodam1.model.Venta
import com.example.proyectodam1.network.VentaDataSource

class VentaRepository(private val dataSource: VentaDataSource) {

    fun obtenerVentas(rs : (List<Venta>) -> Unit) {
        dataSource.obtenerVentas { v ->
            rs(v)
        }
    }

    fun agregarVenta(venta: Venta, rs : (Boolean) -> Unit) {
        dataSource.agregarVenta(venta) {
            rs(it)
        }
    }

    fun actualizarVenta(id : String, venta: Venta, rs: (Boolean) -> Unit) {
        dataSource.actualizarVenta(id, venta) {
            rs(it)
        }
    }
}