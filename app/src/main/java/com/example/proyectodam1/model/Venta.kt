package com.example.proyectodam1.model

import com.google.firebase.firestore.DocumentReference
import java.io.Serializable

class Venta (
    var id : String,
    val idcliente : DocumentReference? = null,
    val idusuario : DocumentReference ? = null,
    val idproducto : DocumentReference ? = null,
    val cantidad : Int ? = null,
    val precio : Double ? = null,
    val igv : Double ? = null,
    val subtotal : Double ? = null,
    val total : Double ? = null,
    val fechareg : String ? = null
) : Serializable {
    constructor() : this("",null,null,null,0,0.0,0.0,0.0,0.0,"")
}