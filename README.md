# 🛒 Punto de Venta – Dulcería

Aplicación de escritorio para la gestión de ventas e inventario de una dulcería. Desarrollada en **Java (Swing)** con **SQLite** como base de datos y una interfaz moderna gracias a **FlatLaf**.

El sistema permite iniciar sesión, registrar ventas, administrar productos, consultar el inventario y realizar cortes de caja. Sigue una arquitectura por capas (Modelo – DAO – Vista) que facilita su mantenimiento y futuras ampliaciones.

---

## 📋 Características principales

- 🔐 **Inicio de sesión** con validación de usuario, contraseña y estado activo.
- 🛍️ **Punto de venta**:
  - Búsqueda de productos por código de barras.
  - Carrito de compra con cantidades editables.
  - Cálculo automático de subtotal, importe y total.
  - Validación de existencia disponible.
- 📦 **Gestión de productos**:
  - Agregar, modificar y eliminar productos.
  - Administración de categorías.
- 📊 **Inventario**:
  - Búsqueda por nombre o código.
  - Filtrado por categoría.
  - Visualización de existencias.
- 🧾 **Corte de caja**:
  - Resumen de ventas realizadas y productos vendidos.
  - Desglose por métodos de pago (estructura preparada).
- 🎨 **Interfaz gráfica** moderna con FlatLaf, iconos SVG y diseño limpio.

---

## 🧰 Tecnologías utilizadas

| Tecnología / Librería | Versión | Uso |
|------------------------|---------|-----|
| **Java**               | 25      | Lenguaje principal |
| **Maven**              | —       | Gestión de dependencias y construcción |
| **SQLite**             | 3.36.0.3| Base de datos embebida |
| **FlatLaf**            | 3.6     | Look & Feel moderno para Swing |
| **FlatLaf Extras**     | 3.6     | Soporte para iconos SVG |
| **Swing**              | —       | Interfaz gráfica de usuario |

---

## 📂 Estructura del proyecto

```
punto_venta/
├── database/
│   ├── dulceria          # Carpeta con posibles scripts SQL
│   └── dulceria.db       # Base de datos SQLite (generada)
├── src/
│   └── main/
│       ├── java/
│       │   └── com/dulceria/
│       │       ├── app/
│       │       │   └── Main.java          # Punto de entrada
│       │       ├── config/
│       │       │   └── ConexionDB.java    # Conexión a SQLite
│       │       ├── dao/
│       │       │   └── UsuarioDAO.java    # Acceso a datos de usuarios
│       │       ├── model/
│       │       │   └── Usuario.java       # Modelo de usuario
│       │       └── view/
│       │           ├── componets/         # Barras y menú reutilizables
│       │           ├── LoginView.java
│       │           ├── PrincipalView.java # Ventas
│       │           ├── ProductosView.java
│       │           ├── InventarioView.java
│       │           └── CorteView.java
│       └── resources/
│           └── images/                    # Iconos SVG
├── pom.xml                                # Configuración Maven
└── README.md
```

---

## 🗄️ Esquema de base de datos (inferido)

A partir de las consultas SQL del código, las tablas principales son:

### `usuarios`
| Columna      | Tipo    | Descripción                     |
|--------------|---------|---------------------------------|
| `id_usuario` | INTEGER | Clave primaria                  |
| `usuario`    | TEXT    | Nombre de usuario (único)       |
| `contrasena` | TEXT    | Contraseña                      |
| `nombre`     | TEXT    | Nombre completo                 |
| `rol`        | TEXT    | Rol del usuario (admin, cajero) |
| `activo`     | INTEGER | 1 = activo, 0 = inactivo        |

### `productos`
| Columna        | Tipo    | Descripción               |
|----------------|---------|---------------------------|
| `id_producto`  | INTEGER | Clave primaria            |
| `codigo`       | TEXT    | Código de barras (único)  |
| `nombre`       | TEXT    | Nombre del producto       |
| `precio_venta` | REAL    | Precio unitario           |
| `existencia`   | INTEGER | Unidades disponibles      |
| `categoria`    | TEXT    | Categoría del producto    |

> 💡 La base de datos se crea automáticamente en `database/dulceria.db` (o se espera que exista). Si necesitas generarla desde cero, puedes incluir un script SQL en la carpeta `database/dulceria`.

