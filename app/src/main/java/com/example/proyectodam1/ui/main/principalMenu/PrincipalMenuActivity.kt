package com.example.proyectodam1.ui.main.principalMenu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import com.bumptech.glide.Glide
import com.example.proyectodam1.MainActivity
import com.example.proyectodam1.R
import com.example.proyectodam1.databinding.ActivityPrincipalMenuBinding
import com.example.proyectodam1.network.UsuarioDataSource
import com.example.proyectodam1.repository.UsuarioRepository
import com.example.proyectodam1.viewmodel.UsuarioViewModel
import com.example.proyectodam1.viewmodel.UsuarioViewModelFactory
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class PrincipalMenuActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding : ActivityPrincipalMenuBinding
    private lateinit var navigationView: NavigationView
    private var rolUsuario: String? = null

    private val usuarioViewModel by viewModels<UsuarioViewModel> {
        UsuarioViewModelFactory(UsuarioRepository(UsuarioDataSource(FirebaseFirestore.getInstance())))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrincipalMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarPrincipalMenu.toolbar)

        auth = Firebase.auth
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_principal_menu)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_inicio, R.id.nav_inventary, R.id.nav_venta, R.id.nav_report
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navigationView = findViewById(R.id.nav_view)

        rolesLogin()
        cerrarSesion()
    }

    private fun cerrarSesion() {
        navigationView = findViewById(R.id.nav_view)
        val menu = navigationView.menu
        val cerrarSesion = menu.findItem(R.id.login)
        cerrarSesion.setOnMenuItemClickListener {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            true
        }
    }

    private fun rolesLogin() {
        val email = intent?.getStringExtra("email")
        Log.i("Email","Email: $email")
        if (email != null) {
            usuarioViewModel.obtenerRolUsuario(email) {
                Log.i("NOMUSUARIO: ", "USUARIO: ${it?.nombre}")
                val menu = navigationView.menu
                val inicio = menu.findItem(R.id.nav_inicio)
                val inventario = menu.findItem(R.id.nav_inventary)
                val venta = menu.findItem(R.id.nav_venta)
                val reporte = menu.findItem(R.id.nav_report)
                val nomuser = findViewById<TextView>(R.id.txtnomusuariologin)
                val roluser = findViewById<TextView>(R.id.txtrolusuariologin)
                val imguser = findViewById<ImageView>(R.id.imageView)
                nomuser.text = it?.nombre
                Glide.with(this).load(it?.urlimg).into(imguser)
                it?.rol?.get()?.addOnSuccessListener { doc ->
                    if(doc != null && doc.exists()) {
                        rolUsuario = doc.id
                        val rol = doc.getString("nomrol")
                        Log.i("ROL: ", "USUARIO: $rol")
                        roluser.text = rol
                        if(rol == "admin") {
                            inicio.isVisible = true
                            inventario.isVisible = true
                            venta.isVisible = true
                            reporte.isVisible = true
                        }else {
                            inicio.isVisible = true
                            inventario.isVisible = false
                            venta.isVisible = false
                            reporte.isVisible = true
                        }
                    }else {
                        Log.e("Usuario", "No Se Identifico, el rol no existe")
                    }
                }?.addOnFailureListener {
                    Log.e("USUARIO", "ERROR AL OBTENER ROL : " + it.localizedMessage)
                }
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_principal_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}