package com.example.proyectodam1.network

import android.util.Log
import com.example.proyectodam1.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class UsuarioDataSource(private val db:FirebaseFirestore) {

    private val colleccion = "Usuario"
    fun loguinUsuario(email: String, password:String,rs:(Boolean)-> Unit){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email , password)
            .addOnCompleteListener { r->
                if(r.isSuccessful)  {
                    rs(true)
                }else{
                    rs(false)
                }
        }.addOnFailureListener {
            Log.e("Excepciòn : ", "Error en " + it.localizedMessage)
        }
    }

    fun obtenerRolUsuario(email : String,rs: (Usuario?) -> Unit){
       db.collection(colleccion)
           .whereEqualTo("email",email).get()
           .addOnSuccessListener {
               if(!it.isEmpty){
                    var usuario = it.documents[0].toObject(Usuario::class.java)
                    rs(usuario)
               }else{
                   Log.e("Error","No se encontro el rol")
               }
           }.addOnFailureListener {
               Log.e("Excepciòn : ", "Error en " + it.localizedMessage)
               rs(null)
           }
    }

    fun obtenerUsuario(rs : (List<Usuario>) -> Unit) {
        db.collection(colleccion).get()
            .addOnSuccessListener { q ->
                val lista = mutableListOf<Usuario>()
                for (l in q) {
                    var usuario = l.toObject(Usuario::class.java)
                    usuario.id = l.id
                    lista.add(usuario)
                }
                rs(lista)
            }.addOnFailureListener {
                Log.e("Error en obtener listado", "Listado ${it.localizedMessage}")
                rs(emptyList())
            }
    }

    fun agregarUsuario(usuario : Usuario, rs : (Boolean) -> Unit) {
        db.collection(colleccion).add(usuario)
            .addOnSuccessListener {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(usuario.email.toString(), usuario.password.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            rs(true)
                        }
                    }.addOnFailureListener {
                        Log.e("Excepción : ", "Error en crear usuario auth : " + it.localizedMessage)
                    }
            }
            .addOnFailureListener {
                Log.e("Excepción : ", "Error en Registrar Usuario : " + it.localizedMessage)
                rs(false)
            }
    }

    fun actualizarUsuario(id : String, usuario: Usuario, rs: (Boolean) -> Unit) {
        val usuarioAuth = FirebaseAuth.getInstance().currentUser
        db.collection(colleccion).add(usuario)
            .addOnSuccessListener {
                if(usuarioAuth != null) {
                    if(usuarioAuth.email != usuario.email) {
                        usuarioAuth.updateEmail(usuario.email.toString())
                            .addOnCompleteListener {
                                if(it.isSuccessful) {
                                    Log.d("Bien Auth", "Se Encontro el Email auth")
                                }else {
                                    Log.e("Actualizar Auth Usuario", "Usuario no autenticado")
                                    rs(false)
                                }
                            }
                    }

                    usuarioAuth.updatePassword(usuario.password.toString())
                        .addOnCompleteListener {
                            if(it.isSuccessful) {
                                Log.d("Password Auth", "Se encontro el password")
                                rs(true)
                            }else {
                                Log.d("Password Auth", "No Se encontro el password")
                                rs(false)
                            }
                        }
                        .addOnFailureListener {
                            Log.e("Actualizar Auth Usuario", "Usuario no autenticado")
                            rs(false)
                        }

                }else {
                    Log.e("ActualizarUsuario", "Usuario no autenticado")
                    rs(false)
                }
            }
            .addOnFailureListener {
                Log.e("Excepción : ", "Error en Modificar Usuario : " + it.localizedMessage)
                rs(false)
            }
    }

}