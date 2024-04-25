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
}