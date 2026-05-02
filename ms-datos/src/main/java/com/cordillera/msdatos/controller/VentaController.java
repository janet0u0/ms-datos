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
 * Recibe VentaRequestDTO y devuelve VentaResponseDTO.
 */
@RestController
@RequestMapping("/api/datos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VentaController {

    private final VentaService ventaService;

    /**
     * GET /api/datos/ventas
     * Lista todas las ventas del sistema
     */
    @GetMapping("/ventas")
    public ResponseEntity<List<VentaResponseDTO>> obtenerVentas() {
        return ResponseEntity.ok(ventaService.obtenerTodas());
    }

    /**
     * GET /api/datos/ventas/sucursal/{sucursal}
     * Lista ventas por sucursal
     */
    @GetMapping("/ventas/sucursal/{sucursal}")
    public ResponseEntity<List<VentaResponseDTO>> obtenerPorSucursal(
            @PathVariable String sucursal) {
        return ResponseEntity.ok(ventaService.obtenerPorSucursal(sucursal));
    }

    /**
     * GET /api/datos/ventas/total
     * Retorna el total acumulado de ventas
     */
    @GetMapping("/ventas/total")
    public ResponseEntity<Double> obtenerTotal() {
        return ResponseEntity.ok(ventaService.obtenerTotalVentas());
    }

    /**
     * POST /api/datos/ventas
     * Registra una nueva venta
     * @Valid activa las validaciones del DTO
     */
    @PostMapping("/ventas")
    public ResponseEntity<VentaResponseDTO> registrarVenta(
            @Valid @RequestBody VentaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventaService.registrarVenta(dto));
    }
}