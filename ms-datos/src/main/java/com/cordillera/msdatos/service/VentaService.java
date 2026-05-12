package com.cordillera.msdatos.service;

import com.cordillera.msdatos.dto.VentaRequestDTO;
import com.cordillera.msdatos.dto.VentaResponseDTO;
import com.cordillera.msdatos.model.Venta;
import com.cordillera.msdatos.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de Gestión de Ventas - MS-Datos
 * Grupo Cordillera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;

    /**
     * Mapea la entidad Venta al DTO de respuesta.
     */
    private VentaResponseDTO mapToDTO(Venta venta) {
        return new VentaResponseDTO(
                venta.getId(),
                venta.getSucursal(),
                venta.getMonto(),
                venta.getCantidad(),
                venta.getOrigen(),
                venta.getFechaVenta(),
                venta.getEstado()
        );
    }

    /**
     * Obtiene todas las ventas registradas.
     */
    public List<VentaResponseDTO> obtenerTodas() {
        log.info("Obteniendo todas las ventas desde la base de datos");
        return ventaRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    /**
     * Obtiene ventas filtradas por sucursal.
     */
    public List<VentaResponseDTO> obtenerPorSucursal(String sucursal) {
        log.info("Consultando ventas para la sucursal: {}", sucursal);
        return ventaRepository.findBySucursal(sucursal)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    /**
     * Registra una nueva venta.
     */
    public VentaResponseDTO registrarVenta(VentaRequestDTO dto) {
        log.info("Iniciando registro de venta para sucursal: {}", dto.getSucursal());

        Venta venta = new Venta();
        venta.setSucursal(dto.getSucursal());
        venta.setMonto(dto.getMonto());
        venta.setCantidad(dto.getCantidad());
        venta.setOrigen(dto.getOrigen());
        venta.setFechaVenta(LocalDateTime.now());
        venta.setEstado("PROCESADO");

        return mapToDTO(ventaRepository.save(venta));
    }

    /**
     * Suma el monto de todas las ventas para el KPI total.
     */
    public Double obtenerTotalVentas() {
        log.info("Calculando sumatoria total de ventas");
        return ventaRepository.findAll()
                .stream()
                .mapToDouble(Venta::getMonto)
                .sum();
    }

    /**
     * Elimina una venta por su ID.
     * Se incluye validación de nulidad para evitar warnings de seguridad de tipos.
     */
    public void eliminarVenta(Long id) {
        log.info("Intentando eliminar venta con ID: {}", id);
        
        if (id != null && ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
            log.info("Venta con ID {} eliminada correctamente", id);
        } else {
            log.warn("No se pudo eliminar: el ID {} no existe o es nulo", id);
        }
    }
}