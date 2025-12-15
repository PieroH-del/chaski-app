# 📤 Guía para Subir el Proyecto a GitHub

## ✅ Paso 1: Repositorio Git Local Inicializado

El repositorio local ya está inicializado y configurado con:
- ✅ Git inicializado
- ✅ Rama principal: `main`
- ✅ `.gitignore` configurado para Android
- ✅ Primer commit realizado (143 archivos)

---

## 🌐 Paso 2: Crear Repositorio en GitHub

### Opción A: Usando la Web de GitHub

1. **Ir a GitHub:**
   - Abre tu navegador y ve a: https://github.com
   - Inicia sesión con tu cuenta

2. **Crear nuevo repositorio:**
   - Click en el botón **"+"** en la esquina superior derecha
   - Selecciona **"New repository"**

3. **Configurar el repositorio:**
   ```
   Repository name: chaski-app
   Description: Aplicación Android de delivery multi-restaurante - Proyecto IDAT
   Visibility: ✅ Public (o Private si prefieres)
   
   ⚠️ NO marques estas opciones:
   [ ] Add a README file
   [ ] Add .gitignore
   [ ] Choose a license
   ```

4. **Click en "Create repository"**

### Opción B: Usando GitHub CLI (si lo tienes instalado)

```bash
gh repo create chaski-app --public --description "Aplicación Android de delivery multi-restaurante - Proyecto IDAT"
```

---

## 🔗 Paso 3: Conectar con el Repositorio Remoto

Después de crear el repositorio en GitHub, verás una URL similar a:
```
https://github.com/TU_USUARIO/chaski-app.git
```

### Ejecuta estos comandos:

```powershell
# 1. Agregar el remote (reemplaza TU_USUARIO con tu usuario de GitHub)
git remote add origin https://github.com/TU_USUARIO/chaski-app.git

# 2. Verificar que se agregó correctamente
git remote -v

# 3. Subir el código a GitHub
git push -u origin main
```

---

## 🚀 Paso 4: Comandos Completos

Copia y pega estos comandos en PowerShell (reemplaza `TU_USUARIO`):

```powershell
cd C:\Users\User\AndroidStudioProjects\AppChaski

# Agregar remote (CAMBIA TU_USUARIO)
git remote add origin https://github.com/TU_USUARIO/chaski-app.git

# Push inicial
git push -u origin main
```

---

## 🔐 Autenticación en GitHub

### Si GitHub te pide autenticación:

#### Método 1: Token de Acceso Personal (Recomendado)

1. Ve a GitHub → Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Click en "Generate new token (classic)"
3. Nombre: `Chaski App Upload`
4. Selecciona scopes: `repo` (todos los permisos de repositorio)
5. Click en "Generate token"
6. **Copia el token** (solo se muestra una vez)

7. Cuando hagas `git push`, usa:
   - **Username:** Tu usuario de GitHub
   - **Password:** El token que copiaste

#### Método 2: GitHub CLI

```powershell
# Instalar GitHub CLI (si no lo tienes)
winget install GitHub.cli

# Autenticarte
gh auth login
```

---

## 📋 Resumen de Comandos

```powershell
# Ver estado actual
git status

# Ver historial
git log --oneline

# Ver remotes configurados
git remote -v

# Push (después de configurar remote)
git push -u origin main

# Futuros cambios
git add .
git commit -m "Descripción del cambio"
git push
```

---

## 🎯 Después de Subir a GitHub

### 1. Verificar que se subió correctamente:
   - Ve a: `https://github.com/TU_USUARIO/chaski-app`
   - Deberías ver todos tus archivos

### 2. Actualizar el README (opcional):
   - Edita el README.md en la línea del badge de GitHub
   - Cambia el link del repositorio

### 3. Agregar Topics al repositorio:
   - En GitHub: Settings → Topics
   - Agrega: `android`, `kotlin`, `delivery-app`, `idat`, `mobile-development`

### 4. Proteger la rama main (opcional):
   - Settings → Branches → Add rule
   - Branch name pattern: `main`
   - ✅ Require pull request reviews before merging

---

## 🔄 Colaboración en Equipo

### Para que Piero Leon e Ismael Galve puedan colaborar:

1. **Agregar colaboradores:**
   - En GitHub: Settings → Collaborators
   - Click "Add people"
   - Buscar por username o email
   - Enviar invitación

2. **Clonar el repositorio (para el compañero):**
   ```powershell
   git clone https://github.com/TU_USUARIO/chaski-app.git
   cd chaski-app
   ```

3. **Workflow de colaboración:**
   ```powershell
   # Antes de hacer cambios
   git pull origin main
   
   # Hacer cambios
   # ... editar archivos ...
   
   # Subir cambios
   git add .
   git commit -m "Descripción del cambio"
   git push origin main
   ```

---

## 📱 Subir APK como Release (opcional)

Para compartir la app compilada:

```powershell
# 1. Compilar APK
cd C:\Users\User\AndroidStudioProjects\AppChaski
.\gradlew assembleRelease

# 2. En GitHub:
# - Ve a Releases → Create a new release
# - Tag: v1.0.0
# - Title: Chaski v1.0.0 - Primera versión
# - Description: Aplicación de delivery para IDAT
# - Attach: app/build/outputs/apk/release/app-release.apk
# - Click "Publish release"
```

---

## 🐛 Solución de Problemas

### Error: "remote origin already exists"
```powershell
git remote remove origin
git remote add origin https://github.com/TU_USUARIO/chaski-app.git
```

### Error: "failed to push some refs"
```powershell
git pull origin main --allow-unrelated-histories
git push origin main
```

### Error de autenticación
- Verifica que estás usando el token de acceso personal, no tu password
- O usa GitHub CLI: `gh auth login`

---

## ✅ Checklist Final

Antes de dar por terminado:

- [ ] Repositorio creado en GitHub
- [ ] Remote configurado localmente
- [ ] Push realizado exitosamente
- [ ] Código visible en GitHub
- [ ] README.md se ve correctamente
- [ ] Colaboradores agregados (opcional)
- [ ] Topics agregados (opcional)

---

## 📞 Siguiente Paso

**Ejecuta este comando ahora (reemplaza TU_USUARIO):**

```powershell
git remote add origin https://github.com/TU_USUARIO/chaski-app.git
git push -u origin main
```

**¡Tu proyecto estará en GitHub!** 🎉

---

## 📚 Recursos Adicionales

- [Documentación de Git](https://git-scm.com/doc)
- [GitHub Guides](https://guides.github.com/)
- [GitHub CLI](https://cli.github.com/)

---

<div align="center">

**¿Listo para compartir tu proyecto con el mundo?** 🚀

</div>

