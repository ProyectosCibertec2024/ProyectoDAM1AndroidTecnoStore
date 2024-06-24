package com.example.proyectodam1.network

import android.util.Log
import com.example.proyectodam1.model.Categoria
import com.google.firebase.firestore.FirebaseFirestore

class CategoriaDataSource(private val db : FirebaseFirestore) {

    private val coleccion = "Categoria"

    fun obtenerCategorias(categoria : (List<Categoria>) -> Unit) {
        db.collection(coleccion).get()
            .addOnSuccessListener { query ->
                val lista = mutableListOf<Categoria>()
                for (l in query) {
                    val objCategoria = l.toObject(Categoria::class.java)
                    objCategoria.id = l.id
                    lista.add(objCategoria)
                }
                categoria(lista)
            }
            .addOnFailureListener {
                Log.e("Error","CATEGORIA LISTAR : ${it.localizedMessage}")
            }
    }

    fun agregarCategoria(categoria: Categoria, rs : (Boolean) -> Unit) {
        db.collection(coleccion).add(categoria)
            .addOnSuccessListener {
                rs(true)
            }
            .addOnFailureListener {
                rs(false)
                Log.e("Error","CATEGORIA AGREGAR : ${it.localizedMessage}")
            }
    }

    fun actualizarCategoria(id : String, categoria: Categoria, rs: (Boolean) -> Unit) {
        db.collection(coleccion).document(id)
            .set(categoria)
            .addOnSuccessListener {
                rs(true)
            }
            .addOnFailureListener {
                rs(false)
                Log.e("Error","CATEGORIA UPDATE : ${it.localizedMessage}")
            }
    }

    fun eliminarCategoria(id : String, rs: (Boolean) -> Unit) {
        db.collection(coleccion)
            .document(id)
            .delete()
            .addOnSuccessListener {
                rs(true)
            }
            .addOnFailureListener {
                rs(false)
                Log.e("Error","CATEGORIA DELETE : ${it.localizedMessage}")
            }
    }

    fun obtenerCategoriaxId(id : String, rs: (categoria : Categoria?) -> Unit) {
        db.collection(coleccion).document(id).get()
            .addOnSuccessListener { doc ->
                if(doc.exists()) {
                    val categoria = doc.toObject(Categoria::class.java)
                    rs(categoria!!)
                }else {
                    rs(null)
                }
            }
            .addOnFailureListener {
                rs(null)
                Log.e("Error","CATEGORIA DELETE : ${it.localizedMessage}")
            }
    }
}