package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionInventario.categoria

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectodam1.R
import com.example.proyectodam1.databinding.FragmentCategoriaBinding
import com.example.proyectodam1.model.Categoria
import com.example.proyectodam1.network.CategoriaDataSource
import com.example.proyectodam1.repository.CategoriaRepository
import com.example.proyectodam1.ui.adapter.categoria.CategoriaAdapter
import com.example.proyectodam1.viewmodel.CategoriaViewModel
import com.example.proyectodam1.viewmodel.CategoriaViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore

class CategoriaFragment : Fragment() {
    private var _binding : FragmentCategoriaBinding ? = null
    private val binding get() = _binding!!
    private val categoriaViewModel by viewModels<CategoriaViewModel> {
        CategoriaViewModelFactory(CategoriaRepository(CategoriaDataSource(FirebaseFirestore.getInstance())))
    }

    private var categoriaAdapter : CategoriaAdapter ? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoriaAdapter = CategoriaAdapter({ id, nom ->
            categoriaEliminar(id,nom)
        }, { cat ->
            categoriaModificar(cat)
        })

        binding.rvCategoria.adapter = categoriaAdapter
        binding.rvCategoria.layoutManager = LinearLayoutManager(requireContext())

        categoriaViewModel.obtenerCategoria { cat ->
            categoriaAdapter?.limpiar()
            categoriaAdapter?.listar(cat)
        }

        ingresar()
    }

    private fun categoriaModificar(cat: Categoria) {
        val bundle = bundleOf(
            "id" to cat.id,
            "nomcat" to cat.nomcategoria
        )
        findNavController().navigate(R.id.action_categoriaFragment_to_categoriaModificarFragment, bundle)
    }

    private fun categoriaEliminar(id: String, nom: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Alert!")
            .setMessage("¿Deseas Eliminar a $nom?")
            .setPositiveButton("Aceptar") { _,_ ->
                categoriaViewModel.eliminarCategoria(id) {
                    if(it) {
                        mensaje("Se elimino la categoria $nom")
                        categoriaViewModel.obtenerCategoria { cat ->
                            categoriaAdapter?.limpiar()
                            categoriaAdapter?.listar(cat)
                        }
                    }else {
                        mensaje("No se pudo eliminar a $nom")
                    }
                }
            }.setNegativeButton("Cancelar", null)
            .show()
    }

    private fun mensaje(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

    private fun ingresar() {
        binding.btningresarcategoria.setOnClickListener {
            findNavController().navigate(CategoriaFragmentDirections.actionCategoriaFragmentToCategoriaRegistrarFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}