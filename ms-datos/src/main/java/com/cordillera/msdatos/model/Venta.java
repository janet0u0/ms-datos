package com.cordillera.msdatos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entidad Venta
 * Representa una transacción de venta de cualquier sucursal
 * del Grupo Cordillera (POS o E-commerce)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(nullable = false)
    private String origen;

    @Column(nullable = false)
    private LocalDateTime fechaVenta;

    @Column(nullable = false)
    private String estado;
}