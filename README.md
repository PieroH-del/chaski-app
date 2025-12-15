# 📱 Chaski - Aplicación de Delivery Multi-Restaurante

<div align="center">

![Chaski Logo](https://img.shields.io/badge/Chaski-Multi%20Delivery-F5A623?style=for-the-badge&logo=android&logoColor=white)

### Aplicación Android de Delivery de Comida

**Desarrollo Móvil 1 - IDAT**

[![Android](https://img.shields.io/badge/Android-5.0%2B-3DDC84?style=flat-square&logo=android&logoColor=white)](https://www.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9-7F52FF?style=flat-square&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Material Design 3](https://img.shields.io/badge/Material%20Design-3-757575?style=flat-square&logo=material-design&logoColor=white)](https://m3.material.io/)

</div>

---

## 👥 Integrantes del Proyecto

| Nombre | Rol |
|--------|-----|
| **Ismael Galve Luyo** | Desarrollador Android |
| **Piero Leon** | Desarrollador Android |

---

## 📖 Descripción del Proyecto

**Chaski** es una aplicación móvil Android que permite a los usuarios explorar restaurantes, navegar por menús, agregar productos al carrito y realizar pedidos de comida a domicilio. La aplicación implementa un sistema completo de delivery con seguimiento de pedidos, gestión de direcciones y múltiples métodos de pago.

El proyecto fue desarrollado como parte del curso **Desarrollo Móvil 1** en el **Instituto IDAT**, aplicando las mejores prácticas de desarrollo Android moderno.

---

## ✨ Características Principales

### 🏠 Funcionalidades Implementadas

#### 1. **Autenticación de Usuarios**
- ✅ Registro de nuevos usuarios
- ✅ Inicio de sesión con validación
- ✅ Gestión de sesiones persistentes
- ✅ Recuperación de contraseña

#### 2. **Exploración de Restaurantes**
- ✅ Lista de restaurantes disponibles
- ✅ Filtros por categoría (Hamburguesas, Pizza, Pollo, etc.)
- ✅ Búsqueda por nombre
- ✅ Vista de detalle con información completa
- ✅ Indicador de estado (Abierto/Cerrado)

#### 3. **Catálogo de Productos**
- ✅ Menú completo por restaurante
- ✅ Productos con imágenes, descripciones y precios
- ✅ Opciones personalizables (extras, tamaños, etc.)
- ✅ Selector de cantidad

#### 4. **Carrito de Compras**
- ✅ Agregar productos al carrito
- ✅ Modificar cantidades
- ✅ Eliminar productos
- ✅ Cálculo automático de subtotal, envío e impuestos
- ✅ Validación de productos del mismo restaurante

#### 5. **Sistema de Pedidos**
- ✅ Proceso de checkout completo
- ✅ Selección de dirección de entrega
- ✅ Múltiples métodos de pago (Efectivo, Tarjeta, Yape)
- ✅ Notas e instrucciones especiales
- ✅ Confirmación de pedido
- ✅ Historial de pedidos

#### 6. **Gestión de Direcciones**
- ✅ Agregar múltiples direcciones
- ✅ Editar direcciones existentes
- ✅ Eliminar direcciones
- ✅ Marcar dirección como predeterminada
- ✅ Campos de referencia

#### 7. **Perfil de Usuario**
- ✅ Visualización de datos personales
- ✅ Edición de información
- ✅ Gestión de direcciones
- ✅ Historial de pedidos
- ✅ Cerrar sesión

---

## 🏗️ Arquitectura y Tecnologías

### Arquitectura
- **Patrón:** MVVM (Model-View-ViewModel)
- **Inyección de Dependencias:** Dagger Hilt
- **Navegación:** Android Navigation Component

### Stack Tecnológico

#### Core
- **Lenguaje:** Kotlin 1.9
- **Min SDK:** Android 5.0 (API 21)
- **Target SDK:** Android 14 (API 34)
- **Build System:** Gradle Kotlin DSL

#### UI/UX
- **Material Design 3** - Componentes modernos y diseño adaptativo
- **View Binding** - Acceso type-safe a las vistas
- **RecyclerView** - Listas optimizadas
- **CardView** - Tarjetas de contenido
- **BottomSheet** - Diálogos modales
- **SwipeRefreshLayout** - Pull to refresh

#### Networking
- **Retrofit 2** - Cliente HTTP type-safe
- **OkHttp 4** - Cliente HTTP eficiente
- **Gson** - Serialización/deserialización JSON

#### Asynchronous Programming
- **Kotlin Coroutines** - Programación asíncrona
- **Flow** - Streams de datos reactivos
- **LiveData** - Observación de datos lifecycle-aware

#### Dependency Injection
- **Dagger Hilt** - Inyección de dependencias con menos boilerplate

#### Image Loading
- **Glide** - Carga y caché de imágenes eficiente

#### Logging
- **Timber** - Logging avanzado

### Estructura del Proyecto

```
app/
├── src/main/
│   ├── java/com/example/app_chaski/
│   │   ├── data/
│   │   │   ├── models/          # DTOs y Request models
│   │   │   ├── remote/          # API Service
│   │   │   └── repository/      # Repositorios
│   │   ├── di/                  # Módulos Hilt
│   │   ├── ui/
│   │   │   ├── auth/            # Login y Registro
│   │   │   ├── home/            # Pantalla principal
│   │   │   ├── productos/       # Lista de productos
│   │   │   ├── carrito/         # Carrito de compras
│   │   │   ├── checkout/        # Proceso de pago
│   │   │   ├── pedidos/         # Historial de pedidos
│   │   │   ├── direcciones/     # Gestión de direcciones
│   │   │   └── perfil/          # Perfil de usuario
│   │   └── utils/               # Utilidades y helpers
│   └── res/
│       ├── layout/              # XML layouts
│       ├── drawable/            # Recursos gráficos
│       ├── values/              # Strings, colors, themes
│       └── navigation/          # Gráficos de navegación
```

---

## 🎨 Diseño y Branding

### Paleta de Colores

| Color | Hex | Uso |
|-------|-----|-----|
| **Primary** | `#7B2CBF` | Morado principal - Elementos principales |
| **Primary Variant** | `#5A189A` | Morado oscuro - Sombras y énfasis |
| **Secondary** | `#FFB703` | Amarillo - Acciones secundarias |
| **Secondary Variant** | `#FB8500` | Naranja - Llamadas a la acción |
| **Background** | `#FFFFFF` | Fondo principal |
| **Surface** | `#FFFFFF` | Superficies de tarjetas |

### Logo

El logo de Chaski representa un repartidor en movimiento con un paquete, transmitiendo velocidad y eficiencia en el servicio de delivery.

---

## 🔗 Integración con Backend

### API REST
- **Base URL:** `https://chaski-backend.azurewebsites.net/api`
- **Formato:** JSON
- **Autenticación:** BCrypt para passwords

### Endpoints Principales

#### Usuarios
- `POST /usuarios/registro` - Registro de usuario
- `POST /usuarios/login` - Inicio de sesión
- `GET /usuarios/{id}` - Obtener perfil
- `PUT /usuarios/{id}` - Actualizar perfil

#### Restaurantes
- `GET /restaurantes` - Listar restaurantes
- `GET /restaurantes/{id}` - Detalle de restaurante
- `GET /restaurantes/buscar` - Buscar por nombre
- `GET /restaurantes/categoria/{id}` - Filtrar por categoría

#### Productos
- `GET /productos/restaurante/{id}` - Productos por restaurante
- `GET /productos/{id}` - Detalle de producto

#### Pedidos
- `POST /pedidos` - Crear pedido
- `GET /pedidos/usuario/{id}` - Historial de pedidos
- `GET /pedidos/{id}` - Detalle de pedido
- `PUT /pedidos/{id}/cancelar` - Cancelar pedido

#### Direcciones
- `POST /direcciones` - Crear dirección
- `GET /direcciones/usuario/{id}` - Listar direcciones
- `PUT /direcciones/{id}` - Actualizar dirección
- `DELETE /direcciones/{id}` - Eliminar dirección

#### Pagos
- `POST /pagos` - Procesar pago
- `GET /pagos/pedido/{id}` - Obtener pago por pedido

---

## 🚀 Instalación y Configuración

### Requisitos Previos
- Android Studio Hedgehog | 2023.1.1 o superior
- JDK 17 o superior
- SDK de Android (API 34)
- Dispositivo físico o emulador con Android 5.0+

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/app-chaski.git
   cd app-chaski
   ```

2. **Abrir en Android Studio**
   - Abrir Android Studio
   - File → Open → Seleccionar la carpeta del proyecto

3. **Configurar el proyecto**
   - Android Studio descargará automáticamente las dependencias
   - Esperar a que Gradle termine de sincronizar

4. **Ejecutar la aplicación**
   - Conectar un dispositivo Android o iniciar un emulador
   - Clic en el botón "Run" (▶️)

### Configuración del Backend

Si deseas usar un backend local, actualiza la URL base en `RetrofitModule.kt`:

```kotlin
private const val BASE_URL = "http://TU_IP_LOCAL:8080/api/"
```

---

## 📱 Capturas de Pantalla

### Pantallas Principales

#### Autenticación
- Login elegante con validaciones
- Registro de usuarios nuevos

#### Inicio
- Lista de restaurantes con categorías
- Filtros y búsqueda

#### Productos
- Catálogo completo del menú
- Detalles con opciones personalizables

#### Carrito
- Resumen de productos
- Cálculos automáticos de costos

#### Checkout
- Selección de dirección
- Métodos de pago
- Resumen del pedido

#### Pedidos
- Historial completo
- Detalles con seguimiento

---

## 🧪 Testing

### Pruebas Realizadas

- ✅ Pruebas unitarias de ViewModels
- ✅ Pruebas de integración de repositorios
- ✅ Pruebas de UI con Espresso
- ✅ Pruebas de navegación
- ✅ Validación de formularios
- ✅ Manejo de errores de red

---

## 📝 Características Destacadas

### 1. **Empty States Bien Diseñados**
Cada pantalla sin contenido muestra un mensaje claro y amigable al usuario.

### 2. **Manejo de Errores Robusto**
- Mensajes de error específicos
- Validaciones en tiempo real
- Retroalimentación visual clara

### 3. **UX Optimizada**
- Transiciones suaves
- Loading states
- Pull to refresh
- Botones deshabilitados durante operaciones

### 4. **Persistencia de Sesión**
- SharedPreferences para datos de usuario
- Sesión permanente hasta cerrar sesión

### 5. **Carrito Inteligente**
- Validación de restaurante único
- Limpieza automática al cambiar de restaurante
- Cálculos precisos

### 6. **Gestión de Direcciones Completa**
- CRUD completo
- Dirección predeterminada
- Referencias y alias

---

## 🔐 Seguridad

- ✅ Passwords hasheados con BCrypt en el backend
- ✅ Validación de datos en cliente y servidor
- ✅ Manejo seguro de tokens de sesión
- ✅ Comunicación HTTPS
- ✅ Sanitización de inputs

---

## 📊 Métricas del Proyecto

| Métrica | Valor |
|---------|-------|
| **Líneas de código** | ~8,000+ |
| **Actividades** | 12 |
| **Fragmentos** | 5 |
| **ViewModels** | 10 |
| **Repositorios** | 7 |
| **Layouts XML** | 25+ |
| **Tiempo de desarrollo** | 4 semanas |

---

## 🐛 Problemas Conocidos y Soluciones

### ✅ Resueltos
1. **Error "Sesión no válida"** - Solucionado con validación correcta de SessionManager
2. **Restaurante ID null** - Corregido guardando el ID al agregar productos
3. **Empty state en pedidos** - Implementado mensaje visual
4. **Direcciones no se recargan** - Agregado RESULT_OK en AgregarDireccionActivity

---

## 🔮 Futuras Mejoras

### Features Propuestas
- [ ] Integración con Google Maps para seleccionar ubicación
- [ ] Notificaciones push para seguimiento de pedidos
- [ ] Chat en tiempo real con el repartidor
- [ ] Sistema de calificaciones y reseñas
- [ ] Cupones y promociones
- [ ] Favoritos y productos guardados
- [ ] Modo oscuro completo
- [ ] Soporte para múltiples idiomas

---

## 📚 Documentación Adicional

El proyecto incluye documentación detallada en los siguientes archivos:

- `API_DOCUMENTATION.md` - Documentación completa de la API
- `CHECKOUT_IMPLEMENTATION.md` - Implementación del checkout
- `SOLUCION_RESTAURANTE_ID.md` - Solución al problema del restaurante ID
- `EMPTY_STATE_PEDIDOS.md` - Empty state de pedidos
- `LOGO_CHASKI_IMPLEMENTADO.md` - Implementación del logo

---

## 🎓 Aprendizajes del Curso

Este proyecto nos permitió aplicar y consolidar conocimientos en:

- ✅ Desarrollo Android con Kotlin
- ✅ Arquitectura MVVM
- ✅ Inyección de dependencias con Hilt
- ✅ Consumo de APIs REST
- ✅ Diseño de interfaces con Material Design
- ✅ Manejo de estados y ciclo de vida
- ✅ Programación asíncrona con Coroutines
- ✅ Persistencia de datos con SharedPreferences
- ✅ Navegación entre pantallas
- ✅ Trabajo en equipo y control de versiones

---

## 🙏 Agradecimientos

- **Instituto IDAT** - Por la formación en Desarrollo Móvil
- **Profesor(a)** - Por la guía y mentoría durante el proyecto
- **Compañeros de clase** - Por el feedback y apoyo

---

## 📄 Licencia

Este proyecto fue desarrollado con fines educativos como parte del curso de Desarrollo Móvil 1 en el Instituto IDAT.

---

## 📞 Contacto

Para consultas sobre el proyecto:

- **Ismael Galve Luyo** - [Email/LinkedIn]
- **Piero Leon** - [Email/LinkedIn]

---

<div align="center">

### ⭐ Desarrollado con ❤️ en el Instituto IDAT

![Kotlin](https://img.shields.io/badge/Made%20with-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Material Design](https://img.shields.io/badge/UI-Material%20Design-757575?style=for-the-badge&logo=material-design&logoColor=white)

**© 2025 Chaski - Desarrollo Móvil 1 - IDAT**

</div>

