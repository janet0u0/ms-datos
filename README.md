# 📊 MS-Datos - Grupo Cordillera

Microservicio encargado de centralizar, almacenar y consultar las ventas generadas por las distintas sucursales del Grupo Cordillera.

---

## 🛠️ Tecnologías

* Java 17
* Spring Boot 3.3.5
* Spring Data JPA
* Spring Boot Actuator
* MySQL 8.0
* Docker
* Lombok
* Maven
* JUnit 5
* Mockito
* JaCoCo

---

## 🎯 Patrones Aplicados

* **Repository Pattern**: abstrae el acceso a la base de datos.
* **DTO Pattern**: separa el modelo interno de la API.
* **Builder Pattern**: facilita la construcción de entidades mediante Lombok.

---

## ✅ Requisitos

* Java 17
* Maven
* Docker Desktop

---

## 🚀 Instalación y Ejecución

### Opción 1: Docker (Recomendado)

```bash
docker compose up --build
```

### Opción 2: Ejecución Local

#### 1. Clonar repositorio

```bash
git clone https://github.com/janet0u0/ms-datos
cd ms-datos
```

#### 2. Levantar MySQL

```bash
docker compose up -d
```

#### 3. Ejecutar aplicación

```bash
.\mvnw spring-boot:run
```

Disponible en:

```text
http://localhost:8083
```

---

## 🔗 Endpoints

| Método | Endpoint                              | Descripción                    |
| ------ | ------------------------------------- | ------------------------------ |
| GET    | /api/datos/ventas                     | Obtiene todas las ventas       |
| GET    | /api/datos/ventas/sucursal/{sucursal} | Obtiene ventas por sucursal    |
| GET    | /api/datos/ventas/total               | Obtiene el monto total vendido |
| POST   | /api/datos/ventas                     | Registra una nueva venta       |

---

## 📝 Ejemplo de Uso

### Registrar Venta

```json
POST /api/datos/ventas

{
  "sucursal": "Santiago Centro",
  "monto": 150000,
  "cantidad": 3,
  "origen": "POS"
}
```

### Respuesta

```json
{
  "id": 1,
  "sucursal": "Santiago Centro",
  "monto": 150000,
  "cantidad": 3,
  "origen": "POS",
  "fechaVenta": "2026-05-07T00:00:00",
  "estado": "PROCESADO"
}
```

---

## 📋 Catálogo de Valores

### Origen de Venta

| Código    | Descripción                       |
| --------- | --------------------------------- |
| POS       | Venta en tienda física            |
| ECOMMERCE | Venta realizada por canal digital |

### Estado de Venta

| Estado    | Descripción                      |
| --------- | -------------------------------- |
| PROCESADO | Venta registrada correctamente   |
| PENDIENTE | Venta pendiente de procesamiento |

---

## 📂 Estructura del Proyecto

```text
ms-datos/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/cordillera/msdatos/
│   │   │       ├── controller/
│   │   │       │   └── VentaController.java
│   │   │       ├── dto/
│   │   │       │   ├── VentaRequestDTO.java
│   │   │       │   └── VentaResponseDTO.java
│   │   │       ├── model/
│   │   │       │   └── Venta.java
│   │   │       ├── repository/
│   │   │       │   └── VentaRepository.java
│   │   │       ├── service/
│   │   │       │   └── VentaService.java
│   │   │       └── MsDatosApplication.java
│   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       ├── java/
│       │   └── com/cordillera/msdatos/
│       │       ├── controller/
│       │       │   └── VentaControllerTest.java
│       │       ├── dto/
│       │       │   ├── VentaRequestDTOTest.java
│       │       │   └── VentaResponseDTOTest.java
│       │       ├── repository/
│       │       │   └── VentaRepositoryTest.java
│       │       ├── service/
│       │       │   └── VentaServiceTest.java
│       │       └── MsDatosApplicationTests.java
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

---

## 📌 Componentes Principales

```text
controller/   → Exposición de endpoints REST
dto/          → Objetos de transferencia de datos
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

El proyecto incorpora pruebas unitarias para:

* Controller
* Service
* Repository
* DTO

Herramientas utilizadas:

* JUnit 5
* Mockito
* JaCoCo

---

## 📡 Monitoreo

### Estado de Salud

```http
GET http://localhost:8083/actuator/health
```

### Información de la Aplicación

```http
GET http://localhost:8083/actuator/info
```
