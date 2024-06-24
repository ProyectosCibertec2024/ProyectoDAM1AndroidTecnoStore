package com.example.proyectodam1.ui.adapter.producto

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.proyectodam1.databinding.ItemProductoBinding
import com.example.proyectodam1.model.Producto
import com.example.proyectodam1.network.ProductoDataSource
import com.google.firebase.firestore.FirebaseFirestore

class ProductoVH(private val binding : ItemProductoBinding) : ViewHolder(binding.root)  {
    fun agregarProductos(producto: Producto) {
        Glide.with(binding.root.context).load(producto.urlimg).into(binding.imgProductoView)
        binding.lblMarcaProducto.text = producto.marca
        binding.lblPrecioProducto.text = producto.precio.toString()
        ProductoDataSource(FirebaseFirestore.getInstance()).buscarCategoriaenProducto(producto) { nombreCategoria ->
            binding.lblCategoriaProducto.text = nombreCategoria ?: "Sin categoría"
        }
    }
}