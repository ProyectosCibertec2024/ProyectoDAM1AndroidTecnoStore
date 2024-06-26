package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionInventario.producto

import android.app.Activity
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
import com.bumptech.glide.Glide
import com.example.proyectodam1.R
import com.example.proyectodam1.databinding.FragmentProductoModificarBinding
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

class ProductoModificarFragment : Fragment() {
    private var _binding : FragmentProductoModificarBinding ? = null
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

    private var selectImagen : Uri? = null

    private var selectedCategoriaId: String? = null
    private var selectedProveedorId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductoModificarBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val obid = arguments?.getString("id")
        val obidcategoria = arguments?.getString("idcategoria")
        val obidproveedor = arguments?.getString("idproveedor")
        val obmarca = arguments?.getString("marca")
        val obprecio = arguments?.getString("precio")
        val obstock = arguments?.getString("stock")
        val fecha = arguments?.getString("fechareg")
        val oburlimg = arguments?.getString("urlimg")

        binding.txtidproducto.editText?.setText(obid)
        binding.txtmarca.editText?.setText(obmarca)
        binding.txtprecio.editText?.setText(obprecio)
        binding.txtstock.editText?.setText(obstock)
        binding.cbocategoria2.setText(obidcategoria)
        binding.cboproveedor2.setText(obidproveedor)

        oburlimg?.let {
            Glide.with(requireContext())
                .load(it)
                .placeholder(R.drawable.ic_producto)
                .error(R.drawable.ic_producto)
                .into(binding.imgproductocargar)
        }

        cargarComboCategoria(obidcategoria)
        cargarComboProveedor(obidproveedor)
        abrirGaleria()

        modificarProducto(fecha!!)
    }

    private fun modificarProducto(fecha : String) {
        binding.btnguardarproducto.setOnClickListener {
            binding.txtmarca.error = null
            binding.cbocategoria.error = null
            binding.cboproveedor.error = null
            binding.txtprecio.error = null
            binding.txtstock.error = null

            val idprod = binding.txtidproducto.editText?.text.toString()
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

           if(selectImagen != null) {
               val subirStorage = SubirStorage()
               subirStorage.uploadImage(requireContext(), selectImagen!!, "productos") { success, download ->
                   if(success && download != null) {
                       val nomimagen = subirStorage.getFileName(requireContext(), selectImagen!!)
                       val producto = Producto("", categoriaRef, proveedorRef, txtmarca, nomimagen, fecha, txtprecio, txtstock, download!!)
                       productoViewModel.actualizarProductos(idprod, producto) { isSuccess ->
                           if(isSuccess) {
                               mensaje("Producto modificado exitosamente")
                               findNavController().navigate(ProductoModificarFragmentDirections.actionProductoModificarFragmentToProductoFragment())
                           }else {
                               mensaje("No se pudo modificar")
                           }
                       }
                   }else {
                       mensaje("Error al subir la imagen")
                   }

               }
           }else {
                productoViewModel.buscarProductoxId(idprod) { prod ->
                    val producto = Producto("", categoriaRef, proveedorRef, txtmarca, prod!!.nombreimg, fecha, txtprecio, txtstock, prod.urlimg)
                    productoViewModel.actualizarProductos(idprod, producto) { isSuccess ->
                        if (isSuccess) {
                            mensaje("Producto modificado exitosamente")
                            findNavController().navigate(ProductoModificarFragmentDirections.actionProductoModificarFragmentToProductoFragment())
                        } else {
                            mensaje("No se pudo modificar")
                        }
                    }
                }
           }
        }
    }

    private fun cargarComboProveedor(idproveedor : String?) {
        proveedorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, listaProveedores.map { p -> p.nomprov + " " + p.apeprov })
        binding.cboproveedor2.setAdapter(proveedorAdapter)

        if (!idproveedor.isNullOrBlank()) {
            proveedorViewModel.obtenerProveedorxId(idproveedor) { proveedor ->
                if (proveedor != null) {
                    binding.cboproveedor2.setText("${proveedor.nomprov} ${proveedor.apeprov}", false)
                    selectedProveedorId = proveedor.id
                }
            }
        }

        proveedorViewModel.obtenerProveedores { lis ->
            listaProveedores.clear()
            listaProveedores.addAll(lis)
            val proveedor = lis.map { it.nomprov + " " + it.apeprov }
            proveedorAdapter.clear()
            proveedorAdapter.addAll(proveedor)
            proveedorAdapter.notifyDataSetChanged()
        }
    }

    private fun cargarComboCategoria(idcategoria : String?) {
        categoriaAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            listaCategorias.map { it.nomcategoria })
        binding.cbocategoria2.setAdapter(categoriaAdapter)

        if (!idcategoria.isNullOrBlank()) {
            categoriaViewModel.obtenerCategoriaxId(idcategoria) { categoria ->
                if (categoria != null) {
                    binding.cbocategoria2.setText(categoria.nomcategoria, false)
                    selectedCategoriaId = categoria.id
                }
            }
        }

        categoriaViewModel.obtenerCategoria { lista ->
            listaCategorias.clear()
            listaCategorias.addAll(lista)
            val nombresCategorias = lista.map { it.nomcategoria }
            categoriaAdapter.clear()
            categoriaAdapter.addAll(nombresCategorias)
            categoriaAdapter.notifyDataSetChanged()
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
        if (resultCode == Activity.RESULT_OK) {
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