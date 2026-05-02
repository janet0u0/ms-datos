package com.cordillera.msdatos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para registro de Venta.
 * Solo expone los campos que el cliente puede enviar.
 * El ID, fechaVenta y estado los asigna el sistema automáticamente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaRequestDTO {

    @NotBlank(message = "La sucursal es obligatoria")
    private String sucursal;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    private Double monto;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotBlank(message = "El origen es obligatorio")
    private String origen; // POS o ECOMMERCE
}