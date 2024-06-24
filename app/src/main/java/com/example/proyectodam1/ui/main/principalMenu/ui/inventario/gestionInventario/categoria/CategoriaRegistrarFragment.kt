package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionInventario.categoria

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectodam1.R
import com.example.proyectodam1.databinding.FragmentCategoriaModificarBinding
import com.example.proyectodam1.databinding.FragmentCategoriaRegistrarBinding
import com.example.proyectodam1.model.Categoria
import com.example.proyectodam1.network.CategoriaDataSource
import com.example.proyectodam1.repository.CategoriaRepository
import com.example.proyectodam1.viewmodel.CategoriaViewModel
import com.example.proyectodam1.viewmodel.CategoriaViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class CategoriaRegistrarFragment : Fragment() {
    private var _binding : FragmentCategoriaRegistrarBinding ? = null
    private val binding get() = _binding!!

    private val categoriaViewModel by viewModels<CategoriaViewModel> {
        CategoriaViewModelFactory(CategoriaRepository(CategoriaDataSource(FirebaseFirestore.getInstance())))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriaRegistrarBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarCategoria()
    }

    private fun registrarCategoria() {
        binding.btnregistrarcategoria.setOnClickListener {
            binding.txtnomcategoriaregistrar.error = null

            val nomcat = binding.txtnomcategoriaregistrar.editText?.text.toString().trim()
            if(nomcat.isEmpty()) {
                binding.txtnomcategoriaregistrar.error = "Campo Requerido"
                return@setOnClickListener
            }else if(!nomcat.matches(Regex("^\\D+$"))) {
                binding.txtnomcategoriaregistrar.error = "Solo Letras"
                return@setOnClickListener
            }

            val categoria = Categoria("", nomcat)
            categoriaViewModel.agregarCategoria(categoria) {
                if(it) {
                    mensaje("Guardado Exitosamente a $nomcat")
                    binding.txtnomcategoriaregistrar.editText?.text?.clear()
                    findNavController().navigate(CategoriaRegistrarFragmentDirections.actionCategoriaRegistrarFragmentToCategoriaFragment())
                }else {
                    mensaje("No se pudo registrar")
                }
            }
        }
    }

    private fun mensaje(s: String) {
        Toast.makeText(requireContext(),s,Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}