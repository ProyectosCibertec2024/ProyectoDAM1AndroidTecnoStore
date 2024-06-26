package com.example.proyectodam1.ui.adapter.rol

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.proyectodam1.databinding.ItemRolBinding
import com.example.proyectodam1.model.Rol

class RolAdapter(private val update : (Rol) -> Unit) : Adapter<RolVH>() {

    private val lista : MutableList<Rol> = mutableListOf()

    fun listar(rol : List<Rol>) {
        lista.clear()
        lista.addAll(rol)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RolVH {
        val binding = ItemRolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RolVH(binding)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: RolVH, position: Int) {
        val rol = lista[position]
        holder.agregarItem(rol)

        holder.itemView.setOnClickListener{
            update(rol)
        }
    }
}