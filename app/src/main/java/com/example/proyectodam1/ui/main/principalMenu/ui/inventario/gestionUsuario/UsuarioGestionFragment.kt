package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionUsuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.proyectodam1.databinding.FragmentUsuarioGestionBinding

class UsuarioGestionFragment : Fragment() {
    private var binding : FragmentUsuarioGestionBinding ? = null
    val _binding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsuarioGestionBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.btnGesUsuario.setOnClickListener {
            findNavController().navigate(UsuarioGestionFragmentDirections.actionNavGestionUsuarioToUsuarioFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    
}