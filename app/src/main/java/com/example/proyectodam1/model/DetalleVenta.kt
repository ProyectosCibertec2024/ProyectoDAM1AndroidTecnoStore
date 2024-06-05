package com.example.proyectodam1.model

import com.google.firebase.firestore.DocumentReference
import java.io.Serializable

class DetalleVenta(
    var id : String,
    val idventa : DocumentReference ? = null,
    val idproducto : DocumentReference? = null,
    val cantidad : Int ? = null,
    val precio : Double ? = null,
    val total : Double ? = null
) : Serializable {
    constructor() : this("",null,null,0,0.0,0.0)
}