# CONTRATO del proyecto — Patrimonio Arquidiócesis

Acuerdo entre **Javier** y **José**. **Todo lo que está aquí es obligatorio**: si los dos respetamos estos nombres, el código de uno encaja con el del otro sin tocarlo.

**Si algo de aquí hay que cambiar, se avisa al otro antes.** No se cambia por cuenta propia.

App Android (Kotlin + Jetpack Compose, arquitectura **MVVM + Navigation + Room**). Etapa 2 (Prueba 2): 100% de pantallas con navegación. Datos: el **catálogo** (templos y horarios) sale de `DatosEjemplo` con demora simulada (`suspend` + `delay`); se usa **base de datos local SQLite (con Room)** para lo mínimo y necesario: la configuración (sesión y tema) y lo que el usuario crea. **Backend: Spring Boot** (código aparte, no se conecta a la app en esta etapa). **Entrega: martes 27/10 antes de las 19:00.**

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

**Entidades en SQLite** (ver §3) llevan `@Entity` y `@PrimaryKey(autoGenerate = true)`, salvo `ConfiguracionApp` (id fijo = 1). `templo` y `disponibilidadHoraria` quedan como `data class` normales (catálogo). **Una entidad por archivo** en `model/`. Entidad nueva: `ConfiguracionApp`.

**Huecos (ver `MODELO-ER.md`):** `password` en `usuario` (va al backend en etapa 3), `salidaPedagogica.observaciones` (agregado: lo pide el mockup). La tabla `configuracion` es local del dispositivo (no va al backend).

---

## 3. Persistencia local (SQLite con Room)

La persistencia se hace con **SQLite a través de Room** (`@Entity` = tabla, `@Dao` = consultas, `@Database` = la BD). Se guarda **lo mínimo y necesario**: la configuración (sesión y tema) y **lo que el usuario crea**. El **catálogo** (templos y horarios) NO se guarda: sale de `DatosEjemplo`.

**Tablas en SQLite:** `configuracion`, `usuario`, `rutaPatrimonial`, `paradaRuta`, `salidaPedagogica`, `asignacionGuia` (Javier) · `eventoCultural`, `avisoNotificacion` (José). **No van a SQLite:** `templo`, `disponibilidadHoraria`, `galeriaMultimedia`.

**Config (sesión + tema)** → tabla `configuracion` de **una sola fila** (`id = 1`), entidad `ConfiguracionApp(id=1, sesionUsuarioId: Int?, temaOscuro: Boolean)`. **Reemplaza a SharedPreferences:** `Preferencias.kt` **se elimina** y se usa `data/RepositorioConfiguracion.kt` (base común):
```
suspend guardarSesion(usuarioId: Int)   suspend leerSesion(): Int?   suspend borrarSesion()
suspend guardarTemaOscuro(v: Boolean)   observarTemaOscuro(): Flow<Boolean>
```
- Al abrir la app se lee `sesionUsuarioId`: con valor → `inicio` (o `panelEncargado` si es ENCARGADO); `null` → `login`. Mientras lee, se muestra `Cargando()`.
- Nadie accede a un DAO desde una pantalla: siempre a través de un **repositorio + ViewModel**.
- Para guardar: `@Insert(onConflict = REPLACE)`; para cambiar un dato: `actual.copy(temaOscuro = true)`.

---

## 4. Cómo responden los repositorios

- **Catálogo:** `RepositorioTemplos` es un `object` y sus funciones son `suspend` con `delay` (simula el servidor).
- **Repositorios con Room:** son `class` que reciben sus DAOs en el constructor. Las escrituras son `suspend`; las listas que se actualizan solas devuelven `Flow`.
- **ViewModels:** exponen estado con `StateFlow` (o `mutableStateOf` en formularios), cargan dentro de `viewModelScope.launch`, y son `AndroidViewModel(application)` si necesitan la BD.
- Mientras cargan, la pantalla muestra `Cargando()`.

Las funciones marcadas 🔗 las usa el otro integrante: **no se cambian sin avisar.**

