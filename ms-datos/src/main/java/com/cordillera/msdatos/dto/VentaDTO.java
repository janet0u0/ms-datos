package com.cordillera.msdatos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO de Venta
 * Solo expone los campos necesarios al cliente.
 * El cliente NO necesita enviar id, fechaVenta ni estado,
 * esos los asigna el sistema automáticamente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {

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