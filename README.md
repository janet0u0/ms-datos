markdown# MS-Datos - Grupo Cordillera

Microservicio de gestión de ventas del Grupo Cordillera. 
Centraliza y procesa las transacciones de ventas de todas las sucursales.

## Tecnologías
- Java 17
- Spring Boot 3.3.5
- Spring Data JPA
- Spring Actuator
- MySQL 8.0
- Docker
- Lombok
- Maven

## Patrones Aplicados
- **Repository Pattern**: Abstrae el acceso a la base de datos
- **DTO Pattern**: Separa el modelo interno de la API
- **Builder Pattern**: Construcción de entidades con Lombok @Builder

## Requisitos
- Java 17
- Docker Desktop
- Maven

## Instalación y Ejecución

### 1. Clonar el repositorio
```bash
git clone 
cd ms-datos
```

### 2. Levantar MySQL con Docker
```bash
docker-compose up -d
```

### 3. Ejecutar el microservicio
```bash
.\mvnw spring-boot:run
```

El servicio quedará disponible en `http://localhost:8083`

## Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/datos/ventas | Listar todas las ventas |
| GET | /api/datos/ventas/sucursal/{sucursal} | Ventas por sucursal |
| GET | /api/datos/ventas/total | Total acumulado de ventas |
| POST | /api/datos/ventas | Registrar nueva venta |

## Ejemplo de uso

### Registrar venta
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

### Orígenes disponibles
| Origen | Descripción |
|--------|-------------|
| POS | Venta en tienda física |
| ECOMMERCE | Venta en línea |

### Estados disponibles
| Estado | Descripción |
|--------|-------------|
| PROCESADO | Venta procesada correctamente |
| PENDIENTE | Venta pendiente de procesar |

## Estructura del proyecto
ms-datos/
├── src/
│   ├── main/
│   │   ├── java/com/cordillera/msdatos/
│   │   │   ├── controller/
│   │   │   │   └── VentaController.java
│   │   │   ├── dto/
│   │   │   │   ├── VentaRequestDTO.java
│   │   │   │   └── VentaResponseDTO.java
│   │   │   ├── model/
│   │   │   │   └── Venta.java
│   │   │   ├── repository/
│   │   │   │   └── VentaRepository.java
│   │   │   └── service/
│   │   │       └── VentaService.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── docker-compose.yml
├── pom.xml
└── README.md

## Monitoreo
El microservicio incluye Spring Actuator para monitoreo:
```
GET http://localhost:8083/actuator/health
GET http://localhost:8083/actuator/info
```