### `data/RepositorioUsuarios.kt` — **Javier** (`class`, recibe `UsuarioDao` + `ConfiguracionDao`)
```
suspend iniciarSesion(email, password): Usuario?   // null si no existe o clave mal; si es correcto guarda la sesión
suspend registrarUsuario(nombre, email, password, rol, institucion): Usuario?   // null si el email ya existe
🔗 suspend usuarioActual(): Usuario?               // según la sesión en configuracion
🔗 suspend cerrarSesion()
```

### `data/RepositorioRutas.kt` — **Javier** (`class`, recibe los DAOs de ruta, parada, salida y asignación)
```
🔗 listarRutasDe(usuarioId): Flow<List<RutaPatrimonial>>
suspend crearRuta(usuarioCreadorId, nombre): RutaPatrimonial      // nace BORRADOR
🔗 suspend agregarTemploARutaBorrador(usuarioId, temploId)        // la usa la Ficha (José)
suspend agregarParada(rutaId, temploId)
suspend quitarParada(paradaId)                                    // D
suspend obtenerRuta(rutaId): RutaPatrimonial?
paradasDe(rutaId): Flow<List<ParadaRuta>>
suspend confirmarRuta(rutaId)                                     // U: pasa a CONFIRMADA
suspend crearSalidaPedagogica(colegioId, rutaId, fecha, cantidadEstudiantes, observaciones)
🔗 asignacionesDe(guiaId): Flow<List<AsignacionGuia>>
suspend responderAsignacion(asignacionId, aceptar: Boolean)       // U: CONFIRMADA / RECHAZADA
```

### `data/RepositorioTemplos.kt` — **José** (`object`, catálogo con `delay`)
```
suspend listarTemplos(): List<Templo>
suspend obtenerTemplo(temploId): Templo?
suspend horariosDe(temploId): List<DisponibilidadHoraria>
suspend estadoActualDe(temploId): String          // ABIERTO / CERRADO / EN_CELEBRACION según el día
```

### `data/RepositorioEventos.kt` — **José** (`class`, recibe `EventoDao` + `AvisoDao`)
```
listarEventos(): Flow<List<EventoCultural>>
eventosDe(temploId): Flow<List<EventoCultural>>
suspend crearEvento(temploId, nombre, tipo, fechaHora, descripcion)
avisosDe(temploId): Flow<List<AvisoNotificacion>>
suspend publicarAviso(temploId, mensaje)
listarNotificaciones(usuarioId)   // DERIVADO: avisos + eventos + asignaciones (modelo Notificacion de presentación)
```
`Notificacion` es un modelo de presentación (no está en la BD). Para las asignaciones usa `asignacionesDe(guiaId)` de Javier.

### `data/DatosEjemplo.kt` — **base común** (catálogo + sembrado)
El catálogo (templos y horarios) y los datos que se **siembran** en SQLite al abrir la app por primera vez (usuarios de prueba, rutas, asignaciones, eventos, avisos).

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

**Arranque:** al abrir la app se lee la sesión (mostrando `Cargando()`): con sesión → `inicio` (o `panelEncargado` si es ENCARGADO); sin sesión → `login`. Las rutas con `{temploId}` / `{rutaId}` declaran `arguments = listOf(navArgument("…") { type = NavType.IntType })` y se leen con `backStackEntry.arguments?.getInt("…")`.

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
| `data/DatosEjemplo.kt` | Base común (reunión) |
| `data/AppDatabase.kt` (`@Database`) | Base común — cada uno agrega sus entidades avisando |
| `data/RepositorioConfiguracion.kt` + `dao/ConfiguracionDao.kt` | Base común (Room) |
| El **sembrado** inicial de datos | Base común |
| `view/components/Cargando.kt` | Base común |
| `gradle/libs.versions.toml`, `app/build.gradle.kts` | Los dos, **avisando siempre** |

`Preferencias.kt` **se elimina**. Los `dao/<Tabla>Dao.kt` son del **dueño de la tabla** (§3). En `AppDatabase`, cada uno agrega sus `@Entity` avisando.

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
| `Cargando` | Base común | `Box` + `CircularProgressIndicator`, mientras una pantalla carga (patrón de la guía 12). |

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
| 7 | En SQLite se guarda la configuración (sesión y tema) y lo que el usuario crea; el catálogo sale de `DatosEjemplo` | Repositorios |
| 8 | La sesión se mantiene al cerrar/abrir la app; "Cerrar sesión" o "Salir" la borra | `RepositorioConfiguracion` + `MainActivity` |
| 9 | Un email no se puede registrar dos veces | `RepositorioUsuarios.registrarUsuario` |

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

