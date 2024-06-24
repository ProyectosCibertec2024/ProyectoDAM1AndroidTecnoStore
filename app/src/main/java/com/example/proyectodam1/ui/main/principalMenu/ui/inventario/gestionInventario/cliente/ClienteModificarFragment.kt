package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionInventario.cliente

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectodam1.databinding.FragmentClienteModificarBinding
import com.example.proyectodam1.model.Cliente
import com.example.proyectodam1.network.ClienteDataSource
import com.example.proyectodam1.repository.ClienteRepository
import com.example.proyectodam1.viewmodel.ClienteViewModel
import com.example.proyectodam1.viewmodel.ClienteViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class ClienteModificarFragment : Fragment() {
    private var _binding : FragmentClienteModificarBinding ? = null
    private val binding get() = _binding!!

    private val clienteViewModel by viewModels<ClienteViewModel> {
        ClienteViewModelFactory(ClienteRepository(ClienteDataSource(FirebaseFirestore.getInstance())))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClienteModificarBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idbun = arguments?.getString("id")
        val nomclibun = arguments?.getString("nomcli")
        val apeclibun = arguments?.getString("apecli")
        val dniclibun = arguments?.getString("dnicli")
        val direclibun = arguments?.getString("direcli")
        val fonoclibun = arguments?.getString("fonocli")

        binding.txtidclimodificar.editText?.setText(idbun).toString().trim()
        binding.txtnomclimodificar.editText?.setText(nomclibun).toString().trim()
        binding.txtapeclimodificar.editText?.setText(apeclibun).toString().trim()
        binding.txtdniclimodificar.editText?.setText(dniclibun).toString().trim()
        binding.txtdireccionclimodificar.editText?.setText(direclibun).toString().trim()
        binding.txttelefonoclimodificar.editText?.setText(fonoclibun).toString().trim()

        binding.btnmodificarcliente.setOnClickListener {
            val id = binding.txtidclimodificar.editText?.text.toString().trim()
            val nomcli = binding.txtnomclimodificar.editText?.text.toString().trim()
            val apecli = binding.txtapeclimodificar.editText?.text.toString().trim()
            val dnicli = binding.txtdniclimodificar.editText?.text.toString().trim()
            val direccli = binding.txtdireccionclimodificar.editText?.text.toString().trim()
            val fono = binding.txttelefonoclimodificar.editText?.text.toString().trim()

            if(id.isEmpty()) {
                Log.i("ID","ID NULL: $id")
                return@setOnClickListener
            }

            if(nomcli.isEmpty()) {
                binding.txtnomclimodificar.error = "Campo Requerido"
                binding.txtnomclimodificar.requestFocus()
                return@setOnClickListener
            }else if(!nomcli.matches(Regex("^\\D+$"))){
                binding.txtnomclimodificar.error = "Solo Letras"
                binding.txtnomclimodificar.requestFocus()
                return@setOnClickListener
            }

            if(apecli.isEmpty()) {
                binding.txtapeclimodificar.error = "Campo Requerido"
                binding.txtapeclimodificar.requestFocus()
                return@setOnClickListener
            }else if(!apecli.matches(Regex("^\\D+$"))){
                binding.txtapeclimodificar.error = "Solo Letras"
                binding.txtapeclimodificar.requestFocus()
                return@setOnClickListener
            }

            if(dnicli.isEmpty()) {
                binding.txtdniclimodificar.error = "Campo Requerido"
                binding.txtdniclimodificar.requestFocus()
                return@setOnClickListener
            }else if(!dnicli.matches(Regex("^\\d+$"))){
                binding.txtdniclimodificar.error = "Solo Numeros"
                binding.txtdniclimodificar.requestFocus()
                return@setOnClickListener
            }else if(!dnicli.matches(Regex("^\\d{8}+$"))){
                binding.txtdniclimodificar.error = "El DNI Debe Tener 8 Digitos"
                binding.txtdniclimodificar.requestFocus()
                return@setOnClickListener
            }

            if(direccli.isEmpty()) {
                binding.txtdireccionclimodificar.error = "Campo Requerido"
                binding.txtdireccionclimodificar.requestFocus()
                return@setOnClickListener
            }

            if(fono.isEmpty()) {
                binding.txttelefonoclimodificar.error = "Campo Requerido"
                binding.txttelefonoclimodificar.requestFocus()
                return@setOnClickListener
            }else if(!fono.matches(Regex("^\\d+$"))) {
                binding.txttelefonoclimodificar.error = "Solo Numeros"
                binding.txttelefonoclimodificar.requestFocus()
                return@setOnClickListener
            }else if(!fono.matches(Regex("^\\d{9}+$"))) {
                binding.txttelefonoclimodificar.error = "El Telefono Debe Tener 9 Digitos"
                binding.txttelefonoclimodificar.requestFocus()
                return@setOnClickListener
            }

            val cliente = Cliente(id,nomcli,apecli,dnicli,direccli,fono)
            clienteViewModel.actualizarCliente(id, cliente) {
                if (it) {
                    mensaje("Guardado Exitosamente a $nomcli $apecli")
                    binding.txtidclimodificar.editText?.text?.clear()
                    binding.txtnomclimodificar.editText?.text?.clear()
                    binding.txtapeclimodificar.editText?.text?.clear()
                    binding.txtdniclimodificar.editText?.text?.clear()
                    binding.txtdireccionclimodificar.editText?.text?.clear()
                    binding.txttelefonoclimodificar.editText?.text?.clear()
                    findNavController().navigate(ClienteModificarFragmentDirections.actionClienteModificarFragmentToClienteFragment())
                }else {
                    mensaje("Error No Se Pudo Modificar")
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