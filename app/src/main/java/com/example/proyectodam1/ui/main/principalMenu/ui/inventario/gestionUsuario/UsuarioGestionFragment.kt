package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionUsuario

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectodam1.R

class UsuarioGestionFragment : Fragment() {

    companion object {
        fun newInstance() = UsuarioGestionFragment()
    }

    private val viewModel: UsuarioGestionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_usuario_gestion, container, false)
    }

    
}