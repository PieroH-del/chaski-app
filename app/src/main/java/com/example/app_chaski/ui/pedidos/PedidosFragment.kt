package com.example.app_chaski.ui.pedidos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_chaski.databinding.FragmentPedidosBinding
import com.example.app_chaski.ui.pedido.DetallePedidoActivity
import com.example.app_chaski.utils.SessionManager
import com.example.app_chaski.utils.hide
import com.example.app_chaski.utils.show
import com.example.app_chaski.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PedidosFragment : Fragment() {

    private var _binding: FragmentPedidosBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PedidosViewModel by viewModels()
    private lateinit var adapter: PedidosAdapter
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPedidosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())
        setupRecyclerView()
        setupSwipeRefresh()
        observeViewModel()

        val usuarioId = sessionManager.getUsuarioId()
        viewModel.cargarPedidos(usuarioId)
    }

    private fun setupRecyclerView() {
        adapter = PedidosAdapter(
            onItemClick = { pedido ->
                val intent = Intent(requireContext(), DetallePedidoActivity::class.java).apply {
                    putExtra("pedido_id", pedido.id)
                }
                startActivity(intent)
            },
            onCancelarClick = { pedido ->
                viewModel.cancelarPedido(pedido.id)
            }
        )

        binding.rvPedidos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPedidos.adapter = adapter
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            val usuarioId = sessionManager.getUsuarioId()
            viewModel.cargarPedidos(usuarioId)
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

        viewModel.pedidos.observe(viewLifecycleOwner) { pedidos ->
            if (pedidos.isEmpty()) {
                binding.rvPedidos.hide()
                binding.emptyStateLayout.show()
            } else {
                binding.rvPedidos.show()
                binding.emptyStateLayout.hide()
                adapter.submitList(pedidos)
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

