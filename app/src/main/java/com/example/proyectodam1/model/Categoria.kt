package com.example.proyectodam1.model

import java.io.Serializable

class Categoria(
    var id : String ? = null,
    val nomcategoria : String
) : Serializable {
    constructor() : this("","")
}