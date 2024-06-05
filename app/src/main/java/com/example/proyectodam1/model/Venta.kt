package com.example.proyectodam1.model

import com.google.firebase.firestore.DocumentReference
import java.io.Serializable

class Venta (
    var id : String,
    val numventa : Int ? = null,
    val idcliente : DocumentReference? = null,
    val idusuario : DocumentReference ? = null,
    val igv : Int ? = null,
    val subtotal : Double ? = null,
    val total : Double ? = null,
    val fechareg : String ? = null
) : Serializable {
    constructor() : this("",0,null,null,0,0.0,0.0,"")
}