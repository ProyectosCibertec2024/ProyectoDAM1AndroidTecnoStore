package com.example.proyectodam1.repository

import com.example.proyectodam1.model.Cliente
import com.example.proyectodam1.network.ClienteDataSource

class ClienteRepository(private val dataSource: ClienteDataSource) {

    fun listadoClientes(clientes : (List<Cliente>) -> Unit) {
        dataSource.obtenerClientes {
            clientes(it)
        }
    }

    fun agregarClientes(cliente: Cliente, rs : (Boolean) -> Unit) {
        dataSource.agregarClientes(cliente) {
            rs(it)
        }
    }

    fun actualizarCliente(id : String ,cliente: Cliente, rs : (Boolean) -> Unit) {
        dataSource.actualizarCliente(id, cliente) {
            rs(it)
        }
    }

    fun eliminarCliente(id : String, rs: (Boolean) -> Unit) {
        dataSource.eliminarCliente(id) {
            rs(it)
        }
    }
}