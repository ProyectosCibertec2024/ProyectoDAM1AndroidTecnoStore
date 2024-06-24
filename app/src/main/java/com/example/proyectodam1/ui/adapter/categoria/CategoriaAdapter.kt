package com.example.proyectodam1.ui.adapter.categoria

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.proyectodam1.R
import com.example.proyectodam1.databinding.ItemCategoriaBinding
import com.example.proyectodam1.model.Categoria

class CategoriaAdapter(val clickDelete : (id : String, nom : String) -> Unit,
    val clickUpdate : (Categoria) -> Unit) : Adapter<CategoriaVH>() {

    private val lista : MutableList<Categoria> = mutableListOf()

    fun listar(categoria: List<Categoria>) {
        lista.addAll(categoria)
        notifyDataSetChanged()
    }

    fun limpiar() {
        lista.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaVH {
        val binding = ItemCategoriaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriaVH(binding)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: CategoriaVH, position: Int) {
        val categoria = lista[position]
        holder.agregarElementos(categoria)

        holder.itemView.setOnClickListener {
            clickUpdate(categoria)
        }

        holder.itemView.findViewById<ImageButton>(R.id.btnEliminarCategoria).setOnClickListener {
            Log.i("ID", "ID: ${categoria.id}")
            clickDelete(categoria.id.toString(),categoria.nomcategoria)
        }
    }
}