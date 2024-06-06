package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionInventario.cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectodam1.databinding.FragmentClienteBinding
import com.example.proyectodam1.network.ClienteDataSource
import com.example.proyectodam1.repository.ClienteRepository
import com.example.proyectodam1.ui.adapter.cliente.ClienteAdapter
import com.example.proyectodam1.viewmodel.ClienteViewModel
import com.example.proyectodam1.viewmodel.ClienteViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class ClienteFragment : Fragment() {
    private var binding : FragmentClienteBinding ? = null
    private val _binding get() = binding!!

    private val clienteViewModel by viewModels<ClienteViewModel> {
        ClienteViewModelFactory(ClienteRepository(ClienteDataSource(FirebaseFirestore.getInstance())))
    }

    private var adapterCliente : ClienteAdapter ? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClienteBinding.inflate(inflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterCliente = ClienteAdapter()

        _binding.rvClientes.adapter = adapterCliente
        _binding.rvClientes.layoutManager = LinearLayoutManager(requireContext())

        clienteViewModel.obtenerClientes {  cli ->
            adapterCliente?.limpiar()
            adapterCliente?.listar(cli)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}