**No agregar librerías sin avisar.** Aprobadas: `compose-bom`, `activity-compose`, `material3`, `material-icons-core`, `navigation-compose`, `lifecycle-viewmodel-compose`, **Room** (runtime, ktx, compiler con el **plugin KSP**). Las corrutinas vienen con lifecycle. La librería del QR se define cuando se vea en clases. **Backend:** Spring Boot (proyecto aparte).

**Antes de hacer commit:** `./gradlew assembleDebug` debe pasar. `minSdk = 36` (indicación del profe).

---

## 12. Orden de arranque (base común H0)

Se escribe **a mano** (guiado por bloques) y se mergea a `main`:
1. **Tema** → `Color.kt` + `Theme.kt` + `Type.kt` (paleta de la sección 8).
2. **Modelos** → `model/*.kt` (entidades de la sección 2).
3. **Datos de ejemplo** → `DatosEjemplo.kt`. *(La persistencia ya NO es SharedPreferences: va en el hito **H0.5** con Room — ver §13.)*
4. **Rutas + NavHost** → `Rutas.kt`, `AppNavHost.kt` con las 14 rutas (pantallas vacías por ahora).
5. **Scaffold + BottomBar** → `BottomBar.kt` y el patrón Scaffold.

Después, cada uno construye sus 7 pantallas en su rama.

---

## 13. Reuniones de hito y merge a `main`

`main` **nunca** se toca en solitario. Solo se actualiza **juntos**, en la **reunión al cerrar cada hito**. En el día a día cada uno commitea solo en su rama (`dev-javier` / `dev-jose`).

> ⏰ **Entrega: martes 27/10 antes de las 19:00** — app (100% pantallas + navegación + Room + QR) + backend Spring Boot + video (≤7 min, sin audio).

### Pasos del merge (juntos, en cada reunión de hito)
1. Cada uno hace su último commit y `git push` de su rama.
2. Desde `main` actualizada: `git merge dev-javier` y `git merge dev-jose`. Los **conflictos se resuelven juntos**.
3. Probar que `main` compila: `./gradlew assembleDebug`.
4. `git push` de `main`.
5. Cada uno vuelve a su rama y hace `git merge main` para quedar al día.

### Puntos de reunión (hitos) y commit de cierre sugerido
| Hito | Nos reunimos para mergear… | Commit de cierre |
|---|---|---|
| **H0** Base común | tema, modelos, datos de ejemplo, Rutas, NavHost, BottomBar | `chore: base comun del proyecto` |
| **H0.5** Base de datos (Room) | deps Room+KSP, `AppDatabase`, `ConfiguracionApp`+DAO+`RepositorioConfiguracion`, `Usuario`+DAO, sembrado, `Cargando` — **se mergea apenas compile** | `feat: base de datos room` |
| **H1** Autenticación | Login + Registro **guardando en SQLite** + sesión | `feat: autenticacion y sesion` |
| **H2** Exploración | Inicio/Mapa, Ficha, Horarios, Agenda, Notificaciones | `feat: exploracion de templos y eventos` |
| **H3** Rutas y coordinación | Armar ruta, Detalle de ruta, Planificar salida, Mis asignaciones | `feat: rutas y coordinacion` |
| **H4** Administración | Panel del encargado, Nuevo evento | `feat: administracion de templos` |
| **H5** Integración y defensa | Perfil, ajustes finales, tema oscuro, pulido | `chore: integracion final prueba 2` |
| **H6** QR / Cámara | Escanear QR → abre la Ficha del templo. Va al final | `feat: lectura de qr` |

> Cada uno commitea su avance en su rama con los prefijos de la sección 11 (`feat:`, `fix:`, etc.) **cada vez que algo funcione**. El commit de cierre de arriba es el que queda en `main` al terminar el hito.
