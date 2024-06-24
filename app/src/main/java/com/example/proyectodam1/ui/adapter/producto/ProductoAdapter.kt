package com.example.proyectodam1.ui.adapter.producto

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.proyectodam1.R
import com.example.proyectodam1.databinding.ItemProductoBinding
import com.example.proyectodam1.model.Producto

class ProductoAdapter(private val update : (Producto) -> Unit,
    private val delete : (id : String, nombre : String) -> Unit) : Adapter<ProductoVH>() {

    private val lista : MutableList<Producto> = mutableListOf()
    fun listar (producto : List<Producto>) {
        lista.clear()
        lista.addAll(producto)
        notifyDataSetChanged()
    }

    fun limpiar() {
        lista.clear()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoVH {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoVH(binding)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ProductoVH, position: Int) {
        val producto = lista[position]

        holder.agregarProductos(producto)

        holder.itemView.setOnClickListener {
            update(producto)
        }

        holder.itemView.findViewById<ImageButton>(R.id.btnEliminarProducto).setOnClickListener {
            delete(producto.id, producto.marca)
        }
    }
}