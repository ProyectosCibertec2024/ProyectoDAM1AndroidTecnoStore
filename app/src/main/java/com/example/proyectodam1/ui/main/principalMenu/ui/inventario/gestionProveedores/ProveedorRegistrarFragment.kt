package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionProveedores

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectodam1.R
import com.example.proyectodam1.databinding.FragmentProveedorRegistrarBinding
import com.example.proyectodam1.model.Proveedor
import com.example.proyectodam1.network.ProveedorDataSource
import com.example.proyectodam1.repository.ProveedorRepository
import com.example.proyectodam1.viewmodel.ProveedorViewModel
import com.example.proyectodam1.viewmodel.ProveedorViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class ProveedorRegistrarFragment : Fragment() {
    private var _binding : FragmentProveedorRegistrarBinding ? = null
    private val binding get() = _binding!!
    private val proveedorViewModel by viewModels<ProveedorViewModel> {
        ProveedorViewModelFactory(ProveedorRepository(ProveedorDataSource(FirebaseFirestore.getInstance())))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProveedorRegistrarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnguardar.setOnClickListener {
            binding.txtnomprov.error = null
            binding.txtapeprov.error = null
            binding.txtemailprov.error = null
            binding.txtfonoprov.error = null

            val nomprov = binding.txtnomprov.editText?.text.toString()
            val apeprov = binding.txtapeprov.editText?.text.toString()
            val email = binding.txtemailprov.editText?.text.toString()
            val fono = binding.txtfonoprov.editText?.text.toString()

            if(nomprov.isEmpty()) {
                binding.txtnomprov.error = "Ingrese El Nombre"
                return@setOnClickListener
            }

            if(apeprov.isEmpty()) {
                binding.txtapeprov.error = "Ingrese El Apellido"
                return@setOnClickListener
            }

            if(email.isEmpty()) {
                binding.txtemailprov.error = "Ingrese El Email"
                return@setOnClickListener
            }

            if(fono.isEmpty()) {
                binding.txtfonoprov.error = "Ingrese El Telefono"
                return@setOnClickListener
            }else if(!fono.matches(Regex("^\\d{9,11}$"))) {
                binding.txtfonoprov.error = "El Telefono debe de tener entre 9 a 11 digitos"
                return@setOnClickListener
            }

            val proveedor = Proveedor("", nomprov, apeprov, email, fono)
            proveedorViewModel.agregarProveedores(proveedor) {
                if(it) {
                    mensaje("Se agrego a $nomprov")
                    findNavController().navigate(ProveedorRegistrarFragmentDirections.actionProveedorRegistrarFragmentToProveedorFragment())
                }else {
                    mensaje("No se pudo agregar")
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