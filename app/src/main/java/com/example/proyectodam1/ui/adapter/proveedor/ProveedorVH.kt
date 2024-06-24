package com.example.proyectodam1.ui.adapter.proveedor

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.proyectodam1.databinding.ItemProveedorBinding
import com.example.proyectodam1.model.Proveedor

class ProveedorVH(private val binding : ItemProveedorBinding) : ViewHolder(binding.root) {
    fun agregarItems(proveedor: Proveedor) {
        binding.lblNombreProveedor.text = proveedor.nomprov
        binding.lblApellidoProveedor.text = proveedor.apeprov
    }
}