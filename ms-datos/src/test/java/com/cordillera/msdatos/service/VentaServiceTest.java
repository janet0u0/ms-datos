package com.cordillera.msdatos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.cordillera.msdatos.dto.VentaRequestDTO;
import com.cordillera.msdatos.model.Venta;
import com.cordillera.msdatos.repository.VentaRepository;
import com.cordillera.msdatos.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@SuppressWarnings("unused")
@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @InjectMocks
    private VentaService ventaService;

    private Venta venta;

    @BeforeEach
    void setUp() {
        venta = Venta.builder()
                .id(1L)
                .sucursal("Santiago")
                .monto(10000.0)
                .cantidad(2)
                .origen("POS")
                .fechaVenta(LocalDateTime.now())
                .estado("PROCESADO")
                .build();
    }

    @Test
    void obtenerTodasTest() {

        when(ventaRepository.findAll())
                .thenReturn(List.of(venta));

        var result = ventaService.obtenerTodas();

        assertEquals(1, result.size());
        assertEquals("Santiago", result.get(0).getSucursal());
    }

    @Test
    void obtenerPorSucursalTest() {

        when(ventaRepository.findBySucursal("Santiago"))
                .thenReturn(List.of(venta));

        var result = ventaService.obtenerPorSucursal("Santiago");

        assertFalse(result.isEmpty());
    }

    @Test
    void obtenerPorOrigenTest() {

        when(ventaRepository.findByOrigen("POS"))
                .thenReturn(List.of(venta));

        var result = ventaService.obtenerPorOrigen("POS");

        assertEquals(1, result.size());
    }

    @Test
    void obtenerPorEstadoTest() {

        when(ventaRepository.findByEstado("PROCESADO"))
                .thenReturn(List.of(venta));

        var result = ventaService.obtenerPorEstado("PROCESADO");

        assertEquals(1, result.size());
    }

    @Test
    void registrarVentaTest() {

        VentaRequestDTO dto = new VentaRequestDTO();
        dto.setSucursal("Santiago");
        dto.setMonto(20000.0);
        dto.setCantidad(3);
        dto.setOrigen("ECOMMERCE");

        when(ventaRepository.save(any(Venta.class)))
                .thenAnswer(i -> i.getArgument(0));

        var result = ventaService.registrarVenta(dto);

        assertNotNull(result);
        assertEquals("Santiago", result.getSucursal());
    }

    @Test
    void obtenerTotalVentasTest() {

        when(ventaRepository.findAll())
                .thenReturn(List.of(
                        Venta.builder().monto(10000.0).build(),
                        Venta.builder().monto(5000.0).build()
                ));

        Double total = ventaService.obtenerTotalVentas();

        assertEquals(15000.0, total);
    }

    @Test
    void eliminarVenta_okTest() {

        when(ventaRepository.existsById(1L))
                .thenReturn(true);

        doNothing().when(ventaRepository).deleteById(1L);

        assertDoesNotThrow(() ->
                ventaService.eliminarVenta(1L)
        );

        verify(ventaRepository, times(1))
                .deleteById(1L);
    }

    @Test
    void eliminarVenta_notFoundTest() {

        when(ventaRepository.existsById(99L))
                .thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () ->
                ventaService.eliminarVenta(99L)
        );
    }
}