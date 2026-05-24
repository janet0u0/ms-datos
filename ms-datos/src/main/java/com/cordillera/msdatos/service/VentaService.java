package com.cordillera.msdatos.service;

import com.cordillera.msdatos.dto.VentaRequestDTO;
import com.cordillera.msdatos.dto.VentaResponseDTO;
import com.cordillera.msdatos.exception.ResourceNotFoundException;
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

    public List<VentaResponseDTO> obtenerTodas() {
        log.info("Obteniendo todas las ventas desde la base de datos");
        return ventaRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<VentaResponseDTO> obtenerPorSucursal(String sucursal) {
        log.info("Consultando ventas para la sucursal: {}", sucursal);
        return ventaRepository.findBySucursal(sucursal)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<VentaResponseDTO> obtenerPorOrigen(String origen) {
        log.info("Consultando ventas por origen: {}", origen);
        return ventaRepository.findByOrigen(origen)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<VentaResponseDTO> obtenerPorEstado(String estado) {
        log.info("Consultando ventas por estado: {}", estado);
        return ventaRepository.findByEstado(estado)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ✅ CORREGIDO: usa @Builder en vez de setters
    public VentaResponseDTO registrarVenta(VentaRequestDTO dto) {
        log.info("Iniciando registro de venta para sucursal: {}", dto.getSucursal());
        Venta venta = Venta.builder()
                .sucursal(dto.getSucursal())
                .monto(dto.getMonto())
                .cantidad(dto.getCantidad())
                .origen(dto.getOrigen())
                .fechaVenta(LocalDateTime.now())
                .estado("PROCESADO")
                .build();
        return mapToDTO(ventaRepository.save(venta));
    }

    public Double obtenerTotalVentas() {
        log.info("Calculando sumatoria total de ventas");
        return ventaRepository.findAll()
                .stream()
                .mapToDouble(Venta::getMonto)
                .sum();
    }

    // ✅ CORREGIDO: lanza ResourceNotFoundException en vez de solo loguear
    public void eliminarVenta(Long id) {
        log.info("Intentando eliminar venta con ID: {}", id);
        if (!ventaRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Venta no encontrada con ID: " + id);
        }
        ventaRepository.deleteById(id);
        log.info("Venta con ID {} eliminada correctamente", id);
    }
}