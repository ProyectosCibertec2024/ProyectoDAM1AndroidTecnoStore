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
import com.example.proyectodam1.model.Categoria
import com.example.proyectodam1.network.CategoriaDataSource
import com.example.proyectodam1.repository.CategoriaRepository
import com.example.proyectodam1.viewmodel.CategoriaViewModel
import com.example.proyectodam1.viewmodel.CategoriaViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class CategoriaModificarFragment : Fragment() {
    private var _binding : FragmentCategoriaModificarBinding ? = null
    private val binding get() = _binding!!
    private val categoriaViewModel by viewModels<CategoriaViewModel> {
        CategoriaViewModelFactory(CategoriaRepository(CategoriaDataSource(FirebaseFirestore.getInstance())))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriaModificarBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        modificicarCategoria()
    }

    private fun modificicarCategoria() {
        val id = arguments?.getString("id")
        val nom = arguments?.getString("nomcat")

        binding.txtidcategoriamodificar.editText?.setText(id).toString().trim()
        binding.txtnomcategoriaremodificar.editText?.setText(nom).toString().trim()

        binding.btnmodificarcategoria.setOnClickListener {

            binding.txtnomcategoriaremodificar.error = null

            val idcat = binding.txtidcategoriamodificar.editText?.text.toString().trim()
            val nomcat = binding.txtnomcategoriaremodificar.editText?.text.toString().trim()
            if(nomcat.isEmpty()) {
                binding.txtnomcategoriaremodificar.error = "Campo Requerido"
                return@setOnClickListener
            }else if(!nomcat.matches(Regex("^\\D+$"))) {
                binding.txtnomcategoriaremodificar.error = "Solo Letras"
                return@setOnClickListener
            }
            val categoria = Categoria(id,nomcat)
            categoriaViewModel.actualizarCategoria(idcat, categoria) {
                if(it) {
                    mensaje("Guardado Exitosamente a $nomcat")
                    binding.txtidcategoriamodificar.editText?.text?.clear()
                    binding.txtnomcategoriaremodificar.editText?.text?.clear()
                    findNavController().navigate(CategoriaModificarFragmentDirections.actionCategoriaModificarFragmentToCategoriaFragment())
                }else {
                    mensaje("No se puedo modificar a $nom")
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