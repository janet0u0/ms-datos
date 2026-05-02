package com.cordillera.msdatos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO de salida para respuestas de Venta.
 * Expone todos los campos incluyendo
 * los generados por el sistema (id, fechaVenta, estado).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponseDTO {

    private Long id;
    private String sucursal;
    private Double monto;
    private Integer cantidad;
    private String origen;
    private LocalDateTime fechaVenta;
    private String estado;
}