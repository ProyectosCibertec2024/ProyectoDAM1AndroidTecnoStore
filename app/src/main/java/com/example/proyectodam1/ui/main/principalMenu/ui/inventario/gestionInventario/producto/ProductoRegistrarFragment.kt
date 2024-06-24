package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionInventario.producto

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectodam1.databinding.FragmentProductoRegistrarBinding
import com.example.proyectodam1.firebasestorage.SubirStorage
import com.example.proyectodam1.model.Categoria
import com.example.proyectodam1.model.Producto
import com.example.proyectodam1.model.Proveedor
import com.example.proyectodam1.network.CategoriaDataSource
import com.example.proyectodam1.network.ProductoDataSource
import com.example.proyectodam1.network.ProveedorDataSource
import com.example.proyectodam1.repository.CategoriaRepository
import com.example.proyectodam1.repository.ProductoRepository
import com.example.proyectodam1.repository.ProveedorRepository
import com.example.proyectodam1.viewmodel.CategoriaViewModel
import com.example.proyectodam1.viewmodel.CategoriaViewModelFactory
import com.example.proyectodam1.viewmodel.ProductoViewModel
import com.example.proyectodam1.viewmodel.ProductoViewModelFactory
import com.example.proyectodam1.viewmodel.ProveedorViewModel
import com.example.proyectodam1.viewmodel.ProveedorViewModelFactory
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProductoRegistrarFragment : Fragment() {
    private var _binding : FragmentProductoRegistrarBinding ? = null
    private val binding get() = _binding!!

    private val productoViewModel by viewModels<ProductoViewModel> {
        ProductoViewModelFactory(ProductoRepository(ProductoDataSource(FirebaseFirestore.getInstance())))
    }

    private val categoriaViewModel by viewModels<CategoriaViewModel> {
        CategoriaViewModelFactory(CategoriaRepository(CategoriaDataSource(FirebaseFirestore.getInstance())))
    }

    private val proveedorViewModel by viewModels<ProveedorViewModel> {
        ProveedorViewModelFactory(ProveedorRepository(ProveedorDataSource(FirebaseFirestore.getInstance())))
    }

    private lateinit var categoriaAdapter: ArrayAdapter<String>
    private var listaCategorias = mutableListOf<Categoria>()

    private lateinit var proveedorAdapter: ArrayAdapter<String>
    private var listaProveedores = mutableListOf<Proveedor>()

    private var selectImagen : Uri ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductoRegistrarBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cargarComboCategoria()
        cargarComboProveedor()

        abrirGaleria()
        registrarProducto()
    }

    private fun cargarComboProveedor() {
        proveedorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, listaProveedores.map { p -> p.nomprov + " " + p.apeprov })
        binding.cboproveedor2.setAdapter(proveedorAdapter)
        proveedorViewModel.obtenerProveedores { lis ->
            listaProveedores.clear()
            listaProveedores.addAll(lis)
            val proveedor = lis.map { it.nomprov + " " + it.apeprov }
            proveedorAdapter.clear()
            proveedorAdapter.addAll(proveedor)
            proveedorAdapter.notifyDataSetChanged()
        }
    }

    private fun cargarComboCategoria() {
        categoriaAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            listaCategorias.map { it.nomcategoria })
        binding.cbocategoria2.setAdapter(categoriaAdapter)
        categoriaViewModel.obtenerCategoria { lista ->
            listaCategorias.clear()
            listaCategorias.addAll(lista)
            val nombresCategorias = lista.map { it.nomcategoria }
            categoriaAdapter.clear()
            categoriaAdapter.addAll(nombresCategorias)
            categoriaAdapter.notifyDataSetChanged()
        }
    }

    private fun registrarProducto() {
        binding.btnregistrarproducto.setOnClickListener {
            binding.txtmarca.error = null
            binding.cbocategoria.error = null
            binding.cboproveedor.error = null
            binding.txtprecio.error = null
            binding.txtstock.error = null

            val txtmarca = binding.txtmarca.editText?.text.toString()
            val txtprecio = binding.txtprecio.editText?.text.toString().toDoubleOrNull()
            val txtstock = binding.txtstock.editText?.text.toString().toIntOrNull()
            val categoriaSeleccionada = binding.cbocategoria2.text.toString()
            val proveedorSeleccionado = binding.cboproveedor2.text.toString()

            if (txtmarca.isEmpty()) {
                binding.txtmarca.error = "Ingrese la marca"
                return@setOnClickListener
            }

            if (txtprecio == null) {
                binding.txtprecio.error = "Ingrese un precio válido"
                return@setOnClickListener
            }

            if (selectImagen == null) {
                mensaje("Seleccione una imagen del producto")
                return@setOnClickListener
            }

            if (txtstock == null) {
                binding.txtstock.error = "Ingrese un stock válido"
                return@setOnClickListener
            }

            val categoriaIndex = listaCategorias.indexOfFirst { it.nomcategoria == categoriaSeleccionada }
            val proveedorIndex = listaProveedores.indexOfFirst { it.nomprov + " " + it.apeprov == proveedorSeleccionado }

            if (categoriaIndex == -1) {
                binding.cbocategoria.error = "Seleccione una categoría válida"
                return@setOnClickListener
            }

            if (proveedorIndex == -1) {
                binding.cboproveedor.error = "Seleccione un proveedor válido"
                return@setOnClickListener
            }

            val categoriaRef: DocumentReference = FirebaseFirestore.getInstance().collection("Categoria").document(listaCategorias[categoriaIndex].id)
            val proveedorRef: DocumentReference = FirebaseFirestore.getInstance().collection("Proveedor").document(listaProveedores[proveedorIndex].id)

            val date = Date()
            val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val fecha = format.format(date)

            val subirStorage = SubirStorage()
            subirStorage.uploadImage(requireContext(), selectImagen!!, "productos") { success, download ->
                if(success && download != null) {
                    val nomimagen = subirStorage.getFileName(requireContext(), selectImagen!!)
                    val producto = Producto("", categoriaRef, proveedorRef, txtmarca, nomimagen, fecha, txtprecio, txtstock, download!!)
                    productoViewModel.agregarProductos(producto) { isSuccess ->
                        if (isSuccess) {
                            mensaje("Producto registrado exitosamente")
                            findNavController().navigate(ProductoRegistrarFragmentDirections.actionProductoRegistrarFragmentToProductoFragment())
                        } else {
                            mensaje("Error al registrar el producto")
                        }
                    }
                }else {
                    mensaje("Error al subir la imagen")
                }
            }
        }
    }

    private fun mensaje(s : String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

    private fun abrirGaleria() {
        binding.btnseleccionarproducto.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    data?.data?.let { uri ->
                        selectImagen = uri
                        binding.imgproductocargar.setImageURI(uri)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 100
    }
}