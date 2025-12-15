package com.example.app_chaski.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.app_chaski.R
import com.example.app_chaski.databinding.ActivityMainBinding
import com.example.app_chaski.ui.carrito.CarritoActivity
import com.example.app_chaski.ui.home.HomeFragment
import com.example.app_chaski.ui.pedidos.PedidosFragment
import com.example.app_chaski.ui.perfil.PerfilFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_carrito -> {
                    // Abrir CarritoActivity en lugar de un fragment
                    startActivity(Intent(this, CarritoActivity::class.java))
                    false // No cambiar el item seleccionado
                }
                R.id.nav_pedidos -> {
                    loadFragment(PedidosFragment())
                    true
                }
                R.id.nav_perfil -> {
                    loadFragment(PerfilFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }
}

