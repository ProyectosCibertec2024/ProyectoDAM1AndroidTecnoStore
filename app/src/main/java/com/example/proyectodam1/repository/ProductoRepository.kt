package com.example.proyectodam1.repository

import com.example.proyectodam1.model.Producto
import com.example.proyectodam1.network.ProductoDataSource

class ProductoRepository(private val dataSource: ProductoDataSource) {

    fun obtenerProductos(rs : (List<Producto>) -> Unit) {
        dataSource.obtenerProductos {
            rs(it)
        }
    }

    fun agregarProductos(producto: Producto, rs : (Boolean) -> Unit) {
        dataSource.agregarProductos(producto) {
            rs(it)
        }
    }

    fun actualizarProductos(id : String, producto: Producto, rs: (Boolean) -> Unit) {
        dataSource.actualizarProductos(id, producto) {
            rs(it)
        }
    }

    fun eliminarProducto(id : String, rs: (Boolean) -> Unit) {
        dataSource.eliminarProducto(id) {
            rs(it)
        }
    }

    fun buscarCategoriaenProducto(producto : Producto, nomcategoria : (String?) -> Unit) {
        dataSource.buscarCategoriaenProducto(producto) {
            nomcategoria(it)
        }
    }

    fun buscarProductoxId(id : String, rs : (producto : Producto?) -> Unit) {
        dataSource.buscarProductoxId(id) {
            rs(it)
        }
    }
}