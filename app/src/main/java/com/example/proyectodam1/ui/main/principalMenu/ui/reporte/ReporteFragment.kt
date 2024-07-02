package com.example.proyectodam1.ui.main.principalMenu.ui.reporte

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectodam1.databinding.FragmentReporteBinding
import com.example.proyectodam1.model.Venta
import com.example.proyectodam1.network.VentaDataSource
import com.example.proyectodam1.repository.VentaRepository
import com.example.proyectodam1.ui.adapter.reporte.ReporteAdapter
import com.example.proyectodam1.utils.FechaFormato
import com.example.proyectodam1.viewmodel.VentaViewModel
import com.example.proyectodam1.viewmodel.VentaViewModelFactory
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class ReporteFragment : Fragment() {
    private var binding : FragmentReporteBinding ? = null
    private val _binding get() = binding!!

    private val ventaViewModel by viewModels<VentaViewModel> {
        VentaViewModelFactory(VentaRepository(VentaDataSource(FirebaseFirestore.getInstance())))
    }

    private var adapter : ReporteAdapter ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReporteBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.txtfecha1value?.setOnClickListener { abrirVentanaFecha1() }
        binding?.txtfecha2value?.setOnClickListener { abrirVentanaFecha2() }

        adapter = ReporteAdapter {
            datos(it)
        }

        _binding.rvreporte.adapter = adapter
        _binding.rvreporte.layoutManager = LinearLayoutManager(requireContext())

        ventaViewModel.obtenerVentas { v ->
            adapter?.listar(v)
        }

        _binding.btnconsultar.setOnClickListener {

            val fecha1 = _binding.txtfecha1.editText?.text.toString()
            val fecha2 = _binding.txtfecha2.editText?.text.toString()

            if(fecha1.isEmpty()) {
                mensaje("Ingrese La fecha Desde")
                return@setOnClickListener
            }

            if(fecha2.isEmpty()) {
                mensaje("Ingrese La fecha Hasta")
                return@setOnClickListener
            }

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val fechaInicio = sdf.parse(fecha1)?.let { it1 -> Timestamp(it1) }
            val fechaFin = sdf.parse(fecha2)?.let { it1 -> Timestamp(it1) }

            Log.d("FechaSeleccionada", "Fecha1: $fechaInicio, Fecha2: $fechaFin")

            ventaViewModel.buscarVentaxFecha(fechaInicio!!, fechaFin!!) { ventas ->
                if (ventas.isNotEmpty()) {
                    adapter?.listar(ventas)
                } else {
                    mensaje("No hay ventas en este rango de fechas")
                }
            }
        }

        _binding.btnlistartodo.setOnClickListener {
            ventaViewModel.obtenerVentas { v ->
                adapter?.listar(v)
            }
        }

    }

    private fun datos(it: Venta) {

    }

    private fun mensaje(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

    private fun abrirVentanaFecha1() {
        val fecha = FechaFormato {dia, mes, anio -> fechaSeleccionada1(dia, mes, anio)}
        fecha.show(parentFragmentManager, "datePicker")
    }

    fun fechaSeleccionada1(dia:Int, mes:Int, anio:Int) {
        val formattedDate = String.format("%d-%02d-%02d", anio, mes + 1, dia)
        binding?.txtfecha1value?.setText(formattedDate)
    }

    private fun abrirVentanaFecha2() {
        val fecha = FechaFormato {dia, mes, anio -> fechaSeleccionada2(dia, mes, anio)}
        fecha.show(parentFragmentManager, "datePicker")
    }

    fun fechaSeleccionada2(dia:Int, mes:Int, anio:Int) {
        val formattedDate = String.format("%d-%02d-%02d", anio, mes + 1, dia)
        binding?.txtfecha2value?.setText(formattedDate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}