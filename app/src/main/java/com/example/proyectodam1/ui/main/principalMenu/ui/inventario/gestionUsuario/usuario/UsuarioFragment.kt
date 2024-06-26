package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionUsuario.usuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectodam1.R
import com.example.proyectodam1.databinding.FragmentUsuarioBinding
import com.example.proyectodam1.model.Usuario
import com.example.proyectodam1.network.UsuarioDataSource
import com.example.proyectodam1.repository.UsuarioRepository
import com.example.proyectodam1.ui.adapter.usuario.UsuarioAdapter
import com.example.proyectodam1.viewmodel.UsuarioViewModel
import com.example.proyectodam1.viewmodel.UsuarioViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class UsuarioFragment : Fragment() {
    private var _binding : FragmentUsuarioBinding ? = null
    private val binding get() = _binding!!

    private val usuarioViewModel by viewModels<UsuarioViewModel> {
        UsuarioViewModelFactory(UsuarioRepository(UsuarioDataSource(FirebaseFirestore.getInstance())))
    }

    private var adapter : UsuarioAdapter ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsuarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UsuarioAdapter() {
            modificarUsuario(it)
        }

        binding.rvUsuario.adapter = adapter
        binding.rvUsuario.layoutManager = LinearLayoutManager(requireContext())

        usuarioViewModel.obtenerUsuario { u ->
            adapter?.listar(u)
        }

        ingresarRegUsuario()
    }

    private fun ingresarRegUsuario() {
        binding.btningresarusuario.setOnClickListener {
            findNavController().navigate(UsuarioFragmentDirections.actionUsuarioFragmentToUsuarioRegistrarFragment())
        }
    }

    private fun modificarUsuario(it: Usuario) {
        val bundle = bundleOf(
            "id" to it.id,
            "nombre" to it.nombre,
            "apellido" to it.apellido,
            "email" to it.email,
            "password" to it.password,
            "urlimg" to it.urlimg,
            "rol" to it.rol?.id
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}