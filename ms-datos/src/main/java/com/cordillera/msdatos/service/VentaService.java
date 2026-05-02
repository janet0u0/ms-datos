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
 * Contiene la lógica de negocio del microservicio.
 *
 * Patrón aplicado: Repository Pattern
 * Accede a datos exclusivamente a través de VentaRepository,
 * sin conocer los detalles de la base de datos.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;

    /**
     * Convierte entidad Venta a VentaResponseDTO.
     * Separa la capa de persistencia de la presentación.
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
     * Lista todas las ventas del sistema.
     */
    public List<VentaResponseDTO> obtenerTodas() {
        log.info("Obteniendo todas las ventas");
        return ventaRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    /**
     * Lista ventas por sucursal.
     */
    public List<VentaResponseDTO> obtenerPorSucursal(String sucursal) {
        log.info("Obteniendo ventas de sucursal: {}", sucursal);
        return ventaRepository.findBySucursal(sucursal)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    /**
     * Registra una nueva venta desde el DTO.
     * Asigna automáticamente fechaVenta y estado.
     */
    public VentaResponseDTO registrarVenta(VentaRequestDTO dto) {
        log.info("Registrando nueva venta de sucursal: {}", dto.getSucursal());

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
     * Calcula el total acumulado de todas las ventas.
     */
    public Double obtenerTotalVentas() {
        log.info("Calculando total de ventas");
        return ventaRepository.findAll()
                .stream()
                .mapToDouble(Venta::getMonto)
                .sum();
    }
}