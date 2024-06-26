package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionProveedores

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
import com.example.proyectodam1.databinding.FragmentProveedorBinding
import com.example.proyectodam1.model.Proveedor
import com.example.proyectodam1.network.ProveedorDataSource
import com.example.proyectodam1.repository.ProveedorRepository
import com.example.proyectodam1.ui.adapter.proveedor.ProveedorAdapter
import com.example.proyectodam1.viewmodel.ProveedorViewModel
import com.example.proyectodam1.viewmodel.ProveedorViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class ProveedorFragment : Fragment() {
    private var _binding : FragmentProveedorBinding ? = null
    private val binding get() = _binding!!

    private val proveedorViewModel by viewModels<ProveedorViewModel> {
        ProveedorViewModelFactory(ProveedorRepository(ProveedorDataSource(FirebaseFirestore.getInstance())))
    }

    private var adapter : ProveedorAdapter ? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProveedorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProveedorAdapter { p ->
            actualizarProveedor(p)
        }

        binding.rvProveedor.adapter = adapter
        binding.rvProveedor.layoutManager = LinearLayoutManager(requireContext())

        proveedorViewModel.obtenerProveedores { p ->
            adapter?.listar(p)
        }

        ingresarProveedor()
    }

    private fun ingresarProveedor() {
        binding.btningresarproveedor.setOnClickListener {
            findNavController().navigate(ProveedorFragmentDirections.actionProveedorFragmentToProveedorRegistrarFragment())
        }
    }

    private fun actualizarProveedor(p: Proveedor) {
        val bundle = bundleOf(
            "id" to p.id,
            "nomprov" to p.nomprov,
            "apeprov" to p.apeprov,
            "email" to p.email,
            "fono" to p.telefono
        )
        findNavController().navigate(R.id.action_proveedorFragment_to_proveedorModificarFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}