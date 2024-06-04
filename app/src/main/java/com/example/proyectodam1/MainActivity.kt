package com.example.proyectodam1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectodam1.databinding.ActivityMainBinding
import com.example.proyectodam1.network.UsuarioDataSource
import com.example.proyectodam1.repository.UsuarioRepository
import com.example.proyectodam1.ui.main.principalMenu.PrincipalMenuActivity
import com.example.proyectodam1.viewmodel.UsuarioViewModel
import com.example.proyectodam1.viewmodel.UsuarioViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private val usuarioViewModel by viewModels<UsuarioViewModel> {
        UsuarioViewModelFactory(UsuarioRepository(UsuarioDataSource(FirebaseFirestore.getInstance())))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnIngresar.setOnClickListener {
            binding.txtEmailLogin.error = null
            binding.txtPasswordLogin.error = null

            val email = binding.txtEmailLogin.editText?.text.toString()
            if(email.isEmpty()) {
                binding.txtEmailLogin.error = "Ingrese El Email"
                return@setOnClickListener
            }else
                if(!email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}"))) {
                    binding.txtEmailLogin.error = "Formato Incorrecto"
                    return@setOnClickListener
                }

            val password = binding.txtPasswordLogin.editText?.text.toString()
            if(password.isEmpty()) {
                binding.txtPasswordLogin.error = "Ingrese El Password"
                return@setOnClickListener
            }else
                if(!password.matches(Regex("[a-zA-Z0-9á-ý@._%+-]{3,15}"))) {
                    binding.txtPasswordLogin.error = "La contraseña debe tener entre 3 y 15 digitos"
                    return@setOnClickListener
                }


            usuarioViewModel.logueoUsuario(email, password) {
                if(email.isNotEmpty() && password.isNotEmpty()) {
                    if(it) {
                        val intent = Intent(this, PrincipalMenuActivity::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                    }else {
                        mensaje("Intente de nuevo, Usuario o Password Incorrecto")
                    }
                }else {
                    mensaje("Complete Los Campos")
                }
            }
        }

    }

    private fun mensaje(men: String) {
        Toast.makeText(this, men, Toast.LENGTH_SHORT).show()
    }
}