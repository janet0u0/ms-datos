# 📊 MS-Datos - Grupo Cordillera

Microservicio encargado de la gestión, almacenamiento y consulta de las ventas registradas por las sucursales del Grupo Cordillera.

Forma parte de una arquitectura basada en microservicios desarrollada con Spring Boot, Docker y MySQL, permitiendo centralizar la información de ventas para su posterior análisis mediante KPIs y paneles de visualización.

---

# 🛠️ Tecnologías Utilizadas

* Java 17
* Spring Boot 3.3.5
* Spring Data JPA
* Spring Boot Actuator
* MySQL 8.0
* Docker
* Maven
* Lombok
* JUnit 5
* Mockito
* MockMvc
* JaCoCo

---

# 🎯 Patrones de Diseño Aplicados

## Repository Pattern

Permite abstraer el acceso a los datos mediante Spring Data JPA.

## DTO Pattern

Separa la representación interna de las entidades de los datos expuestos por la API.

## Builder Pattern

Implementado mediante Lombok para facilitar la construcción de objetos.

---

# 📋 Requisitos

* Java 17
* Maven 3.9+
* Docker Desktop

---

# 🚀 Ejecución del Sistema

## Arquitectura Integrada

MS-Datos forma parte del ecosistema de microservicios:

* BFF-Cordillera
* MS-Usuarios
* MS-Datos
* MS-KPI
* Frontend React
* Bases de datos MySQL independientes

## Levantamiento Recomendado

El sistema completo debe iniciarse desde el proyecto:

```bash
bff-cordillera
```

Ejecutar:

```bash
docker compose up --build
```

Este comando levanta automáticamente:

* mysql-ms-datos
* mysql-ms-usuarios
* mysql-ms-kpi
* ms-datos
* ms-usuarios
* ms-kpi
* bff-cordillera

No es necesario ejecutar individualmente los docker-compose de cada microservicio.

---

# 🌐 URL Base

```text
http://localhost:8083
```

---

# 🔗 Endpoints Disponibles

| Método | Endpoint                              | Descripción                |
| ------ | ------------------------------------- | -------------------------- |
| GET    | /api/datos/ventas                     | Obtener todas las ventas   |
| GET    | /api/datos/ventas/total               | Obtener total de ventas    |
| GET    | /api/datos/ventas/sucursal/{sucursal} | Buscar ventas por sucursal |
| GET    | /api/datos/ventas/origen/{origen}     | Buscar ventas por origen   |
| GET    | /api/datos/ventas/estado/{estado}     | Buscar ventas por estado   |
| POST   | /api/datos/ventas                     | Registrar venta            |
| DELETE | /api/datos/ventas/{id}                | Eliminar venta             |

---

# 📝 Ejemplo de Registro de Venta

## Solicitud

```json
{
  "sucursal": "Santiago Centro",
  "monto": 150000,
  "cantidad": 3,
  "origen": "POS"
}
```

## Respuesta

```json
{
  "id": 1,
  "sucursal": "Santiago Centro",
  "monto": 150000,
  "cantidad": 3,
  "origen": "POS",
  "fechaVenta": "2026-06-11T15:30:00",
  "estado": "PROCESADO"
}
```

---

# 📌 Reglas de Negocio Implementadas

Durante el registro de ventas se aplican las siguientes validaciones:

### Regla 1

La sucursal es obligatoria.

```text
No puede ser nula ni vacía.
```

### Regla 2

El monto debe ser mayor a cero.

```text
Monto > 0
```

### Regla 3

La cantidad debe ser mayor a cero.

```text
Cantidad > 0
```

### Regla 4

El origen de la venta es obligatorio.

```text
No puede ser nulo ni vacío.
```

### Regla 5

La fecha de venta se genera automáticamente.

```text
LocalDateTime.now()
```

### Regla 6

Toda venta registrada queda con estado:

```text
PROCESADO
```

---

# 📂 Estructura del Proyecto

```text
ms-datos
│
├── src
│
├── main
│   └── java/com/cordillera/msdatos
│
│       ├── config
│       │   └── CorsConfig.java
│       │
│       ├── controller
│       │   └── VentaController.java
│       │
│       ├── dto
│       │   ├── VentaRequestDTO.java
│       │   └── VentaResponseDTO.java
│       │
│       ├── exception
│       │   ├── GlobalExceptionHandler.java
│       │   └── ResourceNotFoundException.java
│       │
│       ├── model
│       │   └── Venta.java
│       │
│       ├── repository
│       │   └── VentaRepository.java
│       │
│       ├── service
│       │   └── VentaService.java
│       │
│       └── MsDatosApplication.java
│
├── test
│   ├── java/com/cordillera/msdatos
│   │   ├── controller
│   │   │   └── VentaControllerTest.java
│   │   ├── exception
│   │   │   └── GlobalExceptionHandlerTest.java
│   │   ├── repository
│   │   │   └── VentaRepositoryTest.java
│   │   ├── service
│   │   │   └── VentaServiceTest.java
│   │   └── MsDatosApplicationTests.java
│   │
│   └── resources
│       └── application-test.properties
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

# 🏗️ Arquitectura

```text
Cliente
   │
   ▼
VentaController
   │
   ▼
VentaService
   │
   ▼
VentaRepository
   │
   ▼
MySQL
```

---

# 🧪 Pruebas Automatizadas

El proyecto incorpora pruebas unitarias y de integración para garantizar la calidad del software.

## Controller

* VentaControllerTest

Valida:

* Endpoints REST
* Códigos HTTP
* Serialización JSON
* Integración con Service

## Service

* VentaServiceTest

Valida:

* Registro de ventas
* Eliminación de ventas
* Cálculo de totales
* Búsquedas por filtros
* Reglas de negocio
* Manejo de excepciones

## Repository

* VentaRepositoryTest

Valida:

* Persistencia de datos
* Consultas JPA
* Eliminación de registros
* Búsquedas por sucursal
* Búsquedas por origen
* Búsquedas por estado

## Exception Handling

* GlobalExceptionHandlerTest

Valida:

* Error 400 Bad Request
* Error 404 Not Found
* Error 500 Internal Server Error
* Validaciones de entrada

---

# 📊 Cobertura de Código

Reporte generado con JaCoCo:

| Componente  | Cobertura |
| ----------- | --------- |
| Service     | 100%      |
| Controller  | 100%      |
| Repository  | 100%      |
| Exception   | 100%      |
| Config      | 100%      |
| Application | 100%      |

## Resultado Global

```text
Instructions: 100%
Branches:     100%
Lines:        100%
Methods:      100%
Classes:      100%
```

---

# 📡 Monitoreo

## Estado de Salud

```http
GET /actuator/health
```

## Información de la Aplicación

```http
GET /actuator/info
```

---

# 👥 Proyecto

Desarrollado por el equipo Grupo Cordillera como parte de la implementación de una arquitectura basada en microservicios utilizando Spring Boot, Docker, MySQL, pruebas automatizadas y cobertura de código mediante JaCoCo.
