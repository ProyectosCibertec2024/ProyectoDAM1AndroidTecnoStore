package com.example.proyectodam1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.proyectodam1.model.Usuario
import com.example.proyectodam1.repository.UsuarioRepository

class UsuarioViewModel (private val repository: UsuarioRepository) : ViewModel() {
    fun logueoUsuario(email: String, password:String,rs:(Boolean)-> Unit){
        repository.loguinUsuario(email, password){
            rs(it)
        }
    }
    fun obtenerRolUsuario(email : String,rs: (Usuario?) -> Unit) {
       repository.obtenerRolUsuario(email) {
            rs(it)
        }
    }

    fun obtenerUsuario(rs : (List<Usuario>) -> Unit) {
        repository.obtenerUsuario {
            rs(it)
        }
    }

    fun agregarUsuario(usuario : Usuario, rs : (Boolean) -> Unit) {
        repository.agregarUsuario(usuario) {
            rs(it)
        }
    }

    fun actualizarUsuario(id : String, usuario: Usuario, rs: (Boolean) -> Unit) {
        repository.actualizarUsuario(id, usuario) {
            rs(it)
        }
    }

    fun obtenerUsuarioxId(id : String, rs : (Usuario?) -> Unit) {
        repository.obtenerUsuarioxId(id) {
            rs(it)
        }
    }
}