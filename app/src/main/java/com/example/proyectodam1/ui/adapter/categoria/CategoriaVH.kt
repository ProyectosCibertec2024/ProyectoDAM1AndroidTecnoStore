package com.example.proyectodam1.ui.adapter.categoria

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.proyectodam1.databinding.ItemCategoriaBinding
import com.example.proyectodam1.model.Categoria

class CategoriaVH(val binding : ItemCategoriaBinding) : ViewHolder(binding.root) {
    fun agregarElementos(categoria: Categoria) {
        binding.lblNomCategoria.text = categoria.nomcategoria
    }
}