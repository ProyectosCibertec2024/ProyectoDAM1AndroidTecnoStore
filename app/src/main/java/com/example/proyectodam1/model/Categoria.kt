package com.example.proyectodam1.model

import java.io.Serializable

class Categoria(
    var id : String,
    val nomcategoria : String
) : Serializable
{
    constructor() : this("","")
}