# 📈 MS-KPI - Grupo Cordillera

Microservicio encargado de la gestión y monitoreo de los **Indicadores Clave de Desempeño (KPIs)** del Grupo Cordillera.

Permite registrar, consultar, actualizar y eliminar KPIs asociados a distintas áreas de negocio, facilitando la toma de decisiones mediante indicadores de ventas, rentabilidad, logística e inventario.

---

## 🛠️ Tecnologías

- Java 17
- Spring Boot 3.3.5
- Spring Data JPA
- Spring Boot Actuator
- MySQL 8.0
- Docker
- Lombok
- Maven
- JUnit 5
- Mockito
- JaCoCo

---

## 🎯 Patrones Aplicados

### Repository Pattern
Abstrae el acceso a la base de datos mediante Spring Data JPA.

### DTO Pattern
Separa el modelo interno de la API para mayor seguridad y desacoplamiento.

### Builder Pattern
Facilita la creación de objetos utilizando Lombok `@Builder`.

---

## ✅ Requisitos

- Java 17
- Maven
- Docker Desktop

---

## 🚀 Instalación y Ejecución

### Opción 1: Docker (Recomendado)

```bash
docker compose up --build
```

### Opción 2: Ejecución Local

#### 1. Clonar repositorio

```bash
git clone https://github.com/janet0u0/ms-kpi
cd ms-kpi
```

#### 2. Levantar Base de Datos

```bash
docker compose up -d
```

#### 3. Ejecutar Aplicación

```bash
.\mvnw spring-boot:run
```

Disponible en:

```text
http://localhost:8082
```

---

## 🔗 Endpoints

| Método | Endpoint | Descripción |
|----------|----------|----------|
| GET | /api/kpis | Obtiene todos los KPIs |
| GET | /api/kpis/{id} | Obtiene un KPI por ID |
| GET | /api/kpis/tipo/{tipo} | Obtiene KPIs por tipo |
| GET | /api/kpis/area/{area} | Obtiene KPIs por área |
| GET | /api/kpis/estado/{estado} | Obtiene KPIs por estado |
| POST | /api/kpis | Registra un KPI |
| PUT | /api/kpis/{id} | Actualiza un KPI |
| DELETE | /api/kpis/{id} | Elimina un KPI |

---

## 📝 Ejemplo de Uso

### Crear KPI

```json
POST /api/kpis

{
  "tipo": "VENTAS",
  "valor": 150000.00,
  "fecha": "2026-06-11",
  "area": "VENTAS",
  "estado": "VERDE"
}
```

### Respuesta

```json
{
  "id": 1,
  "tipo": "VENTAS",
  "valor": 150000.00,
  "fecha": "2026-06-11",
  "area": "VENTAS",
  "estado": "VERDE"
}
```

---

## 📋 Catálogo de Valores

### Tipos de KPI

| Tipo | Descripción |
|--------|--------|
| VENTAS | Indicadores de ventas |
| RENTABILIDAD | Indicadores financieros |
| INVENTARIO | Control de inventario |
| LOGISTICA | Indicadores logísticos |

### Áreas de Negocio

| Área |
|--------|
| VENTAS |
| FINANZAS |
| OPERACIONES |

### Estados

| Estado | Descripción |
|--------|--------|
| VERDE | Rendimiento óptimo |
| AMARILLO | Requiere atención |
| ROJO | Estado crítico |

---

## 📂 Estructura del Proyecto

```text
ms-kpi/
├── src/
│
├── main/
│   ├── java/com/cordillera/mskpi/
│   │
│   ├── config/
│   │   └── CorsConfig.java
│   │
│   ├── controller/
│   │   └── KpiController.java
│   │
│   ├── dto/
│   │   ├── KpiRequestDTO.java
│   │   └── KpiResponseDTO.java
│   │
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java
│   │   └── ResourceNotFoundException.java
│   │
│   ├── model/
│   │   └── Kpi.java
│   │
│   ├── repository/
│   │   └── KpiRepository.java
│   │
│   ├── service/
│   │   └── KpiService.java
│   │
│   └── MsKpiApplication.java
│
├── test/
│   ├── java/com/cordillera/mskpi/
│   │
│   ├── controller/
│   │   └── KpiControllerTest.java
│   │
│   ├── exception/
│   │   └── GlobalExceptionHandlerTest.java
│   │
│   ├── repository/
│   │   └── KpiRepositoryTest.java
│   │
│   ├── service/
│   │   └── KpiServiceTest.java
│   │
│   └── resources/
│       └── application-test.properties
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

## 📌 Componentes Principales

```text
config/       → Configuración CORS
controller/   → Endpoints REST
dto/          → Objetos de transferencia
exception/    → Manejo centralizado de errores
model/        → Entidades JPA
repository/   → Acceso a datos
service/      → Lógica de negocio
resources/    → Configuración de la aplicación
```

---

## 🏗️ Flujo de Arquitectura

```text
Cliente
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
MySQL
```

---

## 🧪 Pruebas Unitarias

El proyecto cuenta con pruebas para:

### Controller
- KpiControllerTest

### Service
- KpiServiceTest

### Repository
- KpiRepositoryTest

### Exception Handling
- GlobalExceptionHandlerTest

### Configuración de Pruebas
- application-test.properties

### Herramientas utilizadas

- JUnit 5
- Mockito
- MockMvc
- Spring Test
- JaCoCo

---

## 📊 Cobertura de Código

Reporte generado con JaCoCo:

| Paquete | Cobertura |
|----------|----------|
| Service | 100% |
| Controller | 100% |
| Repository | 100% |
| Exception | 100% |
| Config | 100% |
| Application | 100% |

### Resultado Global

```text
Instructions: 100%
Branches:     100%
Lines:        100%
Methods:      100%
Classes:      100%
```

---

## 📡 Monitoreo

### Estado de Salud

```http
GET http://localhost:8082/actuator/health
```

### Información de la Aplicación

```http
GET http://localhost:8082/actuator/info
```

---

## 👥 Proyecto Académico

Desarrollado por el equipo **Grupo Cordillera** como parte de la implementación de una arquitectura basada en microservicios utilizando Spring Boot, Docker, MySQL, pruebas automatizadas y cobertura de código con JaCoCo.
