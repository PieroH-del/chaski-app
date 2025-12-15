package com.example.app_chaski.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_chaski.R
import com.example.app_chaski.data.models.CategoriaDTO
import com.example.app_chaski.databinding.FragmentHomeBinding
import com.example.app_chaski.ui.productos.ProductosActivity
import com.example.app_chaski.utils.hide
import com.example.app_chaski.utils.show
import com.example.app_chaski.utils.showToast
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: RestaurantesAdapter

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
        setupRecyclerView()
        setupSearchView()
        setupSwipeRefresh()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = RestaurantesAdapter { restaurante ->
            val intent = Intent(requireContext(), ProductosActivity::class.java).apply {
                putExtra("restaurante_id", restaurante.id)
                putExtra("restaurante_nombre", restaurante.nombre)
                putExtra("costo_envio", restaurante.costoEnvioBase)
                putExtra("imagen_portada", restaurante.imagenPortadaUrl)
            }
            startActivity(intent)
        }

        binding.rvRestaurantes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRestaurantes.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotBlank()) {
                        viewModel.buscarRestaurantes(it)
                    } else {
                        viewModel.cargarRestaurantes()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    viewModel.cargarRestaurantes()
                } else {
                    viewModel.buscarRestaurantes(newText)
                }
                return true
            }
        })
    }

    private fun setupCategorias(categorias: List<CategoriaDTO>) {
        val chipGroup = binding.chipGroupCategorias
        chipGroup.removeAllViews()
        chipGroup.isSingleSelection = true

        // Chip "Todas"
        val chipTodas = Chip(requireContext()).apply {
            text = "Todas"
            isCheckable = true
            id = View.generateViewId()
            chipCornerRadius = resources.getDimension(R.dimen.chip_corner_radius)
            textSize = 14f

            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    chipBackgroundColor = resources.getColorStateList(R.color.primary, null)
                    setTextColor(resources.getColor(R.color.white, null))
                    chipStrokeWidth = 0f
                    viewModel.limpiarFiltroCategoria()
                } else {
                    setChipBackgroundColorResource(R.color.white)
                    setTextColor(resources.getColor(R.color.text_primary, null))
                    chipStrokeWidth = 2f
                    chipStrokeColor = resources.getColorStateList(R.color.accent_light, null)
                }
            }
        }
        chipGroup.addView(chipTodas)

        // Category chips
        categorias.forEach { categoria ->
            val chip = Chip(requireContext()).apply {
                text = categoria.nombre
                isCheckable = true
                id = View.generateViewId()
                chipCornerRadius = resources.getDimension(R.dimen.chip_corner_radius)
                textSize = 14f

                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        chipBackgroundColor = resources.getColorStateList(R.color.primary, null)
                        setTextColor(resources.getColor(R.color.white, null))
                        chipStrokeWidth = 0f
                        viewModel.filtrarPorCategoria(categoria.id)
                    } else {
                        setChipBackgroundColorResource(R.color.white)
                        setTextColor(resources.getColor(R.color.text_primary, null))
                        chipStrokeWidth = 2f
                        chipStrokeColor = resources.getColorStateList(R.color.accent_light, null)
                    }
                }
            }
            chipGroup.addView(chip)
        }

        // Set initial state
        chipGroup.check(chipTodas.id)
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.cargarRestaurantes()
        }
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
                binding.swipeRefresh.isRefreshing = false
            }
        }

        viewModel.categorias.observe(viewLifecycleOwner) { categorias ->
            if (categorias.isNotEmpty()) {
                setupCategorias(categorias)
            }
        }

        viewModel.restaurantes.observe(viewLifecycleOwner) { restaurantes ->
            if (restaurantes.isEmpty()) {
                binding.rvRestaurantes.hide()
                binding.tvEmptyState.show()
            } else {
                binding.rvRestaurantes.show()
                binding.tvEmptyState.hide()
                adapter.submitList(restaurantes)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                showToast(it)
                viewModel.clearError()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
