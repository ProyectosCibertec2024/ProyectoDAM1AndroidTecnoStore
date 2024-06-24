package com.example.proyectodam1.network

import android.util.Log
import com.example.proyectodam1.model.Cliente
import com.google.firebase.firestore.FirebaseFirestore

class ClienteDataSource(private val db : FirebaseFirestore) {
    private val coleccion = "Cliente"

    fun obtenerClientes(clientes : (List<Cliente>) -> Unit) {
        db.collection(coleccion).get()
            .addOnSuccessListener { query ->
                val lista = mutableListOf<Cliente>()
                for (l in query) {
                    val cliente = l.toObject(Cliente::class.java)
                    cliente.id = l.id
                    lista.add(cliente)
                }
                clientes(lista)
            }
            .addOnFailureListener {
                clientes(emptyList())
                Log.e("ERROR CLIENTES LISTADO", "ERROR LISTADO: ${it.localizedMessage}")
            }
    }

    fun agregarClientes(cliente: Cliente, rs : (Boolean) -> Unit) {
        db.collection(coleccion).add(cliente)
            .addOnSuccessListener { doc ->
                rs(true)
            }
            .addOnFailureListener {
                rs(false)
                Log.e("ERROR CLIENTES REGISTRAR", "ERROR REGISTRAR: ${it.localizedMessage}")
            }
    }

    fun actualizarCliente(id : String ,cliente: Cliente, rs : (Boolean) -> Unit) {
        db.collection(coleccion).document(id)
            .set(cliente)
            .addOnSuccessListener {
                rs(true)
            }
            .addOnFailureListener {
                rs(false)
                Log.e("ERROR CLIENTES ACTUALIZAR", "ERROR ACTUALIZAR: ${it.localizedMessage}")
            }
    }

    fun eliminarCliente(id : String, rs: (Boolean) -> Unit) {
        db.collection(coleccion)
            .document(id)
            .delete()
            .addOnSuccessListener {
                rs(true)
            }
            .addOnFailureListener {
                rs(false)
            }
    }
}