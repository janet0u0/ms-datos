# MS-Datos - Grupo Cordillera

## Descripción
Microservicio de Gestión de Datos Organizacionales.
Centraliza y consolida información de ventas proveniente
de múltiples fuentes (POS y E-commerce) del Grupo Cordillera.

## Patrón aplicado
- **Repository Pattern**: Abstrae el acceso a la base de datos,
  permitiendo cambiar el motor de BD sin afectar la lógica de negocio.

## Tecnologías
- Java 17
- Spring Boot 3.5.14
- Spring Data JPA
- MySQL
- Lombok
- Maven

## Estructura del proyecto
ms-datos/
├── controller/    → VentaController (endpoints REST)
├── service/       → VentaService (lógica de negocio)
├── repository/    → VentaRepository (acceso a datos)
├── model/         → Venta (entidad JPA)
└── dto/           → VentaDTO (objeto de transferencia)

## Endpoints disponibles
- GET  /api/datos/ventas              → Lista todas las ventas
- GET  /api/datos/ventas/sucursal/{s} → Ventas por sucursal
- GET  /api/datos/ventas/total        → Total de ventas
- POST /api/datos/ventas              → Registrar nueva venta

## Ejemplo POST /api/datos/ventas
{
  "sucursal": "Santiago Centro",
  "monto": 150000,
  "cantidad": 3,
  "origen": "POS"
}

## Base de datos
- Motor: MySQL
- Base de datos: ms_datos_db
- Puerto: 3306

## Cómo ejecutar
1. Iniciar MySQL (XAMPP)
2. Ejecutar: mvn spring-boot:run
3. Servidor en: http://localhost:8083

## Autores
- Janet Huaylla Huayllas
- Bairo Pasten Codoceo