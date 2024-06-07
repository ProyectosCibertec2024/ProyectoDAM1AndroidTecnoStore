package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionInventario.cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectodam1.databinding.FragmentClienteRegistrarBinding
import com.example.proyectodam1.model.Cliente
import com.example.proyectodam1.network.ClienteDataSource
import com.example.proyectodam1.repository.ClienteRepository
import com.example.proyectodam1.viewmodel.ClienteViewModel
import com.example.proyectodam1.viewmodel.ClienteViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class ClienteRegistrarFragment : Fragment() {
    private var _binding : FragmentClienteRegistrarBinding ? = null
    private val binding get() = _binding!!
    private val clienteViewModel by viewModels<ClienteViewModel> {
        ClienteViewModelFactory(ClienteRepository(ClienteDataSource(FirebaseFirestore.getInstance())))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClienteRegistrarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarCliente()
    }

    private fun registrarCliente() {
        binding.btnregistrarcliente.setOnClickListener {
            binding.txtnomcliregistrar.error = null
            binding.txtapecliregistrar.error = null
            binding.txtdnicliregistrar.error = null
            binding.txtdireccioncliregistrar.error = null
            binding.txttelefonocliregistrar.error = null

            val nomcli = binding.txtnomcliregistrar.editText?.text.toString()
            val apecli = binding.txtapecliregistrar.editText?.text.toString()
            val dnicli = binding.txtdnicliregistrar.editText?.text.toString()
            val direccli = binding.txtdireccioncliregistrar.editText?.text.toString()
            val fono = binding.txttelefonocliregistrar.editText?.text.toString()

            if(nomcli.isEmpty()) {
                binding.txtnomcliregistrar.error = "Campo Requerido"
                binding.txtnomcliregistrar.requestFocus()
                return@setOnClickListener
            }else if(!nomcli.matches(Regex("^\\D+$"))){
                binding.txtnomcliregistrar.error = "Solo Letras"
                binding.txtnomcliregistrar.requestFocus()
                return@setOnClickListener
            }

            if(apecli.isEmpty()) {
                binding.txtapecliregistrar.error = "Campo Requerido"
                binding.txtapecliregistrar.requestFocus()
                return@setOnClickListener
            }else if(!apecli.matches(Regex("^\\D+$"))){
                binding.txtapecliregistrar.error = "Solo Letras"
                binding.txtapecliregistrar.requestFocus()
                return@setOnClickListener
            }

            if(dnicli.isEmpty()) {
                binding.txtdnicliregistrar.error = "Campo Requerido"
                binding.txtdnicliregistrar.requestFocus()
                return@setOnClickListener
            }else if(!dnicli.matches(Regex("^\\d+$"))){
                binding.txtdnicliregistrar.error = "Solo Numeros"
                binding.txtdnicliregistrar.requestFocus()
                return@setOnClickListener
            }else if(!dnicli.matches(Regex("^\\d{8}+$"))){
                binding.txtdnicliregistrar.error = "El DNI Debe Tener 8 Digitos"
                binding.txtdnicliregistrar.requestFocus()
                return@setOnClickListener
            }

            if(direccli.isEmpty()) {
                binding.txtdireccioncliregistrar.error = "Campo Requerido"
                binding.txtdireccioncliregistrar.requestFocus()
                return@setOnClickListener
            }

            if(fono.isEmpty()) {
                binding.txttelefonocliregistrar.error = "Campo Requerido"
                binding.txttelefonocliregistrar.requestFocus()
                return@setOnClickListener
            }else if(!fono.matches(Regex("^\\d+$"))) {
                binding.txttelefonocliregistrar.error = "Solo Numeros"
                binding.txttelefonocliregistrar.requestFocus()
                return@setOnClickListener
            }else if(!fono.matches(Regex("^\\d{9}+$"))) {
                binding.txttelefonocliregistrar.error = "El Telefono Debe Tener 9 Digitos"
                binding.txttelefonocliregistrar.requestFocus()
                return@setOnClickListener
            }

            val cliente = Cliente(null,nomcli,apecli,dnicli,direccli,fono)
            clienteViewModel.agregarCliente(cliente) {
                if(it) {
                    mensaje("Guardado Exitosamente a $nomcli $apecli")
                    binding.txtnomcliregistrar.editText?.text?.clear()
                    binding.txtapecliregistrar.editText?.text?.clear()
                    binding.txtdnicliregistrar.editText?.text?.clear()
                    binding.txtdireccioncliregistrar.editText?.text?.clear()
                    binding.txttelefonocliregistrar.editText?.text?.clear()
                    findNavController().navigate(ClienteRegistrarFragmentDirections.actionClienteRegistrarFragmentToClienteFragment())
                }else {
                    mensaje("Ups, Error en registrar")
                }
            }
        }
    }

    private fun mensaje(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}