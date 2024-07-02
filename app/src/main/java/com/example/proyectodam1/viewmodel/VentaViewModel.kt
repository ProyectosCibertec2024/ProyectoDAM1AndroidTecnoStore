package com.example.proyectodam1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.proyectodam1.model.Venta
import com.example.proyectodam1.repository.VentaRepository
import com.google.firebase.Timestamp

class VentaViewModel(private val repository : VentaRepository) : ViewModel() {
    fun obtenerVentas(rs : (List<Venta>) -> Unit) {
        repository.obtenerVentas { v ->
            rs(v)
        }
    }

    fun agregarVenta(venta: Venta, rs : (Boolean) -> Unit) {
        repository.agregarVenta(venta) {
            rs(it)
        }
    }

    fun actualizarVenta(id : String, venta: Venta, rs: (Boolean) -> Unit) {
        repository.actualizarVenta(id, venta) {
            rs(it)
        }
    }

    fun buscarVentaxFecha(fecha1 : Timestamp, fecha2: Timestamp, rs: (List<Venta>) -> Unit) {
        repository.buscarVentaxFecha(fecha1, fecha2) {
            rs(it)
        }
    }
}