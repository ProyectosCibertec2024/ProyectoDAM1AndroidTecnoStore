package com.example.proyectodam1.ui.adapter.usuario

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.proyectodam1.databinding.ItemUsuarioBinding
import com.example.proyectodam1.model.Usuario

class UsuarioVH(private val binding : ItemUsuarioBinding) : ViewHolder(binding.root) {
    fun agregarItem(usuario: Usuario) {
        binding.lblNombreUsuario.text = usuario.nombre
        binding.lblApellidoUsuario.text = usuario.apellido
    }
}