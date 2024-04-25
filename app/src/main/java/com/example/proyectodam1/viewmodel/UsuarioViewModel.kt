package com.example.proyectodam1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.proyectodam1.model.Usuario
import com.example.proyectodam1.repository.UsuarioRepository

class UsuarioViewModel (private val repository: UsuarioRepository):ViewModel() {
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
}