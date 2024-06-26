package com.example.proyectodam1.ui.adapter.rol

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.proyectodam1.databinding.ItemRolBinding
import com.example.proyectodam1.model.Rol

class RolVH(private val binding : ItemRolBinding) : ViewHolder(binding.root) {
    fun agregarItem(rol : Rol) {
        binding.lblNombre.text = rol.nomrol
    }
}