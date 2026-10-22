# CONTRATO del proyecto — Patrimonio Arquidiócesis

Acuerdo entre **Javier** y **José**. **Todo lo que está aquí es obligatorio**: si los dos respetamos estos nombres, el código de uno encaja con el del otro sin tocarlo.

**Si algo de aquí hay que cambiar, se avisa al otro antes.** No se cambia por cuenta propia.

App Android (Kotlin + Jetpack Compose, arquitectura **MVVM + Navigation**). Etapa 2 (Prueba 2): 100% de pantallas con navegación. Datos con **repositorio de ejemplo + persistencia local**; backend y Room llegan en etapa 3.

---

## 1. De dónde sale cada cosa

| | Fuente |
|---|---|
| **Datos, entidades y reglas** | `MODELO-ER.md` (derivado de `MoldeoBD.png`) |
| **Solo el estilo visual** | El mockup `Mockup_App_Patrimonio_Completo.pdf` (colores, fuentes, navbar, fichas, badges) |

Lo que el mockup tiene y la BD **no respalda** no se inventa: está listado como *hueco* en `MODELO-ER.md` y se resuelve en etapa 3 (ej. `password`).

---

## 2. Modelo de datos

Nombres en **camelCase** (en español), para que calcen con lo que devolverá el backend en etapa 3. Los `id` son números. Ver campos completos en `MODELO-ER.md`.

Entidades: `usuario`, `templo`, `disponibilidadHoraria`, `avisoNotificacion`, `eventoCultural`, `galeriaMultimedia`, `rutaPatrimonial`, `paradaRuta`, `salidaPedagogica`, `asignacionGuia`.

**Valores fijos** (usar constantes/`enum`, nunca texto suelto):

| Campo | Valores |
|---|---|
| `rol` de usuario | `TURISTA` · `COLEGIO` · `GUIA` · `ENCARGADO` |
| `estado` de templo / disponibilidad | `ABIERTO` · `CERRADO` · `EN_CELEBRACION` |
| `estado` de ruta | `BORRADOR` · `CONFIRMADA` |
| `estadoAsignacion` | `PENDIENTE` · `CONFIRMADA` · `RECHAZADA` |
| `estado` de salida | `PENDIENTE` · `CONFIRMADA` |
| `tipoEvento` | `LITURGICO` · `CONCIERTO` · `EXPOSICION` |
| `diaSemana` | `LUNES` … `DOMINGO` |

**Reglas de forma:** fechas como texto ISO; `email` se compara en **minúsculas**; `ENCARGADO` **no se registra** (lo habilita la administración) → en Registro solo se elige TURISTA/COLEGIO/GUÍA.

**Campos que la BD no tiene** y se agregan en etapa 3: `password` en `usuario`.

---

## 3. Persistencia local (SharedPreferences)

Equivalente a `localStorage`. Archivo `data/Preferencias.kt` (base común). **Qué se guarda:**

| Clave | Qué | Para qué |
|---|---|---|
| `sesionUsuarioId` | `Int?` | **Mantener la cuenta logueada**: al abrir la app, si hay sesión → Inicio; si no → Login. |
| `temaOscuro` | `Boolean` | Recordar si el usuario activó **tema oscuro** (switch en Perfil). |

Funciones acordadas (se implementan a mano):
```
guardarSesion(usuarioId: Int)   leerSesion(): Int?   borrarSesion()
guardarTemaOscuro(v: Boolean)   leerTemaOscuro(): Boolean
```
Nadie lee SharedPreferences directo desde una pantalla: siempre a través de `Preferencias`.

---

## 4. Cómo responden los repositorios

La capa de datos son **repositorios** con datos de ejemplo en memoria. Las consultas devuelven el dato directo (lista, objeto o `null`). Las acciones que pueden fallar devuelven el objeto creado o `null` + se maneja el mensaje en el ViewModel.

Los **ViewModel** exponen el estado con `mutableStateOf` y funciones (como ya se hizo en `RegistroViewModel`). Cada pantalla tiene su `XViewModel` (del dueño de la pantalla).

