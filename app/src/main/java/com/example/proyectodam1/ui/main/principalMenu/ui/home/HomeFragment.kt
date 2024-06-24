package com.example.proyectodam1.ui.main.principalMenu.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.proyectodam1.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        obtenerLinks()
    }

    private fun obtenerLinks() {
        binding.ivTwitter.setOnClickListener {
            val twitter = "https://x.com/";
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Desea Continuar?")
                .setMessage("Se Redirigira a la app twitter")
                .setPositiveButton("Aceptar") { _, _ ->
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(twitter)
                    startActivity(intent)
                }.setNegativeButton("Cancelar", null)
                .show()
        }

        binding.ivInstagram.setOnClickListener {
            val instagram = "https://www.instagram.com/";
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Desea Continuar?")
                .setMessage("Se Redirigira a la app instagram")
                .setPositiveButton("Aceptar") { _, _ ->
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(instagram)
                    startActivity(intent)
                }.setNegativeButton("Cancelar", null)
                .show()
        }

        binding.ivFacebook.setOnClickListener {
            val facebook = "https://www.facebook.com/";
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Desea Continuar?")
                .setMessage("Se Redirigira a la app Facebook")
                .setPositiveButton("Aceptar") { _, _ ->
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(facebook)
                    startActivity(intent)
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}