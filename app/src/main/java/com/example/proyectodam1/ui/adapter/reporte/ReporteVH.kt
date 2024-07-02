package com.example.proyectodam1.ui.adapter.reporte

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.proyectodam1.databinding.ItemReporteBinding
import com.example.proyectodam1.model.Venta
import com.example.proyectodam1.network.VentaDataSource
import com.google.firebase.firestore.FirebaseFirestore

class ReporteVH(private val binding : ItemReporteBinding) : ViewHolder(binding.root) {
    fun agregarItem(venta: Venta) {
        VentaDataSource(FirebaseFirestore.getInstance()).buscarclientexventa(venta) {
            binding.lblClienteProducto.text = "Cliente : ${it}"
        }
        VentaDataSource(FirebaseFirestore.getInstance()).buscarventaxusuario(venta) {
            binding.lblUsuario.text = "Usuario : ${it}"
        }
        binding.lblTotal.text = "Total : ${venta.total}"
        VentaDataSource(FirebaseFirestore.getInstance()).buscarproductoxventa(venta) { marca, img ->
            binding.lblProducto.text = "Producto : ${marca}"
            Glide.with(binding.root.context).load(img).into(binding.imgProductoView)
        }
    }
}