### `data/RepositorioUsuarios.kt` — **Javier**
```
iniciarSesion(email, password): Usuario?      // null si no existe o clave incorrecta
registrarUsuario(nombre, email, password, rol, institucion?): Usuario
usuarioActual(): Usuario?                       // desde la sesión (Preferencias)
cerrarSesion()
```

### `data/RepositorioRutas.kt` — **Javier**
```
listarRutasDe(usuarioId): List<RutaPatrimonial>
crearRuta(usuarioCreadorId, nombre): RutaPatrimonial     // nace BORRADOR
agregarParada(rutaId, temploId)
obtenerRuta(rutaId): RutaPatrimonial?                    // con sus paradas
confirmarRuta(rutaId)                                    // pasa a CONFIRMADA
crearSalidaPedagogica(colegioId, rutaId, fecha, cantidadEstudiantes, observaciones)
asignacionesDe(guiaId): List<AsignacionGuia>
responderAsignacion(asignacionId, aceptar: Boolean)      // CONFIRMADA / RECHAZADA
```

### `data/RepositorioTemplos.kt` — **José**
```
listarTemplos(): List<Templo>
obtenerTemplo(temploId): Templo?
horariosDe(temploId): List<DisponibilidadHoraria>
estadoActualDe(temploId): String                 // ABIERTO/CERRADO/EN_CELEBRACION
listarEventos(): List<EventoCultural>            // agenda cultural
eventosDe(temploId): List<EventoCultural>
galeriaDe(temploId): List<GaleriaMultimedia>
avisosDe(temploId): List<AvisoNotificacion>
publicarAviso(temploId, mensaje)                 // panel del encargado
crearEvento(temploId, nombre, tipo, fechaHora, descripcion)
```

### Notificaciones (feed) — **José**, pantalla #7
```
listarNotificaciones(usuarioId): List<Notificacion>   // DERIVADO (ver hueco en MODELO-ER.md)
```
`Notificacion` es un modelo de presentación (no está en la BD): junta avisos de templo + eventos nuevos + asignación de guía. Se define en la reunión al construirla.

### `data/DatosEjemplo.kt` — **base común**
Los datos sembrados (templos, usuarios de prueba, eventos…). Se crea en H0 entre los dos.

---

## 5. Rutas de navegación

Constantes en `navigation/Rutas.kt` (archivo compartido). Formato exacto de cada ruta:

| Ruta | Pantalla | # | Dueño |
|---|---|---|---|
| `login` | Login | 1 | 🧙 Javier |
| `registro` | Registro de usuario | 2 | 🧙 Javier |
| `inicio` | Inicio / Mapa | 3 | ⚔️ José |
| `templo/{temploId}` | Ficha de templo | 4 | ⚔️ José |
| `templo/{temploId}/horarios` | Horarios del templo | 5 | ⚔️ José |
| `agenda` | Agenda cultural | 6 | ⚔️ José |
| `notificaciones` | Notificaciones | 7 | ⚔️ José |
| `perfil` | Perfil | 8 | 🧙 Javier |
| `armarRuta` | Armar ruta | 9 | 🧙 Javier |
| `ruta/{rutaId}` | Detalle de ruta | 10 | 🧙 Javier |
| `salidaPedagogica` | Planificar salida pedagógica | 11 | 🧙 Javier |
| `misAsignaciones` | Mis asignaciones | 12 | 🧙 Javier |
| `panelEncargado` | Panel del encargado | 13 | ⚔️ José |
| `nuevoEvento` | Nuevo evento | 14 | ⚔️ José |

**Reparto 7 / 7:** Javier = 1, 2, 8, 9, 10, 11, 12 · José = 3, 4, 5, 6, 7, 13, 14.

Cada pantalla vive en `view/<Nombre>Screen.kt` + `viewmodel/<Nombre>ViewModel.kt` (archivos propios de cada dueño, no se pisan).

