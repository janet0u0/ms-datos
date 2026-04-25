package com.cordillera.msdatos.controller;

import com.cordillera.msdatos.dto.VentaDTO;
import com.cordillera.msdatos.model.Venta;
import com.cordillera.msdatos.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller REST del MS-Datos
 * Expone los endpoints de ventas
 * Recibe DTOs y devuelve entidades
 */
@RestController
@RequestMapping("/api/datos")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    // GET /api/datos/ventas
    @GetMapping("/ventas")
    public ResponseEntity<List<Venta>> obtenerVentas() {
        return ResponseEntity.ok(ventaService.obtenerTodas());
    }

    // GET /api/datos/ventas/sucursal/{sucursal}
    @GetMapping("/ventas/sucursal/{sucursal}")
    public ResponseEntity<List<Venta>> obtenerPorSucursal(
            @PathVariable String sucursal) {
        return ResponseEntity.ok(ventaService.obtenerPorSucursal(sucursal));
    }

    // POST /api/datos/ventas
    // @Valid activa las validaciones del DTO
    @PostMapping("/ventas")
    public ResponseEntity<Venta> registrarVenta(@Valid @RequestBody VentaDTO dto) {
        return ResponseEntity.ok(ventaService.registrarVenta(dto));
    }

    // GET /api/datos/ventas/total
    @GetMapping("/ventas/total")
    public ResponseEntity<Double> obtenerTotal() {
        return ResponseEntity.ok(ventaService.obtenerTotalVentas());
    }
}