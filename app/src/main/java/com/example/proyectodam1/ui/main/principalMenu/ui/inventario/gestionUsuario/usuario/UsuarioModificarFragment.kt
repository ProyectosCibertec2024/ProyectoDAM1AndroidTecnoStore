package com.example.proyectodam1.ui.main.principalMenu.ui.inventario.gestionUsuario.usuario

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
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
import com.example.proyectodam1.databinding.FragmentUsuarioModificarBinding
import com.example.proyectodam1.firebasestorage.SubirStorage
import com.example.proyectodam1.model.Rol
import com.example.proyectodam1.model.Usuario
import com.example.proyectodam1.network.RolDataSource
import com.example.proyectodam1.network.UsuarioDataSource
import com.example.proyectodam1.repository.RolRepository
import com.example.proyectodam1.repository.UsuarioRepository
import com.example.proyectodam1.viewmodel.RolViewModel
import com.example.proyectodam1.viewmodel.RolViewModelFactory
import com.example.proyectodam1.viewmodel.UsuarioViewModel
import com.example.proyectodam1.viewmodel.UsuarioViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class UsuarioModificarFragment : Fragment() {
    private var _binding : FragmentUsuarioModificarBinding ? = null
    private val binding get() = _binding!!

    private val usuarioViewModel by viewModels<UsuarioViewModel> {
        UsuarioViewModelFactory(UsuarioRepository(UsuarioDataSource(FirebaseFirestore.getInstance())))
    }

    private val rolViewModel by viewModels<RolViewModel> {
        RolViewModelFactory(RolRepository(RolDataSource(FirebaseFirestore.getInstance())))
    }

    private lateinit var rolAdapter : ArrayAdapter<String>
    private var listaRoles = mutableListOf<Rol>()

    private var selectedrolId: String? = null

    private var selectImagen : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsuarioModificarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val obid = arguments?.getString("id")
        val obnom = arguments?.getString("nombre")
        val obape = arguments?.getString("apellido")
        val obemail = arguments?.getString("email")
        val obpass = arguments?.getString("password")
        val oburl = arguments?.getString("urlimg")
        val obrol = arguments?.getString("rol")

        binding.txtid.editText?.setText(obid)
        binding.txtnombre.editText?.setText(obnom)
        binding.txtapellido.editText?.setText(obape)
        binding.txtemail.editText?.setText(obemail)
        binding.txtpassword.editText?.setText(obpass)
        Glide.with(requireContext()).load(oburl).into(binding.imgusuariocargar)
        binding.cborol2.setText(obrol)

        cargarComboRoles(obrol)
        abrirGaleria()

        modificarUsuario()
    }

    private fun modificarUsuario() {
        binding.btnguardar.setOnClickListener {
            val usuarioAuth = FirebaseAuth.getInstance().currentUser
            if (usuarioAuth != null) {

                binding.txtnombre.error = null
                binding.txtapellido.error = null
                binding.txtemail.error = null
                binding.txtpassword.error = null
                binding.cborol.error = null

                val id = binding.txtid.editText?.text.toString()
                val nombre = binding.txtnombre.editText?.text.toString()
                val apellido = binding.txtapellido.editText?.text.toString()
                val email = binding.txtemail.editText?.text.toString()
                val password = binding.txtpassword.editText?.text.toString()
                val rol = binding.cborol2.text.toString()

                if (nombre.isEmpty()) {
                    binding.txtnombre.error = "Ingrese El Nombre"
                    return@setOnClickListener
                }

                if (apellido.isEmpty()) {
                    binding.txtapellido.error = "Ingrese El Apellido"
                    return@setOnClickListener
                }

                if(email.isEmpty()) {
                    binding.txtemail.error = "Ingrese El Email"
                    return@setOnClickListener
                }else
                    if(!email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}"))) {
                        binding.txtemail.error = "Formato Incorrecto"
                        return@setOnClickListener
                    }

                if(password.isEmpty()) {
                    binding.txtpassword.error = "Ingrese El Password"
                    return@setOnClickListener
                }else
                    if(!password.matches(Regex("[a-zA-Z0-9á-ý@._%+-]{3,15}"))) {
                        binding.txtpassword.error = "La contraseña debe tener entre 3 y 15 digitos"
                        return@setOnClickListener
                    }

                val rolIndex = listaRoles.indexOfFirst { it.nomrol == rol }

                if (rolIndex == -1) {
                    binding.cborol.error = "Ingrese El Nombre"
                    return@setOnClickListener
                }

                val rolref : DocumentReference = FirebaseFirestore.getInstance().collection("Rol").document(listaRoles[rolIndex].id)

                if(selectImagen != null) {
                    val subirStorage = SubirStorage()
                    subirStorage.uploadImage(requireContext(), selectImagen!!, "users") { success, download ->
                        if(success && download != null) {
                            val nomimagen = subirStorage.getFileName(requireContext(), selectImagen!!)
                            val usuario = Usuario(id, nombre, apellido, email, password, rolref, nomimagen, download)
                            usuarioViewModel.actualizarUsuario(id, usuario) {
                                if (it) {
                                    mensaje("Se guardo exitosamente")
                                    findNavController().navigate(UsuarioModificarFragmentDirections.actionUsuarioModificarFragmentToUsuarioFragment())
                                }else {
                                    mensaje("Error en modificar el usuario")
                                }
                            }
                        }else {
                            mensaje("Error en subir la imagen")
                        }
                    }
                }else {
                    usuarioViewModel.obtenerUsuarioxId(id) { user ->
                        val usuario = Usuario(id, nombre, apellido, email, password, rolref, user?.nomimg!!, user.urlimg)
                        usuarioViewModel.actualizarUsuario(id, usuario) {
                            if (it) {
                                mensaje("Se modifico exitosamente")
                                findNavController().navigate(UsuarioModificarFragmentDirections.actionUsuarioModificarFragmentToUsuarioFragment())
                            }else {
                                mensaje("Error en modificar el usuario")
                            }
                        }
                    }
                }

            }else {
                Log.e("Modificar Usuario", "Usuario no autenticado")
                return@setOnClickListener
            }

        }
    }

    private fun cargarComboRoles(idrol : String?) {
        rolAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, listaRoles.map { it.nomrol })
        binding.cborol2.setAdapter(rolAdapter)
        if(!idrol.isNullOrBlank()) {
            rolViewModel.obtenerRolxIdrol(idrol) { rol ->
                if(rol != null) {
                    binding.cborol2.setText(rol.nomrol, false)
                    selectedrolId = rol.id
                }
            }
        }
        rolViewModel.obtenerRoles { r ->
            listaRoles.clear()
            listaRoles.addAll(r)
            val nomrol = r.map { it.nomrol }
            rolAdapter.clear()
            rolAdapter.addAll(nomrol)
            rolAdapter.notifyDataSetChanged()
        }
    }

    private fun mensaje(s : String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

    private fun abrirGaleria() {
        binding.btnseleccionarusuario.setOnClickListener {
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
                        binding.imgusuariocargar.setImageURI(uri)
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