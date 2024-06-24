package com.example.proyectodam1.repository

import com.example.proyectodam1.model.Categoria
import com.example.proyectodam1.network.CategoriaDataSource

class CategoriaRepository(private val dataSource: CategoriaDataSource) {

    fun listarCategoria(rs : (List<Categoria>) -> Unit) {
        dataSource.obtenerCategorias { lst ->
            rs(lst)
        }
    }

    fun agregarCategoria(categoria: Categoria,  rs: (Boolean) -> Unit) {
        dataSource.agregarCategoria(categoria) {
            rs(it)
        }
    }

    fun actualizarCategoria(id : String, categoria: Categoria, rs: (Boolean) -> Unit) {
        dataSource.actualizarCategoria(id, categoria) {
            rs(it)
        }
    }

    fun eliminarCategoria(id : String, rs: (Boolean) -> Unit) {
        dataSource.eliminarCategoria(id) {
            rs(it)
        }
    }

    fun obtenerCategoriaxId(id : String, rs: (categoria : Categoria?) -> Unit) {
        dataSource.obtenerCategoriaxId(id) { cat ->
            rs(cat)
        }
    }
}