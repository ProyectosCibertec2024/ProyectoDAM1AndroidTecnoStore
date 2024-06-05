package com.example.proyectodam1.model

import com.google.firebase.firestore.DocumentReference
import java.io.Serializable

class Usuario (
        var id : String,
        val nombre : String ? = null,
        val apellido : String ? = null,
        val email : String ? = null,
        val password : String ? = null,
        val rol : DocumentReference ? = null,
        val nomimg : String ? = null,
        val urlimg : String ? = null
) : Serializable {
    constructor():this("","","","","",null,"","")
}