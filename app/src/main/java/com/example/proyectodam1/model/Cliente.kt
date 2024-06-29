package com.example.proyectodam1.model

import java.io.Serializable

class Cliente (
    var id : String ? = null,
    val nomcli : String ? = null,
    val apecli : String ? = null,
    val dnicliente : String,
    val direccion : String,
    val telefono : String
) : Serializable {
    constructor() : this("","","","","","")
}