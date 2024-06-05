package com.example.proyectodam1.ui.main.principalMenu

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectodam1.R
import com.example.proyectodam1.databinding.ActivityPrincipalMenuBinding
import com.example.proyectodam1.network.UsuarioDataSource
import com.example.proyectodam1.repository.UsuarioRepository
import com.example.proyectodam1.viewmodel.UsuarioViewModel
import com.example.proyectodam1.viewmodel.UsuarioViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class PrincipalMenuActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding : ActivityPrincipalMenuBinding

    private val usuarioViewModel by viewModels<UsuarioViewModel> {
        UsuarioViewModelFactory(UsuarioRepository(UsuarioDataSource(FirebaseFirestore.getInstance())))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrincipalMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarPrincipalMenu.toolbar)

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

        rolesLogin()
    }

    private fun rolesLogin() {
        val email = intent?.getStringExtra("email")
        var rolUser : String ? = null
        if (email != null) {
            usuarioViewModel.obtenerRolUsuario(email) {
                rolUser = it?.rol
                if(rolUser.equals("")) {

                }
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_principal_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}