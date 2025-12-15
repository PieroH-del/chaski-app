package com.example.app_chaski.data.remote

import com.example.app_chaski.data.models.*
import retrofit2.http.*

interface ChaskiApiService {

    // ========== AUTENTICACIÓN ==========
    @POST("usuarios/registro")
    suspend fun registrarUsuario(
        @Body request: UsuarioRegistroRequest
    ): UsuarioDTO

    @POST("usuarios/login")
    suspend fun loginUsuario(
        @Body request: LoginRequest
    ): UsuarioDTO

    @GET("usuarios/{id}")
    suspend fun obtenerUsuario(
        @Path("id") usuarioId: Long
    ): UsuarioDTO

    @PUT("usuarios/{id}")
    suspend fun actualizarUsuario(
        @Path("id") usuarioId: Long,
        @Body request: UsuarioActualizacionRequest
    ): UsuarioDTO

    // ========== RESTAURANTES ==========
    @GET("restaurantes")
    suspend fun obtenerRestaurantes(): List<RestauranteDTO>

    @GET("restaurantes/{id}")
    suspend fun obtenerRestaurante(
        @Path("id") restauranteId: Long
    ): RestauranteDTO

    @GET("restaurantes/buscar")
    suspend fun buscarRestaurantes(
        @Query("nombre") nombre: String
    ): List<RestauranteDTO>

    @GET("restaurantes/filtrar/disponibilidad")
    suspend fun filtrarRestaurantesPorDisponibilidad(
        @Query("estaAbierto") estaAbierto: Boolean
    ): List<RestauranteDTO>

    @GET("restaurantes/filtrar/categoria/{categoriaId}")
    suspend fun filtrarRestaurantesPorCategoria(
        @Path("categoriaId") categoriaId: Long
    ): List<RestauranteDTO>

    // ========== CATEGORÍAS ==========
    @GET("categorias")
    suspend fun obtenerCategorias(): List<CategoriaDTO>

    @GET("categorias/{id}")
    suspend fun obtenerCategoria(
        @Path("id") categoriaId: Long
    ): CategoriaDTO

    // ========== PRODUCTOS ==========
    @GET("productos/restaurante/{restauranteId}")
    suspend fun obtenerProductosPorRestaurante(
        @Path("restauranteId") restauranteId: Long
    ): List<ProductoDTO>

    @GET("productos/restaurante/{restauranteId}/disponibles")
    suspend fun obtenerProductosDisponibles(
        @Path("restauranteId") restauranteId: Long
    ): List<ProductoDTO>

    @GET("productos/{id}")
    suspend fun obtenerProducto(
        @Path("id") productoId: Long
    ): ProductoDTO

    // ========== DIRECCIONES ==========
    @GET("direcciones/usuario/{usuarioId}")
    suspend fun obtenerDireccionesPorUsuario(
        @Path("usuarioId") usuarioId: Long
    ): List<DireccionDTO>

    @POST("direcciones")
    suspend fun crearDireccion(
        @Body request: DireccionRequest
    ): DireccionDTO

    @PUT("direcciones/{id}")
    suspend fun actualizarDireccion(
        @Path("id") direccionId: Long,
        @Body request: DireccionRequest
    ): DireccionDTO

    @DELETE("direcciones/{id}")
    suspend fun eliminarDireccion(
        @Path("id") direccionId: Long
    )

    // ========== PEDIDOS ==========
    @POST("pedidos")
    suspend fun crearPedido(
        @Body request: PedidoRequest
    ): PedidoDTO

    @GET("pedidos/usuario/{usuarioId}")
    suspend fun obtenerPedidosPorUsuario(
        @Path("usuarioId") usuarioId: Long
    ): List<PedidoDTO>

    @GET("pedidos/{id}")
    suspend fun obtenerPedido(
        @Path("id") pedidoId: Long
    ): PedidoDTO

    @PUT("pedidos/{id}/cancelar")
    suspend fun cancelarPedido(
        @Path("id") pedidoId: Long
    ): PedidoDTO

    // ========== PAGOS ==========
    @POST("pagos")
    suspend fun crearPago(
        @Body request: PagoRequest
    ): PagoDTO

    @GET("pagos/pedido/{pedidoId}")
    suspend fun obtenerPagoPorPedido(
        @Path("pedidoId") pedidoId: Long
    ): PagoDTO
}
