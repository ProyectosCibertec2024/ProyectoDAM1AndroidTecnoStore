package com.example.proyectodam1.ui.main.principalMenu.ui.reporte

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectodam1.R
import com.example.proyectodam1.databinding.FragmentReporteBinding

class ReporteFragment : Fragment() {
    private var binding : FragmentReporteBinding ? = null
    private val _binding get() = binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReporteBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}