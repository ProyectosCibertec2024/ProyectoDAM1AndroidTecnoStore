package com.example.proyectodam1.network

import android.util.Log
import com.example.proyectodam1.model.Categoria
import com.example.proyectodam1.model.Producto
import com.google.firebase.firestore.FirebaseFirestore

class ProductoDataSource(private val db : FirebaseFirestore) {
    private val coleccion = "Producto"
    fun obtenerProductos(rs : (List<Producto>) -> Unit) {
        db.collection(coleccion).get()
            .addOnSuccessListener { q ->
                var lista = mutableListOf<Producto>()
                for (l in q) {
                    var objProducto = l.toObject(Producto::class.java)
                    objProducto.id = l.id
                    lista.add(objProducto)
                }
                rs(lista)
            }
            .addOnFailureListener {
                rs(emptyList())
                Log.e("ERROR", "ERROR LISTAR PRODUCTO ${it.localizedMessage}")
            }
    }

    fun buscarCategoriaenProducto(producto: Producto, nomcategoria: (String?) -> Unit) {
        producto.idcategoria?.get()?.addOnSuccessListener { categoriadoc ->
            if (categoriadoc.exists()) {
                val categoria = categoriadoc.toObject(Categoria::class.java)
                nomcategoria(categoria?.nomcategoria)
            } else {
                nomcategoria(null)
                Log.e("Error", "No se encontró la categoría para el producto")
            }
        }?.addOnFailureListener {
            nomcategoria(null)
            Log.e("Error", "Error al buscar categoría para el producto: ${it.localizedMessage}")
        }
    }

    fun agregarProductos(producto: Producto, rs : (Boolean) -> Unit) {
        db.collection(coleccion).add(producto)
            .addOnSuccessListener {
                rs(true)
            }
            .addOnFailureListener {
                rs(false)
                Log.e("ERROR", "ERROR AGREGAR PRODUCTO ${it.localizedMessage}")
            }
    }

    fun actualizarProductos(id : String, producto: Producto, rs: (Boolean) -> Unit) {
        db.collection(coleccion).document(id)
            .set(producto)
            .addOnSuccessListener {
                rs(true)
            }
            .addOnFailureListener {
                rs(false)
                Log.e("ERROR", "ERROR UPDATE PRODUCTO ${it.localizedMessage}")
            }
    }

    fun eliminarProducto(id : String, rs: (Boolean) -> Unit) {
        db.collection(coleccion).document(id)
            .delete()
            .addOnSuccessListener {
                rs(true)
            }
            .addOnFailureListener {
                rs(false)
                Log.e("ERROR", "ERROR DELETE PRODUCTO ${it.localizedMessage}")
            }
    }

    fun buscarProductoxId(id : String, rs : (producto : Producto?) -> Unit) {
        db.collection(coleccion).document(id).get()
            .addOnSuccessListener {
                if(it.exists()) {
                    val producto = it.toObject(Producto::class.java)
                    rs(producto)
                }
            }
            .addOnFailureListener{
                Log.e("Error" , "EXCEPTION BUSCAR PRODUCTO ID : ${it.localizedMessage}")
            }
    }
}