---

## 6. Archivos compartidos

**Solo se tocan en las reuniones de hito, o avisando al otro.**

| Archivo | Dueño |
|---|---|
| `MainActivity.kt` | Javier |
| `navigation/AppNavHost.kt` (el NavHost con las 14 rutas) | Base común (reunión) |
| `navigation/Rutas.kt` (constantes de rutas) | Base común (reunión) |
| `view/components/BottomBar.kt` | Javier (base H0) |
| `ui/theme/Color.kt`, `Theme.kt`, `Type.kt` | Base común (reunión) |
| `model/*.kt` (las entidades) | Base común (reunión) |
| `data/RepositorioUsuarios.kt`, `RepositorioRutas.kt` | Javier |
| `data/RepositorioTemplos.kt` | José |
| `data/DatosEjemplo.kt`, `data/Preferencias.kt` | Base común (reunión) |
| `gradle/libs.versions.toml`, `app/build.gradle.kts` | Los dos, **avisando siempre** |

**Caso concreto:** la **barra inferior** (`BottomBar`) es de Javier. José **no la toca**; sus pantallas (Inicio, Agenda) se muestran dentro del Scaffold que ya la incluye.

---

## 7. Componentes y patrón de pantalla compartidos

| Componente | Dueño | Cómo se usa |
|---|---|---|
| **Scaffold** (patrón) | Todos | Cada pantalla se arma dentro de un `Scaffold`: `topBar` (flecha-atrás + título) en las internas; `bottomBar` solo en las 4 con barra (Inicio, Agenda, Rutas, Perfil). |
| `BottomBar` | Javier | 4 destinos: Mapa(`inicio`) · Eventos(`agenda`) · Rutas(`armarRuta`) · Perfil(`perfil`). |
| `TemploCard` | José | Tarjeta de templo reutilizable (Inicio y donde se liste un templo). |
| `EstadoChip` | José | Badge de estado: ABIERTO (dorado), EN_CELEBRACION (terracota), CERRADO (piedra oscura). |
| **HorizontalPager** | José | Sugerido en Inicio/Mapa (carrusel "Cerca de ti"). Opcional. |

---

## 8. Estilo visual (tema)

Colores en `ui/theme/Color.kt` (nombres en español) y mapeo Material 3 en `Theme.kt`:

```
Piedra #EDE4D3  · Superficie #FBF7EF · PiedraOscura #DCCEA9
Tinta #2E2A22   · TintaSuave #7A7160
Dorado #A9812F  · DoradoProfundo #8A6A23 · DoradoClaro #EFE0B2
Terracota #A3512F · TerracotaClaro #F0DACB
```
Mapeo: `primary = Dorado`, `onPrimary = White`, `background = Piedra`, `onBackground = Tinta`, `surface = Superficie`, `onSurface = Tinta`, `outline = PiedraOscura`, `secondary = Terracota`.

Tipografías: **Lora** (títulos y nombres de templos) y **Poppins** (resto). El panel del encargado usa encabezado **café oscuro** (`Tinta`) con texto claro.

---

## 9. Reglas de negocio (de la BD / mockup)

| # | Regla | Dónde se cumple |
|---|---|---|
| 1 | `ENCARGADO` no se registra; lo habilita la administración | `RepositorioUsuarios.registrarUsuario` |
| 2 | El estado del templo (ABIERTO/CERRADO/EN_CELEBRACION) sale del horario del día | `RepositorioTemplos.estadoActualDe` |
| 3 | Una ruta nace `BORRADOR` y luego se `CONFIRMA` | `RepositorioRutas` |
| 4 | Una asignación de guía va `PENDIENTE` → `CONFIRMADA`/`RECHAZADA` | `RepositorioRutas.responderAsignacion` |
| 5 | Solo COLEGIO planifica salida pedagógica; solo GUÍA ve Mis asignaciones | navegación según `rol` |
| 6 | `email` siempre en minúsculas | al registrar / iniciar sesión |

