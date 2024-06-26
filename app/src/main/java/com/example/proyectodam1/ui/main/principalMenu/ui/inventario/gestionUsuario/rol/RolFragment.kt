package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionUsuario.rol

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
import com.example.proyectodam1.databinding.FragmentRolBinding
import com.example.proyectodam1.model.Rol
import com.example.proyectodam1.network.RolDataSource
import com.example.proyectodam1.repository.RolRepository
import com.example.proyectodam1.ui.adapter.rol.RolAdapter
import com.example.proyectodam1.viewmodel.RolViewModel
import com.example.proyectodam1.viewmodel.RolViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class RolFragment : Fragment() {
    private var _binding : FragmentRolBinding ? = null
    private val binding get() = _binding!!

    private val rolViewModel by viewModels<RolViewModel> {
        RolViewModelFactory(RolRepository(RolDataSource(FirebaseFirestore.getInstance())))
    }

    private var adapter : RolAdapter ? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRolBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RolAdapter { rol ->
            actualizarRol(rol)
        }

        binding.rvRol.adapter = adapter
        binding.rvRol.layoutManager = LinearLayoutManager(requireContext())

        rolViewModel.obtenerRoles { rol ->
            adapter?.listar(rol)
        }

        binding.btningresarrol.setOnClickListener {
            findNavController().navigate(RolFragmentDirections.actionRolFragmentToRolRegistrarFragment())
        }

    }

    private fun actualizarRol(rol: Rol) {
        val bundle = bundleOf(
            "id" to rol.id,
            "nomrol" to rol.nomrol
        )
        findNavController().navigate(R.id.action_rolFragment_to_rolModificarFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}