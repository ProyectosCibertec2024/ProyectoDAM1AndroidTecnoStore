package com.example.proyectodam1.network

import android.util.Log
import com.example.proyectodam1.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class UsuarioDataSource(private val db:FirebaseFirestore) {

    private val colleccion = "Usuario"
    fun loguinUsuario(email: String, password:String,rs:(Boolean)-> Unit){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email , password)
            .addOnCompleteListener { r->
                if(r.isSuccessful)  {
                    rs(true)
                }else{
                    rs(false)
                }
        }.addOnFailureListener {
            Log.e("Excepciòn : ", "Error en " + it.localizedMessage)
        }
    }

    fun obtenerRolUsuario(email : String,rs: (Usuario ?) -> Unit){
       db.collection(colleccion)
           .whereEqualTo("email",email).get()
           .addOnSuccessListener {
               if(!it.isEmpty){
                    var usuario = it.documents[0].toObject(Usuario::class.java)
                    rs(usuario)
               }else{
                   Log.e("Error","No se encontro el rol")
               }
           }.addOnFailureListener {
               Log.e("Excepciòn : ", "Error en " + it.localizedMessage)
           }
    }

}