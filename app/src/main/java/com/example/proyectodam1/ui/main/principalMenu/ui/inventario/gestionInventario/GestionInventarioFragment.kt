package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionInventario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.proyectodam1.R
import com.example.proyectodam1.databinding.FragmentGestionInventarioBinding
import com.example.proyectodam1.databinding.FragmentInventarioBinding

class GestionInventarioFragment : Fragment() {
    private var binding : FragmentGestionInventarioBinding ? = null
    private val _binding get() = binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGestionInventarioBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ingresar()
    }

    private fun ingresar() {
        _binding.btngesinventariocliente.setOnClickListener {
            findNavController().navigate(GestionInventarioFragmentDirections.actionGestionInventarioFragmentToClienteFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}