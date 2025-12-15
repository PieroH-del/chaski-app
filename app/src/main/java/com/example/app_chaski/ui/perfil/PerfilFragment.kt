package com.example.app_chaski.ui.perfil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.app_chaski.R
import com.example.app_chaski.databinding.FragmentPerfilBinding
import com.example.app_chaski.ui.auth.LoginActivity
import com.example.app_chaski.ui.direcciones.DireccionesActivity
import com.example.app_chaski.ui.perfil.EditarPerfilActivity
import com.example.app_chaski.utils.SessionManager
import com.example.app_chaski.utils.loadCircularImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())

        setupViews()
        loadUserData()
    }

    private fun setupViews() {
        binding.apply {
            layoutEditarPerfil.setOnClickListener {
                startActivity(Intent(requireContext(), EditarPerfilActivity::class.java))
            }

            layoutDirecciones.setOnClickListener {
                startActivity(Intent(requireContext(), DireccionesActivity::class.java))
            }

            layoutMisPedidos.setOnClickListener {
                // Navegar al tab de pedidos
                requireActivity().findViewById<View>(R.id.nav_pedidos)?.performClick()
            }

            layoutCerrarSesion.setOnClickListener {
                mostrarDialogCerrarSesion()
            }
        }
    }

    private fun loadUserData() {
        binding.apply {
            tvNombre.text = sessionManager.getUsuarioNombre()
            tvEmail.text = sessionManager.getUsuarioEmail()
            ivPerfil.loadCircularImage(sessionManager.getUsuarioImagen())
        }
    }

    private fun mostrarDialogCerrarSesion() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.logout)
            .setMessage(R.string.logout_confirm)
            .setPositiveButton(R.string.yes) { _, _ ->
                cerrarSesion()
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    private fun cerrarSesion() {
        sessionManager.cerrarSesion()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onResume() {
        super.onResume()
        loadUserData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

