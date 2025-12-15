package com.example.app_chaski.ui.checkout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_chaski.R
import com.example.app_chaski.data.models.*
import com.example.app_chaski.databinding.ActivityCheckoutBinding
import com.example.app_chaski.ui.direcciones.AgregarDireccionActivity
import com.example.app_chaski.ui.pedido.PedidoExitoActivity
import com.example.app_chaski.utils.CarritoManager
import com.example.app_chaski.utils.CurrencyUtils
import com.example.app_chaski.utils.SessionManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private val viewModel: CheckoutViewModel by viewModels()
    private lateinit var sessionManager: SessionManager
    private lateinit var direccionesAdapter: DireccionesCheckoutAdapter

    private var direccionSeleccionada: DireccionDTO? = null
    private var metodoPagoSeleccionado: String = "EFECTIVO"

    companion object {
        private const val TAG = "CheckoutActivity"
        private const val REQUEST_CODE_DIRECCION = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setupToolbar()
        setupDirecciones()
        setupMetodosPago()
        setupResumen()
        observeViewModel()

        val usuarioId = sessionManager.getUsuarioId()
        val isLoggedIn = sessionManager.isLoggedIn()

        // Log para depuración
        android.util.Log.d("CheckoutActivity", "Usuario ID: $usuarioId")
        android.util.Log.d("CheckoutActivity", "Is Logged In: $isLoggedIn")
        android.util.Log.d("CheckoutActivity", "Usuario Nombre: ${sessionManager.getUsuarioNombre()}")

        if (usuarioId != -1L && isLoggedIn) {
            viewModel.cargarDirecciones(usuarioId)
        } else {
            Snackbar.make(binding.root, "Debe iniciar sesión para continuar", Snackbar.LENGTH_LONG).show()
            finish()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.checkout)
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupDirecciones() {
        // Configurar adapter
        direccionesAdapter = DireccionesCheckoutAdapter { direccion ->
            direccionSeleccionada = direccion
            direccionesAdapter.setDireccionSeleccionada(direccion.id)
        }

        binding.rvDirecciones.apply {
            layoutManager = LinearLayoutManager(this@CheckoutActivity)
            adapter = direccionesAdapter
        }

        // Botón agregar dirección
        binding.btnAgregarDireccion.setOnClickListener {
            val intent = Intent(this, AgregarDireccionActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_DIRECCION)
        }
    }

    private fun setupMetodosPago() {
        binding.rgMetodoPago.setOnCheckedChangeListener { _, checkedId ->
            metodoPagoSeleccionado = when (checkedId) {
                R.id.rb_efectivo -> "EFECTIVO"
                R.id.rb_tarjeta_credito -> "TARJETA_CREDITO"
                R.id.rb_tarjeta_debito -> "TARJETA_DEBITO"
                R.id.rb_yape -> "YAPE"
                else -> "EFECTIVO"
            }
        }
    }

    private fun setupResumen() {
        val subtotal = CarritoManager.calcularSubtotal()
        val costoEnvio = CarritoManager.calcularCostoEnvio()
        val impuestos = subtotal * 0.18 // Asumiendo 18% de impuestos
        val total = subtotal + costoEnvio + impuestos

        binding.apply {
            tvSubtotal.text = CurrencyUtils.formatPrice(subtotal)
            tvCostoEnvio.text = CurrencyUtils.formatPrice(costoEnvio)
            tvImpuestos.text = CurrencyUtils.formatPrice(impuestos)
            tvTotal.text = CurrencyUtils.formatPrice(total)

            btnConfirmarPedido.setOnClickListener {
                confirmarPedido(subtotal, costoEnvio, impuestos, total)
            }
        }
    }

    private fun confirmarPedido(subtotal: Double, costoEnvio: Double, impuestos: Double, total: Double) {
        Log.d(TAG, "=== Iniciando confirmarPedido ===")

        if (direccionSeleccionada == null) {
            Log.e(TAG, "Error: No hay dirección seleccionada")
            Snackbar.make(binding.root, getString(R.string.error_select_address), Snackbar.LENGTH_LONG).show()
            return
        }

        val usuarioId = sessionManager.getUsuarioId()
        val restauranteId = CarritoManager.restauranteId

        Log.d(TAG, "Usuario ID: $usuarioId")
        Log.d(TAG, "Restaurante ID: $restauranteId")
        Log.d(TAG, "¿Usuario logueado?: ${sessionManager.isLoggedIn()}")
        Log.d(TAG, "Nombre usuario: ${sessionManager.getUsuarioNombre()}")
        Log.d(TAG, "Items en carrito: ${CarritoManager.cantidadItems()}")

        // Validar sesión de usuario
        if (usuarioId == -1L) {
            Log.e(TAG, "Error: Usuario ID es -1, sesión no válida")
            Snackbar.make(binding.root, "Sesión no válida. Por favor inicia sesión nuevamente", Snackbar.LENGTH_LONG).show()
            return
        }

        // Validar restaurante
        if (restauranteId == null) {
            Log.e(TAG, "Error: Restaurante ID es null")
            Snackbar.make(binding.root, "Error: Restaurante no válido", Snackbar.LENGTH_LONG).show()
            return
        }

        // Validar que haya items en el carrito
        if (CarritoManager.estaVacio()) {
            Log.e(TAG, "Error: Carrito vacío")
            Snackbar.make(binding.root, "El carrito está vacío", Snackbar.LENGTH_LONG).show()
            return
        }

        Log.d(TAG, "Validaciones pasadas, creando pedido...")

        val detalles = CarritoManager.obtenerItems().map { item ->
            DetallePedidoRequest(
                productoId = item.producto.id,
                cantidad = item.cantidad,
                opciones = item.opcionesSeleccionadas?.map { opcion ->
                    OpcionRequest(
                        opcionId = opcion.id
                    )
                } ?: emptyList()
            )
        }

        val pedidoRequest = PedidoRequest(
            usuarioId = usuarioId,
            restauranteId = restauranteId,
            direccionEntregaId = direccionSeleccionada!!.id,
            notasInstrucciones = binding.etNotas.text.toString().trim().takeIf { it.isNotEmpty() },
            detalles = detalles
        )

        viewModel.crearPedido(pedidoRequest)
    }

    private fun observeViewModel() {
        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnConfirmarPedido.isEnabled = !isLoading
        }

        viewModel.direcciones.observe(this) { direcciones ->
            if (direcciones.isNotEmpty()) {
                binding.rvDirecciones.visibility = View.VISIBLE
                binding.tvDireccion.visibility = View.GONE

                direccionesAdapter.submitList(direcciones)

                // Seleccionar la predeterminada o la primera
                val direccionInicial = direcciones.find { it.predeterminada } ?: direcciones.first()
                direccionSeleccionada = direccionInicial
                direccionesAdapter.setDireccionSeleccionada(direccionInicial.id)
            } else {
                binding.rvDirecciones.visibility = View.GONE
                binding.tvDireccion.visibility = View.VISIBLE
                binding.tvDireccion.text = "No hay direcciones guardadas.\nAgrega una dirección para continuar."
            }
        }

        viewModel.pedidoCreado.observe(this) { pedido ->
            if (pedido != null) {
                // Pedido creado exitosamente, ahora procesar el pago
                val totalFinal = pedido.totalFinal

                val pagoRequest = PagoRequest(
                    pedidoId = pedido.id,
                    monto = totalFinal,
                    metodo = metodoPagoSeleccionado
                )

                viewModel.crearPago(pagoRequest)
            }
        }

        viewModel.pagoCompletado.observe(this) { pago ->
            if (pago != null) {
                // Pago completado exitosamente
                CarritoManager.limpiarCarrito()

                val intent = Intent(this, PedidoExitoActivity::class.java).apply {
                    putExtra("pedido_id", pago.pedidoId)
                }
                startActivity(intent)
                finish()
            }
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_DIRECCION && resultCode == RESULT_OK) {
            // Recargar direcciones después de agregar una nueva
            val usuarioId = sessionManager.getUsuarioId()
            if (usuarioId != -1L) {
                viewModel.cargarDirecciones(usuarioId)
            }
        }
    }
}
