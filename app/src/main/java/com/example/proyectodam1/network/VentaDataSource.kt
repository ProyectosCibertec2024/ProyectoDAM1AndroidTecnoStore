package com.example.proyectodam1.network

import android.util.Log
import com.example.proyectodam1.model.Cliente
import com.example.proyectodam1.model.Producto
import com.example.proyectodam1.model.Usuario
import com.example.proyectodam1.model.Venta
import com.google.firebase.firestore.FirebaseFirestore

class VentaDataSource(private val db : FirebaseFirestore) {
    private val coleccion = "Venta"

    fun obtenerVentas(rs : (List<Venta>) -> Unit) {
        db.collection(coleccion).get()
            .addOnSuccessListener {
                val lista = mutableListOf<Venta>()
                for (l in it) {
                    val venta = l.toObject(Venta::class.java)
                    venta.id = l.id
                    lista.add(venta)
                }
                rs(lista)
            }
            .addOnFailureListener {
                Log.e("Error en : ","OBTENER VENTAS : ${it.localizedMessage}")
                rs(emptyList())
            }
    }

    fun agregarVenta(venta: Venta, rs : (Boolean) -> Unit) {
        db.collection(coleccion).add(venta)
            .addOnSuccessListener {
                rs(true)
            }
            .addOnFailureListener {
                Log.e("Error en : ","INSERTAR VENTAS : ${it.localizedMessage}")
                rs(false)
            }
    }

    fun actualizarVenta(id : String, venta: Venta, rs: (Boolean) -> Unit) {
        db.collection(coleccion).document(id).set(venta)
            .addOnSuccessListener {
                rs(true)
            }
            .addOnFailureListener {
                Log.e("Error en : ","ACTUALIZAR VENTAS : ${it.localizedMessage}")
                rs(false)
            }
    }

    fun buscarproductoxventa(venta: Venta, rs : (marca : String?, urlprod : String?) -> Unit) {
        venta.idproducto?.get()?.addOnSuccessListener { v ->
            if(v.exists()) {
                val producto = v.toObject(Producto::class.java)
                rs(producto?.marca,producto?.urlimg)
            }else {
                Log.e("Error", "No se encontró la venta para el producto")
                rs(null, null)
            }
        }?.addOnFailureListener {
            Log.e("Error", "No se encontró la venta para el producto: ${it.localizedMessage}")
            rs(null, null)
        }
    }

    fun buscarclientexventa(venta: Venta, rs : (cliente : String?) -> Unit) {
        venta.idcliente?.get()?.addOnSuccessListener { c ->
            if(c.exists()) {
                val cliente = c.toObject(Cliente::class.java)
                rs(cliente?.nomcli + " " + cliente?.apecli)
            }else {
                Log.e("Error", "No se encontró la venta para el cliente")
                rs(null)
            }
        }?.addOnFailureListener {
            Log.e("Error", "No se encontró la venta para el cliente: ${it.localizedMessage}")
            rs(null)
        }
    }

    fun buscarventaxusuario(venta: Venta, rs : (usuario : String?) -> Unit) {
        venta.idusuario?.get()?.addOnSuccessListener {
            if(it.exists()) {
                val usuario = it.toObject(Usuario::class.java)
                rs(usuario?.nombre + " " + usuario?.apellido)
            }else {
                Log.e("Error", "No se encontró la venta para el usuario")
                rs(null)
            }
        }?.addOnFailureListener {
            Log.e("Error", "No se encontró la venta para el usuario: ${it.localizedMessage}")
            rs(null)
        }
    }
}