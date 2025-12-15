# Script para crear todos los layouts XML necesarios para App Chaski
# Ejecutar desde la raíz del proyecto: .\create_layouts.ps1

$layoutsPath = "app\src\main\res\layout"
$menuPath = "app\src\main\res\menu"
$navPath = "app\src\main\res\navigation"

# Crear directorios si no existen
if (-not (Test-Path $menuPath)) {
    New-Item -ItemType Directory -Path $menuPath -Force
}

if (-not (Test-Path $navPath)) {
    New-Item -ItemType Directory -Path $navPath -Force
}

Write-Host "Creando layouts XML..." -ForegroundColor Green

# 1. activity_register.xml
$content = @'
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fillViewport="true">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="24dp">

        <com.google.android.material.appbar.MaterialToolbar
            android:id="@+id/toolbar"
            android:layout_width="0dp"
            android:layout_height="?attr/actionBarSize"
            app:navigationIcon="@android:drawable/ic_menu_close_clear_cancel"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <com.google.android.material.textfield.TextInputLayout
            android:id="@+id/til_nombre"
            style="@style/Widget.Material3.TextInputLayout.OutlinedBox"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:hint="@string/nombre"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/toolbar">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/et_nombre"
                android:layout_width="match_parent"
                android:layout_height="wrap_content" />
        </com.google.android.material.textfield.TextInputLayout>

        <com.google.android.material.textfield.TextInputLayout
            android:id="@+id/til_email"
            style="@style/Widget.Material3.TextInputLayout.OutlinedBox"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:hint="@string/email"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/til_nombre">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/et_email"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:inputType="textEmailAddress" />
        </com.google.android.material.textfield.TextInputLayout>

        <com.google.android.material.textfield.TextInputLayout
            android:id="@+id/til_password"
            style="@style/Widget.Material3.TextInputLayout.OutlinedBox"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:hint="@string/password"
            app:endIconMode="password_toggle"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/til_email">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/et_password"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:inputType="textPassword" />
        </com.google.android.material.textfield.TextInputLayout>

        <com.google.android.material.textfield.TextInputLayout
            android:id="@+id/til_telefono"
            style="@style/Widget.Material3.TextInputLayout.OutlinedBox"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:hint="@string/telefono"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/til_password">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/et_telefono"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:inputType="phone" />
        </com.google.android.material.textfield.TextInputLayout>

        <com.google.android.material.button.MaterialButton
            android:id="@+id/btn_register"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="24dp"
            android:text="@string/btn_register"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/til_telefono" />

        <ProgressBar
            android:id="@+id/progress_bar"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:visibility="gone"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/btn_register" />

        <TextView
            android:id="@+id/tv_login"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:text="@string/login_here"
            android:textColor="@color/primary"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/progress_bar" />

    </androidx.constraintlayout.widget.ConstraintLayout>
</ScrollView>
'@
Set-Content -Path "$layoutsPath\activity_register.xml" -Value $content
Write-Host "  ✓ activity_register.xml creado" -ForegroundColor Cyan

# 2. activity_productos.xml
$content = @'
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.google.android.material.appbar.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <com.google.android.material.appbar.MaterialToolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            app:navigationIcon="@android:drawable/ic_menu_close_clear_cancel" />
    </com.google.android.material.appbar.AppBarLayout>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/rv_productos"
            android:layout_width="0dp"
            android:layout_height="0dp"
            android:padding="8dp"
            android:clipToPadding="false"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <TextView
            android:id="@+id/tv_empty_state"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/no_products"
            android:visibility="gone"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <ProgressBar
            android:id="@+id/progress_bar"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:visibility="gone"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab_carrito"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="16dp"
        android:src="@android:drawable/ic_menu_add" />

</androidx.coordinatorlayout.widget.CoordinatorLayout>
'@
Set-Content -Path "$layoutsPath\activity_productos.xml" -Value $content
Write-Host "  ✓ activity_productos.xml creado" -ForegroundColor Cyan

Write-Host "`nLayouts XML básicos creados. Consulta el README.md para la lista completa." -ForegroundColor Green
Write-Host "Nota: Necesitarás crear los 21 layouts restantes siguiendo las plantillas del README." -ForegroundColor Yellow

