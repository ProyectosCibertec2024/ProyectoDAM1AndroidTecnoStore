package com.example.proyectodam1.model

import java.io.Serializable

class Cliente (
    var id : String,
    val nomcli : String,
    val apecli : String,
    val dnicliente : String,
    val direccion : String,
    val telefono : String
) : Serializable {
    constructor() : this("","","","","","")
}