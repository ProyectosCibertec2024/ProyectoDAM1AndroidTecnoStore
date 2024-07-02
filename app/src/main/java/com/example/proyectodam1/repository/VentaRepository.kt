package com.example.proyectodam1.repository

import com.example.proyectodam1.model.Venta
import com.example.proyectodam1.network.VentaDataSource
import com.google.firebase.Timestamp

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

    fun buscarVentaxFecha(fecha1 : Timestamp, fecha2: Timestamp, rs: (List<Venta>) -> Unit) {
        dataSource.buscarVentaxFecha(fecha1, fecha2) {
            rs(it)
        }
    }
}