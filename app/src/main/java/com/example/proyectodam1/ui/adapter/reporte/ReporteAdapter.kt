package com.example.proyectodam1.ui.adapter.reporte

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.proyectodam1.databinding.ItemReporteBinding
import com.example.proyectodam1.model.Venta

class ReporteAdapter(private val data : (Venta) -> Unit) : Adapter<ReporteVH>() {

    private val lista : MutableList<Venta> = mutableListOf()

    fun listar(venta : List<Venta>) {
        lista.clear()
        lista.addAll(venta)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReporteVH {
        val binding = ItemReporteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReporteVH(binding)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ReporteVH, position: Int) {
        val venta = lista[position]

        holder.agregarItem(venta)

        holder.itemView.setOnClickListener {
            data(venta)
        }
    }
}