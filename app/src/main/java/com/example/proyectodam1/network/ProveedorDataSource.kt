package com.example.proyectodam1.network

import android.util.Log
import com.example.proyectodam1.model.Proveedor
import com.google.firebase.firestore.FirebaseFirestore

class ProveedorDataSource(private val db : FirebaseFirestore) {
    private val coleccion = "Proveedor";

    fun obtenerProveedores(rs : (List<Proveedor>) -> Unit) {
        db.collection(coleccion).get()
            .addOnSuccessListener { query ->
                val lista = mutableListOf<Proveedor>()
                for (l in query) {
                    val objprov = l.toObject(Proveedor::class.java)
                    objprov.id = l.id
                    lista.add(objprov)
                }
                rs(lista)
            }
            .addOnFailureListener {
                Log.e("Exception", "Error en obt prov : ${it.localizedMessage}" )
                rs(emptyList())
            }
    }

    fun agregarProveedores(proveedor: Proveedor, rs : (Boolean) -> Unit) {
        db.collection(coleccion).add(proveedor)
            .addOnSuccessListener {
                rs(true)
            }
            .addOnFailureListener {
                Log.e("Exception", "Error en obt prov : ${it.localizedMessage}" )
                rs(false)
            }
    }

    fun actualizarProveedores(id : String, proveedor: Proveedor, rs: (Boolean) -> Unit) {
        db.collection(coleccion).document(id)
            .set(proveedor)
            .addOnSuccessListener {
                rs(true)
            }
            .addOnFailureListener {
                Log.e("Exception", "Error en obt prov : ${it.localizedMessage}" )
                rs(false)
            }
    }

    fun obtenerProveedorxId(id : String, rs: (proveedor : Proveedor?) -> Unit) {
        db.collection(coleccion).document(id).get()
            .addOnSuccessListener {
                if(it.exists()) {
                    val proveedor = it.toObject(Proveedor::class.java)
                    rs(proveedor)
                }else {
                    rs(null)
                }
            }
            .addOnFailureListener {
                Log.e("Exception", "Error en obt prov : ${it.localizedMessage}" )
                rs(null)
            }
    }

}