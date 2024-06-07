package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionInventario.cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectodam1.R
import com.example.proyectodam1.databinding.FragmentClienteBinding
import com.example.proyectodam1.model.Cliente
import com.example.proyectodam1.network.ClienteDataSource
import com.example.proyectodam1.repository.ClienteRepository
import com.example.proyectodam1.ui.adapter.cliente.ClienteAdapter
import com.example.proyectodam1.viewmodel.ClienteViewModel
import com.example.proyectodam1.viewmodel.ClienteViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        adapterCliente = ClienteAdapter({ id, nom ->
            eliminarCliente(id, nom)
        }, { cli ->
            cargarCliente(cli)
        })

        _binding.rvClientes.adapter = adapterCliente
        _binding.rvClientes.layoutManager = LinearLayoutManager(requireContext())

        clienteViewModel.obtenerClientes {  cli ->
            adapterCliente?.limpiar()
            adapterCliente?.listar(cli)
        }

        ingresarFormularioCliente()
    }

    private fun eliminarCliente(id: String, nom : String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Alert!")
            .setMessage("Deseas Eliminar a $nom?")
            .setPositiveButton("Aceptar") { _,_ ->
                clienteViewModel.eliminarCliente(id) {
                    if(it) {
                        mensaje("Se Elimino El cliente $nom")
                        clienteViewModel.obtenerClientes {  cli ->
                            adapterCliente?.limpiar()
                            adapterCliente?.listar(cli)
                        }
                    }else {
                        mensaje("Error No Se Pudo Eliminar el : $nom")
                    }
                }
            }.setNegativeButton("Cancelar", null)
            .show()
    }

    private fun mensaje(s: String) {
        Toast.makeText(requireContext(),s,Toast.LENGTH_SHORT).show()
    }

    private fun ingresarFormularioCliente() {
        _binding.btningresarcliente.setOnClickListener {
            findNavController().navigate(ClienteFragmentDirections.actionClienteFragmentToClienteRegistrarFragment())
        }
    }

    private fun cargarCliente(cli: Cliente) {
        val bundle = bundleOf(
            "id" to cli.id,
            "nomcli" to cli.nomcli,
            "apecli" to cli.apecli,
            "dnicli" to cli.dnicliente,
            "direcli" to cli.direccion,
            "fonocli" to cli.telefono
        )
        findNavController().navigate(R.id.action_clienteFragment_to_clienteModificarFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}