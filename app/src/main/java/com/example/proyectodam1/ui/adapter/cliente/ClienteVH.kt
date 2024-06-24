package com.example.proyectodam1.ui.adapter.cliente

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.proyectodam1.databinding.ItemClienteBinding
import com.example.proyectodam1.model.Cliente

class ClienteVH(val binding : ItemClienteBinding) : ViewHolder(binding.root) {

    fun obtenerClientes(cliente: Cliente) {
        binding.lblNombreCliente.text = cliente.nomcli
        binding.lblApellidoCliente.text = cliente.apecli
    }
}