---

## 🚀 Instalación y ejecución

### Requisitos previos

- **Java JDK 25** o superior.
- **Maven** instalado (o usar el wrapper si se añade).
- Sistema operativo con soporte para Swing (Windows, Linux o macOS).

### Pasos

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/AcronMaster/punto_venta.git
   cd punto_venta
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

3. **Ejecutar la aplicación**
   ```bash
   mvn exec:java -Dexec.mainClass="com.dulceria.app.Main"
   ```

   > Si no tienes el plugin `exec`, puedes ejecutar directamente desde tu IDE (IntelliJ, Eclipse, VS Code) la clase `Main.java`.

4. **Credenciales de prueba**
   - Asegúrate de que la base de datos contenga al menos un usuario activo en la tabla `usuarios`. Si no existe, puedes insertarlo manualmente:
     ```sql
     INSERT INTO usuarios (usuario, contrasena, nombre, rol, activo)
     VALUES ('admin', 'admin123', 'Administrador', 'admin', 1);
     ```

---

## 🖥️ Uso de la aplicación

### 1. Inicio de sesión
Al arrancar, se muestra la ventana de login. Ingresa un usuario y contraseña válidos (y que el usuario esté activo).

### 2. Ventana principal – Ventas
- **Código de barras**: escribe o escanea el código y presiona *Enter* para agregar el producto al carrito.
- **Tabla de productos**: muestra código, nombre, precio, cantidad, importe y existencia.
- **Cantidad editable**: puedes modificar la cantidad directamente en la tabla; el sistema valida que no supere la existencia.
- **Totales**: se actualizan automáticamente.

### 3. Productos
Desde el menú superior puedes acceder a la sección de **Productos**, donde encontrarás:
- **Agregar producto**: formulario para dar de alta un nuevo producto.
- **Modificar producto**: actualizar datos de un producto existente.
- **Eliminar producto**: dar de baja un producto.
- **Categorías**: gestionar las categorías disponibles.

### 4. Inventario
- Búsqueda por nombre o código.
- Filtro por categoría.
- Lista detallada de productos con su existencia actual.

### 5. Corte de caja
Muestra un resumen del turno:
- Ventas realizadas.
- Productos vendidos.
- Desglose por métodos de pago (estructura preparada para integrarse con la base de datos).

> ⚠️ Actualmente los valores de ventas y productos vendidos en el corte son **estáticos** (datos de ejemplo). La lógica para calcularlos dinámicamente desde la base de datos aún está pendiente de implementación.

---

## 🏗️ Arquitectura

El proyecto sigue una separación por capas:

| Capa | Paquete | Responsabilidad |
|------|---------|-----------------|
| **Modelo** | `com.dulceria.model` | Clases de entidad (ej. `Usuario`). |
| **DAO** | `com.dulceria.dao` | Acceso a la base de datos (consultas SQL). |
| **Configuración** | `com.dulceria.config` | Conexión a SQLite. |
| **Vista** | `com.dulceria.view` | Interfaces gráficas (ventanas y componentes). |
| **Aplicación** | `com.dulceria.app` | Punto de entrada y configuración del Look & Feel. |

### Flujo general

```
Main → LoginView → PrincipalView (Ventas)
                     ├── ProductosView
                     ├── InventarioView
                     └── CorteView
```

Cada vista utiliza `BarraSuperiorView` y `MenuPrincipalView` para la navegación, y `BarraInferiorView` como pie de página.

---

## 📌 Notas y limitaciones

- La base de datos **SQLite** debe existir en `database/dulceria.db`. Si no existe, la aplicación fallará al conectar. Se recomienda incluir un script de creación en la carpeta `database/dulceria`.
- El **corte de caja** aún no consulta las ventas reales; muestra datos de ejemplo. Es una base para futuras implementaciones.
- No se incluyen pruebas unitarias en el repositorio actual.
- La interfaz está diseñada para una resolución de **1400x800**; en pantallas más pequeñas puede requerir ajustes.
- Los iconos SVG se cargan desde `src/main/resources/images`; asegúrate de que las rutas sean correctas al ejecutar.

---

## 👨‍💻 Autor

**Luis Adrián Cauich Dzul**  
GitHub: [@AcronMaster](https://github.com/AcronMaster)
