package com.cordillera.msdatos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entidad Venta - MS-Datos
 * Representa una transacción de venta de cualquier sucursal
 * del Grupo Cordillera (POS o E-commerce).
 *
 * Patrón aplicado: Repository Pattern
 * Esta entidad es gestionada por VentaRepository
 * que abstrae el acceso a la base de datos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sucursal;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private Integer cantidad;

    // Origen de la venta: POS o ECOMMERCE
    @Column(nullable = false)
    private String origen;

    @Column(nullable = false)
    private LocalDateTime fechaVenta;

    // Estado: PROCESADO | PENDIENTE
    @Column(nullable = false)
    private String estado;
}