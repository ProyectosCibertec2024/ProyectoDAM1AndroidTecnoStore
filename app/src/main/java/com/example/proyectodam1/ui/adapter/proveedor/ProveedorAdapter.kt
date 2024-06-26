package com.example.proyectodam1.ui.adapter.proveedor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.proyectodam1.databinding.ItemProveedorBinding
import com.example.proyectodam1.model.Proveedor

class ProveedorAdapter(private val updateClick : (Proveedor) -> Unit) : Adapter<ProveedorVH>() {

    private val lista : MutableList<Proveedor> = mutableListOf()

    fun listar(proveedor : List<Proveedor>) {
        lista.clear()
        lista.addAll(proveedor)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProveedorVH {
        val view = ItemProveedorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProveedorVH(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ProveedorVH, position: Int) {
        val proveedor = lista[position]
        holder.agregarItems(proveedor)

        holder.itemView.setOnClickListener {
            updateClick(proveedor)
        }
    }

}