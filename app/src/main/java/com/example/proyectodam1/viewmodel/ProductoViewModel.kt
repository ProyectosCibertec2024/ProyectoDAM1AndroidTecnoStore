package com.example.proyectodam1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.proyectodam1.model.Producto
import com.example.proyectodam1.repository.ProductoRepository

class ProductoViewModel(private val repository: ProductoRepository) : ViewModel() {
    fun obtenerProductos(rs : (List<Producto>) -> Unit) {
        repository.obtenerProductos {
            rs(it)
        }
    }

    fun agregarProductos(producto: Producto, rs : (Boolean) -> Unit) {
        repository.agregarProductos(producto) {
            rs(it)
        }
    }

    fun actualizarProductos(id : String, producto: Producto, rs: (Boolean) -> Unit) {
        repository.actualizarProductos(id, producto) {
            rs(it)
        }
    }

    fun eliminarProducto(id : String, rs: (Boolean) -> Unit) {
        repository.eliminarProducto(id) {
            rs(it)
        }
    }

    fun buscarProductoxId(id : String, rs : (producto : Producto?) -> Unit) {
        repository.buscarProductoxId(id) {
            rs(it)
        }
    }
}