package com.example.proyectodam1.ui.main.principalMenu.ui.venta

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectodam1.R
import com.example.proyectodam1.databinding.FragmentVentaInsertarBinding
import com.example.proyectodam1.model.Cliente
import com.example.proyectodam1.model.Producto
import com.example.proyectodam1.model.Usuario
import com.example.proyectodam1.model.Venta
import com.example.proyectodam1.network.ClienteDataSource
import com.example.proyectodam1.network.ProductoDataSource
import com.example.proyectodam1.network.UsuarioDataSource
import com.example.proyectodam1.network.VentaDataSource
import com.example.proyectodam1.repository.ClienteRepository
import com.example.proyectodam1.repository.ProductoRepository
import com.example.proyectodam1.repository.UsuarioRepository
import com.example.proyectodam1.repository.VentaRepository
import com.example.proyectodam1.viewmodel.ClienteViewModel
import com.example.proyectodam1.viewmodel.ClienteViewModelFactory
import com.example.proyectodam1.viewmodel.ProductoViewModel
import com.example.proyectodam1.viewmodel.ProductoViewModelFactory
import com.example.proyectodam1.viewmodel.UsuarioViewModel
import com.example.proyectodam1.viewmodel.UsuarioViewModelFactory
import com.example.proyectodam1.viewmodel.VentaViewModel
import com.example.proyectodam1.viewmodel.VentaViewModelFactory
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class VentaInsertarFragment : Fragment() {
    private var _binding : FragmentVentaInsertarBinding ? = null
    private val binding get() = _binding!!

    private val ventaViewModel by viewModels<VentaViewModel> {
        VentaViewModelFactory(VentaRepository(VentaDataSource(FirebaseFirestore.getInstance())))
    }

    private val clienteViewModel by viewModels<ClienteViewModel> {
        ClienteViewModelFactory(ClienteRepository(ClienteDataSource(FirebaseFirestore.getInstance())))
    }

    private val productoViewModel by viewModels<ProductoViewModel> {
        ProductoViewModelFactory(ProductoRepository(ProductoDataSource(FirebaseFirestore.getInstance())))
    }

    private val usuarioViewModel by viewModels<UsuarioViewModel> {
        UsuarioViewModelFactory(UsuarioRepository(UsuarioDataSource(FirebaseFirestore.getInstance())))
    }

    private lateinit var clienteAdapter : ArrayAdapter<String>
    private lateinit var usuarioAdapter : ArrayAdapter<String>
    private lateinit var productoAdapter : ArrayAdapter<String>

    private var listaCliente = mutableListOf<Cliente>()
    private var listaUsuario = mutableListOf<Usuario>()
    private var listaProducto = mutableListOf<Producto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVentaInsertarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cargarComboCliente()
        cargarComboUsuario()
        cargarComboProducto()

        autocompleteTotal()
        registrarVenta()
    }

    private fun autocompleteTotal() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {            }

            override fun afterTextChanged(s: Editable?) {
                val cant = binding.txtcantidad.editText?.text.toString()
                val pre = binding.txtprecio.editText?.text.toString()

                if(cant.isNotEmpty() && pre.isNotEmpty()) {
                    val cant2 = cant.toIntOrNull()
                    val pre2 = pre.toDoubleOrNull()

                    if(cant2 != null && pre2 != null) {
                        val total = cant2 * pre2
                        val format = String.format("%.2f", total)
                        binding.txttotal.editText?.setText(format)
                    }else {
                        binding.txttotal.editText?.setText("")
                    }
                }else {
                    binding.txttotal.editText?.setText("")
                }
            }
        }

        binding.txtcantidad.editText?.addTextChangedListener(textWatcher)
        binding.txtprecio.editText?.addTextChangedListener(textWatcher)
    }

    private fun registrarVenta() {
        binding.btnregistrarventa.setOnClickListener {
            binding.cbocliente.error = null
            binding.cbousuario.error = null
            binding.cboproducto.error = null
            binding.txtcantidad.error = null
            binding.txtprecio.error = null
            binding.txttotal.error = null

            val cbocliente = binding.cbocliente2.text.toString()
            val cboproducto = binding.cboproducto2.text.toString()
            val cbousuario = binding.cbousuario2.text.toString()
            val cantidad = binding.txtcantidad.editText?.text.toString()
            val precio = binding.txtprecio.editText?.text.toString()
            val total = binding.txttotal.editText?.text.toString()

            val clienteindex = listaCliente.indexOfFirst { it.nomcli + " " + it.apecli == cbocliente }
            val productoIndex = listaProducto.indexOfFirst { it.marca == cboproducto }
            val usuarioIndex = listaUsuario.indexOfFirst { it.nombre + " " + it.apellido == cbousuario }

            if (clienteindex == -1) {
                binding.cbocliente.error = "Seleccione el cliente"
                return@setOnClickListener
            }

            if (usuarioIndex == -1) {
                binding.cbousuario.error = "Seleccione el usuario"
                return@setOnClickListener
            }

            if (productoIndex == -1) {
                binding.cboproducto.error = "Seleccione el producto"
                return@setOnClickListener
            }

            if(cantidad.isEmpty()) {
                binding.txtcantidad.error = "Ingrese la cantidad"
                return@setOnClickListener
            }else if (!cantidad.matches(Regex("^\\d+$"))) {
                binding.txtcantidad.error = "Solo digitos"
                return@setOnClickListener
            }

            if(precio.isEmpty()) {
                binding.txtprecio.error = "Seleccione el producto"
                return@setOnClickListener
            }

            if(total.isEmpty()) {
                binding.txtprecio.error = "Ingrese la cantidad y/o precio"
                return@setOnClickListener
            }

            val clienteref : DocumentReference = FirebaseFirestore.getInstance().collection("Cliente").document(listaCliente[clienteindex].id!!)
            val usuarioref : DocumentReference = FirebaseFirestore.getInstance().collection("Usuario").document(listaUsuario[usuarioIndex].id)
            val productoref : DocumentReference = FirebaseFirestore.getInstance().collection("Producto").document(listaProducto[productoIndex].id)

            val totalVenta = total.toDouble() + (total.toDouble() * 0.18)

            val totalVentaFormat = String.format("%.2f", totalVenta)
            val date = Date()
            val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val fecha = format.format(date)

            val venta = Venta("",clienteref,usuarioref,productoref, cantidad.toInt(), precio.toDouble(), 18.0, total.toDouble(), totalVentaFormat.toDouble(), fecha)
            ventaViewModel.agregarVenta(venta) {
                if(it) {
                    mensaje("Se registro la venta exitosamente")
                    findNavController().navigate(VentaInsertarFragmentDirections.actionVentaInsertarFragmentToNavVenta())
                }else {
                    mensaje("Error no se pudo registrar")
                }
            }
        }
    }

    private fun mensaje(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

    private fun cargarComboProducto() {
        productoAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, listaProducto.map { it.marca })
        binding.cboproducto2.setAdapter(productoAdapter)
        productoViewModel.obtenerProductos { p ->
            listaProducto.clear()
            listaProducto.addAll(p)
            val producto = p.map { it.marca }
            productoAdapter.clear()
            productoAdapter.addAll(producto)
            productoAdapter.notifyDataSetChanged()
        }

        binding.cboproducto2.setOnItemClickListener { _, _, position, _ ->
            val productoselect = listaProducto[position]
            binding.txtprecio.editText?.setText(productoselect.precio.toString())
        }
    }

    private fun cargarComboUsuario() {
        usuarioAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, listaUsuario.map { it.nombre + " " + it.apellido })
        binding.cbousuario2.setAdapter(usuarioAdapter)
        usuarioViewModel.obtenerUsuario { u ->
            listaUsuario.clear()
            listaUsuario.addAll(u)
            val usuario = u.map { it.nombre + " " + it.apellido }
            usuarioAdapter.clear()
            usuarioAdapter.addAll(usuario)
            usuarioAdapter.notifyDataSetChanged()
        }
    }

    private fun cargarComboCliente() {
        clienteAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, listaCliente.map { it.nomcli + " " + it.apecli })
        binding.cbocliente2.setAdapter(clienteAdapter)
        clienteViewModel.obtenerClientes { cli ->
            listaCliente.clear()
            listaCliente.addAll(cli)
            val cliente = cli.map { it.nomcli + " " + it.apecli }
            clienteAdapter.clear()
            clienteAdapter.addAll(cliente)
            clienteAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}