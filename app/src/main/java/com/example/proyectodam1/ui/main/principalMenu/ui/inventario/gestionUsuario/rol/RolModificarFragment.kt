package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionUsuario.rol

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectodam1.R
import com.example.proyectodam1.databinding.FragmentRolModificarBinding
import com.example.proyectodam1.model.Rol
import com.example.proyectodam1.network.RolDataSource
import com.example.proyectodam1.repository.RolRepository
import com.example.proyectodam1.viewmodel.RolViewModel
import com.example.proyectodam1.viewmodel.RolViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class RolModificarFragment : Fragment() {
    private var _binding : FragmentRolModificarBinding ? = null
    private val binding get() = _binding!!

    private val rolViewModel by viewModels<RolViewModel> {
        RolViewModelFactory(RolRepository(RolDataSource(FirebaseFirestore.getInstance())))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRolModificarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val obid = arguments?.getString("id")
        val obnomrol = arguments?.getString("nomrol")

        binding.txtid.editText?.setText(obid)
        binding.txtnomrol.editText?.setText(obnomrol)

        binding.btnguardarrol.setOnClickListener {
            binding.txtnomrol.error = null

            val id = binding.txtid.editText?.text.toString()
            val nomrol = binding.txtnomrol.editText?.text.toString()

            if(nomrol.isEmpty()) {
                binding.txtnomrol.error = "Ingrese El Nombre del Rol"
                return@setOnClickListener
            }

            val rol = Rol(id, nomrol)
            rolViewModel.actualizarRol(id, rol) { rs ->
                if(rs) {
                    mensaje("Se modifico exitosmente")
                    findNavController().navigate(RolModificarFragmentDirections.actionRolModificarFragmentToRolFragment())
                }else {
                    mensaje("No se pudo modificar")
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