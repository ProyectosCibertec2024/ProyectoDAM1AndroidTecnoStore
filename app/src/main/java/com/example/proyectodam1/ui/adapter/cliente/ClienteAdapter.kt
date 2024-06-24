package com.example.proyectodam1.ui.adapter.cliente

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.proyectodam1.R
import com.example.proyectodam1.databinding.ItemClienteBinding
import com.example.proyectodam1.model.Cliente

class ClienteAdapter(val clickDelete : (id : String, nombre : String) -> Unit,
                     val click : (Cliente) -> Unit) : Adapter<ClienteVH>() {

    private var lista : MutableList<Cliente> = mutableListOf()

    fun listar(cliente : List<Cliente>) {
        lista.addAll(cliente)
        notifyDataSetChanged()
    }

    fun limpiar() {
        lista.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteVH {
        val itemCliente = ItemClienteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClienteVH(itemCliente)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ClienteVH, position: Int) {
        val cliente = lista[position]
        holder.obtenerClientes(cliente)

        holder.itemView.setOnClickListener {
            click(cliente)
        }

        holder.itemView.findViewById<ImageButton>(R.id.btnEliminarCliente).setOnClickListener {
            Log.i("ID", "ID: ${cliente.id}")
            clickDelete(cliente.id.toString(), cliente.nomcli)
        }
    }

}