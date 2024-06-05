package com.example.proyectodam1.ui.main.principalMenu.ui.inventario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.proyectodam1.databinding.FragmentInventarioBinding

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

        ingresarGestionUsuario();
    }

    private fun ingresarGestionUsuario() {
        binding.btnGesUsuario.setOnClickListener {
            findNavController().navigate(InventarioFragmentDirections.actionNavInventaryToNavGestionUsuario())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}