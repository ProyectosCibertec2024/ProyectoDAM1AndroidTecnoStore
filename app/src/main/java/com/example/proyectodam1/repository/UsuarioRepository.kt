package com.example.proyectodam1.repository

import com.example.proyectodam1.model.Usuario
import com.example.proyectodam1.network.UsuarioDataSource

class UsuarioRepository(private val dataSource: UsuarioDataSource) {
    fun loguinUsuario(email: String, password:String,rs:(Boolean)-> Unit){
        dataSource.loguinUsuario(email, password){
            rs(it)
        }
    }
    fun obtenerRolUsuario(email : String,rs: (Usuario?) -> Unit) {
        dataSource.obtenerRolUsuario(email) {
            rs(it)
        }
    }

    fun obtenerUsuario(rs : (List<Usuario>) -> Unit) {
        dataSource.obtenerUsuario {
            rs(it)
        }
    }

    fun agregarUsuario(usuario : Usuario, rs : (Boolean) -> Unit) {
        dataSource.agregarUsuario(usuario) {
            rs(it)
        }
    }

    fun actualizarUsuario(id : String, usuario: Usuario, rs: (Boolean) -> Unit) {
        dataSource.actualizarUsuario(id, usuario) {
            rs(it)
        }
    }
}