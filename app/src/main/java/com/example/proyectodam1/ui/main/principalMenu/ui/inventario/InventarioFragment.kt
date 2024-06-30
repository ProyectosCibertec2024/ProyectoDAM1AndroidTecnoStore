package com.example.proyectodam1.ui.main.principalMenu.ui.inventario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.proyectodam1.databinding.FragmentInventarioBinding
import com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionInventario.GestionInventarioFragmentDirections

class InventarioFragment : Fragment() {

    private var _binding: FragmentInventarioBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentInventarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ingresar()
    }

    private fun ingresar() {
        binding.btnGesUsuario.setOnClickListener {
            findNavController().navigate(InventarioFragmentDirections.actionNavInventaryToNavGestionUsuario())
        }
        binding.btnGesInventario.setOnClickListener {
            findNavController().navigate(InventarioFragmentDirections.actionNavInventaryToGestionInventarioFragment())
        }
        binding.btngesproveedores.setOnClickListener {
            findNavController().navigate(InventarioFragmentDirections.actionNavInventaryToProveedorFragment())
        }
        binding.btnGesConsulta.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}