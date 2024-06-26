package com.example.proyectodam1.ui.adapter.usuario

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.proyectodam1.databinding.ItemUsuarioBinding
import com.example.proyectodam1.model.Usuario

class UsuarioAdapter(private val updateClick : (Usuario) -> Unit) : Adapter<UsuarioVH>() {

    private val lista : MutableList<Usuario> = mutableListOf()

    fun listar(usuario : List<Usuario>) {
        lista.clear()
        lista.addAll(usuario)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioVH {
        val binding = ItemUsuarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsuarioVH(binding)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: UsuarioVH, position: Int) {
        val usuario = lista[position]

        holder.agregarItem(usuario)

        holder.itemView.setOnClickListener {
            updateClick(usuario)
        }
    }

}