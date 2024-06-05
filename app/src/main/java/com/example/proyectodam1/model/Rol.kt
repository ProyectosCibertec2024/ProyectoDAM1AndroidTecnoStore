package com.example.proyectodam1.model

import java.io.Serializable

class Rol(
    var id : String,
    var nomrol : String ? = null
) : Serializable {
    constructor() : this("","")
}