# GameHub 🎮

GameHub es una API REST completa para la gestión de torneos de videojuegos, que incluye funcionalidades para crear y administrar torneos, emparejamiento automático de jugadores, sistema de chat básico, clasificaciones y actualización de resultados en tiempo real.

## 🛠️ Tecnologías

- **Backend**: Java 17, Spring Boot
- **Base de Datos**: PostgreSQL
- **Contenedores**: Docker & Docker Compose
- **Documentación**: Swagger/OpenAPI
- **Testing**: Postman Collections

## 📋 Prerrequisitos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Docker](https://www.docker.com/get-started) y Docker Compose
- [Postman](https://www.postman.com/downloads/) (opcional, para pruebas de API)

## 🚀 Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/sebas679og/gamehub.git

cd gamehub
```

### 2. Configurar variables de entorno

```bash
cp .env.template .env

# Editar el archivo .env y configurar las variables necesarias
```

### 3. Ejecutar la aplicación

```bash
# Construir y ejecutar con Docker Compose
docker-compose up --build -d

# Verificar que los contenedores estén ejecutándose
docker-compose ps
```

## 🌐 Acceso a la Aplicación

### API REST

- **URL Base**: `http://localhost:8080`

### Documentación Interactiva

- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI Spec**: `http://localhost:8080/v3/api-docs`

> **Nota**: Asegúrate de que la variable `SWAGGER_UI_DOCUMENTATION_ENABLED=true` esté configurada en tu archivo `.env` para acceder a la documentación.

## 🧪 Testing

### Colecciones de Postman

Las colecciones de Postman para testing están disponibles en el directorio `docs/postman/`:

```bash
docs/
├── postman/
│   └── GameHub-Users-Management.postman_collection.json
```

### Importar en Postman

1. Abre Postman
2. Haz clic en "Import"
3. Selecciona los archivos de colección desde `docs/postman/`
4. Configura las variables de entorno en Postman (baseUrl, authToken, etc.)

## 📖 Documentación de la API

### Endpoints Principales

#### Autenticación

- `POST /api/auth/register` - Registro de usuario
- `POST /api/auth/login` - Inicio de sesión

### Usuarios

- `GET /api/users/me` - Informacion de perfil en sesion
- `GET /api/users/{id}` - Informacion de perfil segun el ID