---

## 10. Usuarios de prueba (datos de ejemplo)

| Nombre | Correo | Contraseña | Rol |
|---|---|---|---|
| María Pérez | `maria@correo.cl` | `maria123` | TURISTA |
| Colegio San Ignacio | `colegio@correo.cl` | `colegio123` | COLEGIO |
| Carlos M. | `carlos@correo.cl` | `carlos123` | GUIA |
| Encargado Catedral | `encargado@correo.cl` | `encargado123` | ENCARGADO |

---

## 11. Convenciones de trabajo

**Ramas:** cada uno hace commit solo en la suya, `dev-javier` o `dev-jose`. A `main` solo en las reuniones de hito.

**Commits:** `feat:` (algo nuevo), `fix:` (arreglo), `chore:` (config/estructura), `test:` (pruebas), `docs:` (documentación), `style:` (solo formato/colores), `refactor:` (reordenar sin cambiar comportamiento).

**No agregar librerías sin avisar.** Aprobadas: `compose-bom`, `activity-compose`, `material3`, `navigation-compose`, `lifecycle-viewmodel-compose`. `Room`/`DataStore` solo si se acuerda (etapa 3). SharedPreferences no necesita librería.

**Antes de hacer commit:** `./gradlew assembleDebug` debe pasar. `minSdk = 36` (indicación del profe).

---

## 12. Orden de arranque (base común H0)

Se escribe **a mano** (guiado por bloques) y se mergea a `main`:
1. **Tema** → `Color.kt` + `Theme.kt` + `Type.kt` (paleta de la sección 8).
2. **Modelos** → `model/*.kt` (entidades de la sección 2).
3. **Datos de ejemplo + Preferencias** → `DatosEjemplo.kt`, `Preferencias.kt`.
4. **Rutas + NavHost** → `Rutas.kt`, `AppNavHost.kt` con las 14 rutas (pantallas vacías por ahora).
5. **Scaffold + BottomBar** → `BottomBar.kt` y el patrón Scaffold.

Después, cada uno construye sus 7 pantallas en su rama.

---

## 13. Reuniones de hito y merge a `main`

`main` **nunca** se toca en solitario. Solo se actualiza **juntos**, en la **reunión al cerrar cada hito**. En el día a día cada uno commitea solo en su rama (`dev-javier` / `dev-jose`).

### Pasos del merge (juntos, en cada reunión de hito)
1. Cada uno hace su último commit y `git push` de su rama.
2. Desde `main` actualizada: `git merge dev-javier` y `git merge dev-jose`. Los **conflictos se resuelven juntos**.
3. Probar que `main` compila: `./gradlew assembleDebug`.
4. `git push` de `main`.
5. Cada uno vuelve a su rama y hace `git merge main` para quedar al día.

### Puntos de reunión (hitos) y commit de cierre sugerido
| Hito | Nos reunimos para mergear… | Commit de cierre |
|---|---|---|
| **H0** Base común | tema, modelos, datos de ejemplo, Preferencias, Rutas, NavHost, BottomBar | `chore: base comun del proyecto` |
| **H1** Autenticación | Login + Registro + sesión (SharedPreferences) | `feat: autenticacion y sesion` |
| **H2** Exploración | Inicio/Mapa, Ficha, Horarios, Agenda, Notificaciones | `feat: exploracion de templos y eventos` |
| **H3** Rutas y coordinación | Armar ruta, Detalle de ruta, Planificar salida, Mis asignaciones | `feat: rutas y coordinacion` |
| **H4** Administración | Panel del encargado, Nuevo evento | `feat: administracion de templos` |
| **H5** Integración y defensa | Perfil, ajustes finales, tema oscuro, pulido | `chore: integracion final prueba 2` |

> Cada uno commitea su avance en su rama con los prefijos de la sección 11 (`feat:`, `fix:`, etc.) **cada vez que algo funcione**. El commit de cierre de arriba es el que queda en `main` al terminar el hito.
