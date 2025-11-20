# User Service — Hexagonal Architecture | Spring Boot 3 | PostgreSQL | Docker

Este proyecto implementa un microservicio RESTful para la gestión de usuarios siguiendo Arquitectura Hexagonal, buenas prácticas de desarrollo, testing con JUnit 5 + Mockito, y desplegable mediante Docker Compose.

Incluye:
- CRUD completo de usuarios
- Validaciones con @Valid
- Manejo global de excepciones
- MapStruct para mapping
- Swagger (OpenAPI 3)
- PostgreSQL + JPA (Hibernate)
- Tests unitarios con JUnit 5 + Mockito 
- Arquitectura limpia (puertos y adaptadores)

---

# Arquitectura

El proyecto sigue el patrón Hexagonal Architecture (Puertos & Adaptadores):

domain/
├── model/ # Entidades de dominio
├── service/ # Reglas de negocio
application/
├── port/
│ ├── in/ # Use Cases (entradas)
│ └── out/ # Puertos para persistencia
└── dto/ # DTOs de aplicación
infrastructure/
├── controller/ # API REST
├── persistence/ # Adaptadores JPA, entidades, repositorio
├── mapper/ # MapStruct mappers
├── exception/ # GlobalExceptionHandler

---

# Tecnologías

### Backend
- Java 17
- Spring Boot 3.5.7
- Spring Web
- Spring Data JPA
- Spring Validation
- Lombok
- MapStruct

### Infraestructura
- PostgreSQL
- Docker & Docker Compose

### Documentación
- Springdoc OpenAPI UI (Swagger)

### Testing
- JUnit 5
- Mockito

---

# Cómo ejecutar el proyecto

## Ejecutar manualmente (necesario contar con Java 17)
### 1. Levantar la BD en docker
docker compose up -d

### 2️. Levantar microservicio:
./mvnw spring-boot:run

Esto levanta:
user-service en http://localhost:8080
postgres en localhost:5432

---

# Base de Datos

El script de creación se encuentra en la ruta:
/resources/schema.sql

---

# Pruebas Unitarias

Los tests se encuentran en:
src/test/java/com/example/user_service/application/service/UserServiceTests.java

Para ejecutar los tests:
./mvnw test

# Endpoints REST

Todos los endpoints están documentados en Swagger:
http://localhost:8080/swagger-ui.html

### Crear usuario
POST /users

Body:
{
  "firstName": "Ana",
  "lastName": "Cotrina",
  "email": "ana@example.com",
  "status": "ACTIVE"
}

Listar usuarios
GET /users

Obtener usuario por Id
GET /users/{id}

Actualizar usuario
PUT /users/{id}

Eliminar usuario
DELETE /users/{id}

Buscar por email
GET /users/search?email=ana@example.com
