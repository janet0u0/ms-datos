👤 MS-Usuarios - Grupo Cordillera

Microservicio de gestión de usuarios con autenticación y control de acceso basado en roles (RBAC).

## 🛠️ Tecnologías
- Java 17
- Spring Boot 3.3.5
- Spring Security
- Spring Data JPA
- MySQL 8.0
- JUnit 5
- Mockito
- JaCoCo
- Docker
- Lombok
- Maven

## 🎯 Patrones Aplicados
- **Repository Pattern**: Abstrae el acceso a la base de datos
- **DTO Pattern**: Separa el modelo interno de la API
- **RBAC**: Control de acceso basado en roles

## ✅ Requisitos
- Java 17
- Docker Desktop
- Maven

## 🚀 Instalación y Ejecución

### Opción 1: Docker (recomendado)
```bash
docker compose up --build
```

### Opción 2: Local

**1. Clonar el repositorio**
```bash
git clone https://github.com/janet0u0/ms-usuarios
cd ms-usuarios
```

**2. Levantar MySQL con Docker**
```bash
docker-compose up -d
```

**3. Ejecutar el microservicio**
```bash
.\mvnw spring-boot:run
```
Disponible en `http://localhost:8081`

## 🔗 Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | /api/usuarios/login | Autenticar usuario |
| POST | /api/usuarios/registrar | Registrar nuevo usuario |
| GET | /api/usuarios | Listar todos los usuarios |
| GET | /api/usuarios/{id} | Buscar usuario por ID |
| PUT | /api/usuarios/{id} | Actualizar usuario |
| DELETE | /api/usuarios/{id} | Eliminar usuario |

## 📝 Ejemplo de uso

**Registrar usuario**
```json
POST /api/usuarios/registrar
{
    "nombre": "Juan Pérez",
    "email": "juan@cordillera.cl",
    "password": "123456",
    "rol": "EJECUTIVO"
}
```

**Login**
```json
POST /api/usuarios/login
{
    "email": "juan@cordillera.cl",
    "password": "123456"
}
```

## 👥 Roles disponibles

| Rol | Descripción |
|-----|-------------|
| EJECUTIVO | Alta Gerencia |
| ANALISTA | Analista de Negocio |
| SUPERVISOR | Supervisor de Operaciones |
| ADMIN_SISTEMA | Administrador del Sistema |

## 🔒 Seguridad
- Passwords encriptados con BCrypt
- CORS configurado para localhost:3000

## 📂 Estructura del Proyecto

```text
ms-usuarios/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/cordillera/msusuarios/
│   │   │       ├── config/
│   │   │       │   ├── CorsConfig.java
│   │   │       │   └── SecurityConfig.java
│   │   │       ├── controller/
│   │   │       │   └── UsuarioController.java
│   │   │       ├── dto/
│   │   │       │   ├── LoginRequestDTO.java
│   │   │       │   ├── UsuarioRequestDTO.java
│   │   │       │   └── UsuarioResponseDTO.java
│   │   │       ├── exception/
│   │   │       │   ├── GlobalExceptionHandler.java
│   │   │       │   └── ResourceNotFoundException.java
│   │   │       ├── model/
│   │   │       │   ├── Rol.java
│   │   │       │   └── Usuario.java
│   │   │       ├── repository/
│   │   │       │   └── UsuarioRepository.java
│   │   │       ├── service/
│   │   │       │   └── UsuarioService.java
│   │   │       └── MsUsuariosApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       ├── java/
│       │   └── com/cordillera/msusuarios/
│       │       ├── config/
│       │       │   └── SecurityConfigTest.java
│       │       ├── controller/
│       │       │   └── UsuarioControllerTest.java
│       │       ├── dto/
│       │       │   ├── LoginRequestDTOTest.java
│       │       │   ├── UsuarioRequestDTOTest.java
│       │       │   └── UsuarioResponseDTOTest.java
│       │       ├── exception/
│       │       │   ├── GlobalExceptionHandlerTest.java
│       │       │   └── ResourceNotFoundExceptionTest.java
│       │       ├── repository/
│       │       │   └── UsuarioRepositoryTest.java
│       │       ├── service/
│       │       │   └── UsuarioServiceTest.java
│       │       └── MsUsuariosApplicationTests.java
│       │
│       └── resources/
│           └── application-test.properties
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## 📌 Componentes principales

```text
config/       → Seguridad y CORS
controller/   → Endpoints REST
dto/          → Transferencia de datos
exception/    → Manejo de errores
model/        → Entidades JPA
repository/   → Acceso a base de datos
service/      → Lógica de negocio
resources/    → Configuración
```
## 🧪 Pruebas Unitarias y Cobertura

El microservicio cuenta con pruebas unitarias y de integración para validar la lógica de negocio, repositorios, controladores, configuración y manejo de excepciones.

### Herramientas utilizadas

* JUnit 5
* Mockito
* Spring Boot Test
* JaCoCo

### Cobertura de Código

| Paquete     | Cobertura |
| ----------- | --------- |
| service     | 100%      |
| controller  | 100%      |
| repository  | 100%      |
| config      | 100%      |
| exception   | 100%      |
| model       | 100%      |
| application | 100%      |

**Cobertura Total del Proyecto:** 100%

### Ejecutar pruebas

```bash
.\mvnw test
```

### Generar reporte JaCoCo

```bash
.\mvnw clean test jacoco:report
```

Reporte generado en:

```text
target/site/jacoco/index.html
```

### Resultados

* Instrucciones cubiertas: 100%
* Métodos cubiertos: 100%
* Clases cubiertas: 100%
* Ramas cubiertas: 100%
* Tests exitosos: 37/37


## 🏗️ Flujo del Microservicio

```text
Cliente → Controller → Service → Repository → MySQL
```

## 📡 Monitoreo

```
GET http://localhost:8081/actuator/health
GET http://localhost:8081/actuator/info
```