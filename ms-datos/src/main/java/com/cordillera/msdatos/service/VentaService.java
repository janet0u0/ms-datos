package com.cordillera.msdatos.service;

import com.cordillera.msdatos.dto.VentaDTO;
import com.cordillera.msdatos.model.Venta;
import com.cordillera.msdatos.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de Gestión de Ventas
 * Contiene la lógica de negocio del MS-Datos
 * Usa DTO para separar la capa de presentación del modelo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;

    // Obtener todas las ventas
    public List<Venta> obtenerTodas() {
        log.info("Obteniendo todas las ventas");
        return ventaRepository.findAll();
    }

    // Obtener ventas por sucursal
    public List<Venta> obtenerPorSucursal(String sucursal) {
        log.info("Obteniendo ventas de sucursal: {}", sucursal);
        return ventaRepository.findBySucursal(sucursal);
    }

    // Registrar nueva venta recibiendo DTO
    public Venta registrarVenta(VentaDTO dto) {
        log.info("Registrando nueva venta de sucursal: {}", dto.getSucursal());

        // Convertir DTO a entidad
        Venta venta = new Venta();
        venta.setSucursal(dto.getSucursal());
        venta.setMonto(dto.getMonto());
        venta.setCantidad(dto.getCantidad());
        venta.setOrigen(dto.getOrigen());
        venta.setFechaVenta(LocalDateTime.now());
        venta.setEstado("PROCESADO");

        return ventaRepository.save(venta);
    }

    // Obtener total de ventas
    public Double obtenerTotalVentas() {
        log.info("Calculando total de ventas");
        return ventaRepository.findAll()
                .stream()
                .mapToDouble(Venta::getMonto)
                .sum();
    }
}