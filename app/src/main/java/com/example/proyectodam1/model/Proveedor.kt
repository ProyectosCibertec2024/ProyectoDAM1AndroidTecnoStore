package com.example.proyectodam1.model

import java.io.Serializable

class Proveedor(
    var id : String,
    val nomprov : String ? = null,
    val apeprov : String ? = null,
    val email : String ? = null,
    val telefono : String ? = null
) : Serializable {
    constructor() : this("","","","","")
}