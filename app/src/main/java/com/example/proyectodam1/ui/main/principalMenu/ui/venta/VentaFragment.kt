package com.example.proyectodam1.ui.main.principalMenu.ui.venta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectodam1.R
import com.example.proyectodam1.databinding.FragmentVentaBinding
import com.example.proyectodam1.model.Venta
import com.example.proyectodam1.network.VentaDataSource
import com.example.proyectodam1.repository.VentaRepository
import com.example.proyectodam1.ui.adapter.venta.VentaAdapter
import com.example.proyectodam1.viewmodel.VentaViewModel
import com.example.proyectodam1.viewmodel.VentaViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class VentaFragment : Fragment() {

    private var _binding: FragmentVentaBinding ? = null

    private val binding get() = _binding!!

    private val ventaViewModel by viewModels<VentaViewModel> {
        VentaViewModelFactory(VentaRepository(VentaDataSource(FirebaseFirestore.getInstance())))
    }

    private var adapter : VentaAdapter ? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVentaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = VentaAdapter {
            modificar(it)
        }

        binding.rvVentas.adapter = adapter
        binding.rvVentas.layoutManager = LinearLayoutManager(requireContext())

        ventaViewModel.obtenerVentas { v ->
            adapter?.listar(v)
        }

        ingresarNuevaVenta()
    }

    private fun ingresarNuevaVenta() {
        binding.btningresarventa.setOnClickListener {
            findNavController().navigate(VentaFragmentDirections.actionNavVentaToVentaInsertarFragment())
        }
    }

    private fun modificar(it: Venta) {
        val bundle = bundleOf(
            "id" to it.id,
            "idcliente" to it.idcliente?.id,
            "idusuario" to it.idusuario?.id,
            "idproducto" to it.idproducto?.id,
            "cantidad" to it.cantidad,
            "precio" to it.precio,
            "subtotal" to it.subtotal,
            "total" to it.total,
            "fechareg" to it.fechareg?.toDate()?.time
        )
        findNavController().navigate(R.id.action_nav_venta_to_ventaModificarFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}