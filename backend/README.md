# Backend — Patrimonio Arquidiócesis (Spring Boot)

API REST del proyecto. En esta etapa **la app Android no se conecta** al backend (se conecta en la etapa 3).

- **Java 21 + Spring Boot 3.5** (web + Spring Data JPA)
- **H2** en memoria: la base se crea al iniciar y se llena con `src/main/resources/data.sql` (los mismos datos de ejemplo que la app)
- **Lombok** (`@Data`) para no escribir getters y setters

## Cómo correrlo

```bash
./gradlew bootRun     # levanta en http://localhost:8080
./gradlew test        # pruebas: carga de datos y login
```

Consola de la base de datos: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:patrimonio`, usuario `sa`, sin clave).

## Paquetes

| Paquete | Responsabilidad |
|---|---|
| `model` | Entidades JPA (`@Entity`): una por tabla del modelo ER |
| `repository` | Interfaces `JpaRepository` (consultas a la BD) |
| `controller` | `@RestController`: rutas de la API |

## Rutas de la API

| Método | Ruta | Qué hace |
|---|---|---|
| GET | `/api/usuarios`, `/api/usuarios/{id}` | Listar / obtener usuario (sin contraseña) |
| POST | `/api/usuarios/registro` | Registrar (409 si el email existe, 403 si es ENCARGADO) |
| POST | `/api/usuarios/login` | `{"email","password"}` → usuario o 401 |
| GET | `/api/templos`, `/api/templos/{id}` | Catálogo de templos |
| GET | `/api/templos/{id}/horarios` · `/eventos` · `/avisos` | Datos de un templo |
| GET / POST | `/api/eventos` · DELETE `/api/eventos/{id}` | Eventos culturales |
| GET / POST | `/api/avisos` | Avisos del encargado |
| GET / POST | `/api/rutas` (`?usuarioId=`) · GET `/api/rutas/{id}` | Rutas patrimoniales (nacen en BORRADOR) |
| PUT | `/api/rutas/{id}/confirmar` | Pasa a CONFIRMADA y calcula el tiempo total |
| GET / POST | `/api/rutas/{id}/paradas` · DELETE `/api/rutas/paradas/{paradaId}` | Paradas de una ruta |
| GET / POST | `/api/salidas` (`?colegioId=`) | Salidas pedagógicas |
| GET | `/api/asignaciones` (`?guiaId=`) | Asignaciones del guía |
| PUT | `/api/asignaciones/{id}/responder?aceptar=true` | CONFIRMADA o RECHAZADA |

## Lo que falta (etapa 3)

- Guardar la contraseña con BCrypt (Spring Security) y autenticación con token.
- Capa `service` entre controller y repository, y validaciones con `@Valid`.
- Base de datos real (MySQL o PostgreSQL) en vez de H2.
- Conectar la app Android con Retrofit.
