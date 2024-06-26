package com.example.proyectodam1.network

import android.util.Log
import com.example.proyectodam1.model.Rol
import com.google.firebase.firestore.FirebaseFirestore

class RolDataSource(private val db : FirebaseFirestore) {

    private val coleccion = "Rol"

    fun obtenerRoles(rs : (List<Rol>) -> Unit) {
        db.collection(coleccion).get()
            .addOnSuccessListener { q ->
                val lista = mutableListOf<Rol>()
                for (l in q) {
                    var objrol = l.toObject(Rol::class.java)
                    objrol.id = l.id
                    lista.add(objrol)
                }
                rs(lista)
            }
            .addOnFailureListener {
                Log.e("Error en :", "Obtener Rol : ${it.localizedMessage}")
                rs(emptyList())
            }
    }
    fun agregarRol(rol : Rol, rs : (Boolean) -> Unit) {
        db.collection(coleccion).add(rol)
            .addOnSuccessListener {
                rs(true)
            }
            .addOnFailureListener {
                Log.e("Error en :", "Registrar Rol : ${it.localizedMessage}")
                rs(false)
            }
    }

    fun actualizarRol(id : String, rol: Rol, rs: (Boolean) -> Unit) {
        db.collection(coleccion).document(id).set(rol)
            .addOnSuccessListener {
                rs(true)
            }
            .addOnFailureListener {
                Log.e("Error en :", "Modificar Rol : ${it.localizedMessage}")
                rs(false)
            }
    }

    fun obtenerRolxIdrol(id : String, rs: (Rol?) -> Unit) {
        db.collection(coleccion).document(id)
            .get()
            .addOnSuccessListener { doc ->
                if(doc.exists()) {
                    val objrol = doc.toObject(Rol::class.java)
                    rs(objrol)
                }
            }
            .addOnFailureListener {
                Log.e("Error en :", "Modificar Rol : ${it.localizedMessage}")
                rs(null)
            }
    }
}