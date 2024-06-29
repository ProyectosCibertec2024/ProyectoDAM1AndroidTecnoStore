package com.example.proyectodam1.ui.adapter.venta

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.proyectodam1.databinding.ItemVentasBinding
import com.example.proyectodam1.model.Venta

class VentaAdapter(private val update : (Venta) -> Unit) : Adapter<VentaVH>() {

    private val lista : MutableList<Venta> = mutableListOf()

    fun listar(venta: List<Venta>) {
        lista.clear()
        lista.addAll(venta)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VentaVH {
        val binding = ItemVentasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VentaVH(binding)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: VentaVH, position: Int) {
        val venta = lista[position]

        holder.agregarItem(venta)

        holder.itemView.setOnClickListener {
            update(venta)
        }
    }
}