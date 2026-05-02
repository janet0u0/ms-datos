# MS-Datos - Grupo Cordillera

## 📌 Descripción
Microservicio de Gestión de Datos Organizacionales.
Centraliza y consolida información de ventas proveniente
de múltiples fuentes (POS y E-commerce) del Grupo Cordillera.

## 🎯 Patrón aplicado
- **Repository Pattern**: Abstrae el acceso a la base de datos,
  permitiendo cambiar el motor de BD sin afectar la lógica de negocio.

## ⚙️ Tecnologías
- Java 17
- Spring Boot 3.5.14
- Spring Data JPA
- MySQL 8.0
- Lombok
- Maven

## 📁 Estructura del proyecto
ms-datos/
├── controller/  → VentaController (endpoints REST)
├── service/     → VentaService (lógica de negocio)
├── repository/  → VentaRepository (acceso a datos)
├── model/       → Venta (entidad JPA)
└── dto/         → VentaRequestDTO, VentaResponseDTO

## 🌐 Endpoints disponibles
| Método | URL | Descripción |
|--------|-----|-------------|
| GET | /api/datos/ventas | Lista todas las ventas |
| GET | /api/datos/ventas/sucursal/{s} | Ventas por sucursal |
| GET | /api/datos/ventas/total | Total acumulado de ventas |
| POST | /api/datos/ventas | Registrar nueva venta |

## 📦 Ejemplo POST /api/datos/ventas
```json
{
  "sucursal": "Santiago Centro",
  "monto": 150000,
  "cantidad": 3,
  "origen": "POS"
}
```

## 🐳 Cómo ejecutar con Docker
```bash
docker-compose up -d
mvn spring-boot:run
```

## 💻 Cómo ejecutar sin Docker
1. Iniciar MySQL (XAMPP)
2. Ejecutar: `mvn spring-boot:run`
3. Servidor en: http://localhost:8083

## 🗄️ Base de datos
- Motor: MySQL 8.0
- Base de datos: ms_datos_db
- Puerto Docker: 3308

## ✅ Requisitos
- Java 17+
- Maven
- MySQL 8.0 o Docker

## 👥 Autores
- Janet Huaylla Huayllas
- Bairo Pasten Codoceo