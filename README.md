# 📊 MS-Datos - Grupo Cordillera

Microservicio de gestión de ventas del Grupo Cordillera. Centraliza y procesa las transacciones de ventas de todas las sucursales.

## 🛠️ Tecnologías
- Java 17
- Spring Boot 3.3.5
- Spring Data JPA
- Spring Actuator
- MySQL 8.0
- Docker
- Lombok
- Maven

## 🎯 Patrones Aplicados
- **Repository Pattern**: Abstrae el acceso a la base de datos
- **DTO Pattern**: Separa el modelo interno de la API
- **Builder Pattern**: Construcción de entidades con Lombok @Builder

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
git clone https://github.com/janet0u0/ms-datos
cd ms-datos
```

**2. Levantar MySQL con Docker**
```bash
docker-compose up -d
```

**3. Ejecutar el microservicio**
```bash
.\mvnw spring-boot:run
```
Disponible en `http://localhost:8083`

## 🔗 Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/datos/ventas | Listar todas las ventas |
| GET | /api/datos/ventas/sucursal/{sucursal} | Ventas por sucursal |
| GET | /api/datos/ventas/total | Total acumulado de ventas |
| POST | /api/datos/ventas | Registrar nueva venta |

## 📝 Ejemplo de uso

**Registrar venta**
```json
POST /api/datos/ventas
{
    "sucursal": "Santiago Centro",
    "monto": 150000,
    "cantidad": 3,
    "origen": "POS"
}
```

**Respuesta**
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

## 📋 Referencias

| Origen | Descripción |
|--------|-------------|
| POS | Venta en tienda física |
| ECOMMERCE | Venta en línea |

| Estado | Descripción |
|--------|-------------|
| PROCESADO | Venta procesada correctamente |
| PENDIENTE | Venta pendiente de procesar |

## 📂 Estructura del Proyecto

```text
ms-datos/
├── src/
│   ├── main/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   └── resources/
│   └── test/
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```

## 📌 Componentes principales

```text
controller/   → Endpoints REST
dto/          → Transferencia de datos
model/        → Entidades JPA
repository/   → Acceso a base de datos
service/      → Lógica de negocio
resources/    → Configuración
```

## 📡 Monitoreo

```
GET http://localhost:8083/actuator/health
GET http://localhost:8083/actuator/info
```
