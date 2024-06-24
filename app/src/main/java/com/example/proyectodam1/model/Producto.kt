package com.example.proyectodam1.model

import com.google.firebase.firestore.DocumentReference
import java.io.Serializable

class Producto(
    var id : String,
    val idcategoria : DocumentReference? = null,
    val idproveedor : DocumentReference ? = null,
    val marca : String,
    val nombreimg : String,
    val fechareg : String,
    val precio : Double,
    val stock : Int,
    val urlimg : String
) : Serializable {
    constructor() : this("",null,null,"","","",0.0,0,"")
}