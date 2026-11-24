# BetterCallSaul

App móvil Android para estudiar y consultar legislación. Proyecto académico de segundo año (DSY1105).
Los datos de las normas son de ejemplo y no reemplazan las fuentes oficiales.

## Funciones
- Registro e inicio de sesión con validación de campos
- Lista de normas y de artículos, con ajuste del tamaño de letra
- Guardar artículos como favoritos
- Foto de perfil con la cámara
- Recordatorio de lectura con una notificación
- Confirmación al cerrar sesión

## Tecnologías
Kotlin, Jetpack Compose, arquitectura MVVM, Retrofit, Navigation Compose y Xano como backend.

## Arquitectura (MVVM)
| Carpeta | Responsabilidad |
|---|---|
| `model` | Clases de datos: `Norma`, `Articulo`, `Usuario`, `Favorito` |
| `remote` | Conexión con la API: `ApiService` y `RetrofitInstance` |
| `repository` | Piden los datos a la API: `NormaRepository` y `UsuarioRepository` |
| `viewmodel` | Estado de las pantallas: `NormaViewModel` y `LoginViewModel` |
| `ui` | Pantallas en Compose y navegación |
| `notificacion` | Notificaciones locales |

## API (Xano)
La base de datos tiene las tablas `usuario`, `norma`, `articulo` y `favorito`.

| Método | Endpoint | Uso |
|---|---|---|
| GET | `/normas` | Lista las normas |
| GET | `/articulo?normaId=` | Lista los artículos de una norma |
| POST | `/usuario` | Registra un usuario |
| POST | `/login` | Inicia sesión |
| GET | `/usuario/{id}` | Recupera la sesión guardada |
| PATCH | `/usuario/{id}` | Actualiza la foto de perfil |
| GET | `/favorito?usuarioId=` | Lista los favoritos de un usuario |
| POST | `/favorito` | Agrega un favorito |
| DELETE | `/favorito/{id}` | Quita un favorito |

## Recursos nativos
- Cámara, para la foto de perfil
- Notificaciones, para el recordatorio de lectura

## Pruebas unitarias
Están en `app/src/test`:
- `NormaRepositoryTest`: comprueba la llamada GET `/norma` con un servidor simulado (MockWebServer)
- `NormaViewModelTest`: comprueba el estado de carga y el estado de error con un repositorio falso

Para ejecutarlas: `./gradlew test`

## Generar el APK firmado
1. En Android Studio ir a Build, Generate Signed App Bundle / APK
2. Elegir APK y crear o seleccionar el keystore
3. Elegir la variante `release` y finalizar
4. El archivo queda en `app/release/app-release.apk`
