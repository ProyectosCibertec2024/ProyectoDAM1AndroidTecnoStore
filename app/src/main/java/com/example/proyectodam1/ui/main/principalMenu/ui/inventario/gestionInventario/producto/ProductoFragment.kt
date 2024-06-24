package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionInventario.producto

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
import com.example.proyectodam1.databinding.FragmentProductoBinding
import com.example.proyectodam1.model.Producto
import com.example.proyectodam1.network.ProductoDataSource
import com.example.proyectodam1.repository.ProductoRepository
import com.example.proyectodam1.ui.adapter.producto.ProductoAdapter
import com.example.proyectodam1.viewmodel.ProductoViewModel
import com.example.proyectodam1.viewmodel.ProductoViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore

class ProductoFragment : Fragment() {
    private var _binding : FragmentProductoBinding ? = null
    private val binding get() = _binding!!

    private val productoViewModel by viewModels<ProductoViewModel> {
        ProductoViewModelFactory(ProductoRepository(ProductoDataSource(FirebaseFirestore.getInstance())))
    }

    private var adapter : ProductoAdapter ? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProductoAdapter({ prod ->
            actualizarProducto(prod)
        }, { id, nombre ->
            eliminarProducto(id, nombre)
        })

        binding.rvProducto.adapter = adapter
        binding.rvProducto.layoutManager = LinearLayoutManager(requireContext())

        productoViewModel.obtenerProductos { productos ->
            if(productos.isNotEmpty()) {
                adapter?.limpiar()
                adapter?.listar(productos)
            }else {
                mensaje("Lista Vacia")
            }
        }

        registrarVentana()
    }

    private fun registrarVentana() {
        binding.btningresarproducto.setOnClickListener {
            findNavController().navigate(ProductoFragmentDirections.actionProductoFragmentToProductoRegistrarFragment())
        }
    }

    private fun eliminarProducto(id: String, nombre: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Alerta")
            .setMessage("¿Deseas Eliminar El Producto $nombre?")
            .setPositiveButton("Aceptar") { _,_ ->
                productoViewModel.eliminarProducto(id) {
                    if(it) {
                        mensaje("Se Elimino El Producto $nombre")
                        productoViewModel.obtenerProductos { productos ->
                            if(productos.isNotEmpty()) {
                                adapter?.limpiar()
                                adapter?.listar(productos)
                            }else {
                                mensaje("Lista Vacia")
                            }
                        }
                    }else {
                        mensaje("No Se Puedo Eliminar El Producto $nombre")
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun mensaje(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

    private fun actualizarProducto(prod: Producto) {
        val bundle = bundleOf(
            "id" to prod.id,
            "idcategoria" to prod.idcategoria?.id,
            "idproveedor" to prod.idproveedor?.id,
            "marca" to prod.marca,
            "fechareg" to prod.fechareg,
            "nomimg" to prod.nombreimg,
            "precio" to prod.precio.toString(),
            "stock" to prod.stock.toString(),
            "urlimg" to prod.urlimg
        )
        findNavController().navigate(R.id.action_productoFragment_to_productoModificarFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}