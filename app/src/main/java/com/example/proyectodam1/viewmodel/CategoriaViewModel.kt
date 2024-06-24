package com.example.proyectodam1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.proyectodam1.model.Categoria
import com.example.proyectodam1.repository.CategoriaRepository

class CategoriaViewModel(private val repository: CategoriaRepository) : ViewModel() {

    fun obtenerCategoria(rs : (List<Categoria>) -> Unit) {
        repository.listarCategoria {
            rs(it)
        }
    }

    fun agregarCategoria(categoria: Categoria,  rs: (Boolean) -> Unit) {
        repository.agregarCategoria(categoria) {
            rs(it)
        }
    }

    fun actualizarCategoria(id : String, categoria: Categoria, rs: (Boolean) -> Unit) {
        repository.actualizarCategoria(id, categoria) {
            rs(it)
        }
    }

    fun eliminarCategoria(id : String, rs: (Boolean) -> Unit) {
        repository.eliminarCategoria(id) {
            rs(it)
        }
    }

    fun obtenerCategoriaxId(id : String, rs: (categoria : Categoria?) -> Unit) {
        repository.obtenerCategoriaxId(id) { cat ->
            rs(cat)
        }
    }
}