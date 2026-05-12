package com.cordillera.msdatos.controller;

import com.cordillera.msdatos.dto.VentaRequestDTO;
import com.cordillera.msdatos.dto.VentaResponseDTO;
import com.cordillera.msdatos.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller REST del MS-Datos - Grupo Cordillera
 * Expone los endpoints de gestión de ventas.
 */
@RestController
@RequestMapping("/api/datos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VentaController {

    private final VentaService ventaService;

    /**
     * GET /api/datos/ventas
     */
    @GetMapping("/ventas")
    public ResponseEntity<List<VentaResponseDTO>> obtenerVentas() {
        return ResponseEntity.ok(ventaService.obtenerTodas());
    }

    /**
     * GET /api/datos/ventas/total
     */
    @GetMapping("/ventas/total")
    public ResponseEntity<Double> obtenerTotal() {
        return ResponseEntity.ok(ventaService.obtenerTotalVentas());
    }

    /**
     * POST /api/datos/ventas
     */
    @PostMapping("/ventas")
    public ResponseEntity<VentaResponseDTO> registrarVenta(
            @Valid @RequestBody VentaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventaService.registrarVenta(dto));
    }

    /**
     * DELETE /api/datos/ventas/{id}
     * Elimina una venta específica por su ID.
     * Si el ID existe, retorna 204 (No Content).
     */
    @DeleteMapping("/ventas/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}