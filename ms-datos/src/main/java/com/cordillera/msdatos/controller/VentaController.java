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
 * CORS configurado en CorsConfig.java
 */
@RestController
@RequestMapping("/api/datos")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping("/ventas")
    public ResponseEntity<List<VentaResponseDTO>> obtenerVentas() {
        return ResponseEntity.ok(ventaService.obtenerTodas());
    }

    @GetMapping("/ventas/total")
    public ResponseEntity<Double> obtenerTotal() {
        return ResponseEntity.ok(ventaService.obtenerTotalVentas());
    }

    // ✅ AGREGADO: endpoint para filtrar por sucursal
    @GetMapping("/ventas/sucursal/{sucursal}")
    public ResponseEntity<List<VentaResponseDTO>> obtenerPorSucursal(
            @PathVariable String sucursal) {
        return ResponseEntity.ok(ventaService.obtenerPorSucursal(sucursal));
    }

    // ✅ AGREGADO: endpoint para filtrar por origen (POS o ECOMMERCE)
    @GetMapping("/ventas/origen/{origen}")
    public ResponseEntity<List<VentaResponseDTO>> obtenerPorOrigen(
            @PathVariable String origen) {
        return ResponseEntity.ok(ventaService.obtenerPorOrigen(origen));
    }

    // ✅ AGREGADO: endpoint para filtrar por estado (PROCESADO | PENDIENTE)
    @GetMapping("/ventas/estado/{estado}")
    public ResponseEntity<List<VentaResponseDTO>> obtenerPorEstado(
            @PathVariable String estado) {
        return ResponseEntity.ok(ventaService.obtenerPorEstado(estado));
    }

    @PostMapping("/ventas")
    public ResponseEntity<VentaResponseDTO> registrarVenta(
            @Valid @RequestBody VentaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventaService.registrarVenta(dto));
    }

    @DeleteMapping("/ventas/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}