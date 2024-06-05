package com.example.proyectodam1.model

import java.io.Serializable

class Usuario (
        var id : String,
        val nombre : String ? = null,
        val apellido : String ? = null,
        val email : String ? = null,
        val password : String ? = null,
        val rol : String ? = null,
        val nomimg : String ? = null,
        val urlimg : String ? = null
):Serializable{
    constructor():this("","","","","","","","")
}