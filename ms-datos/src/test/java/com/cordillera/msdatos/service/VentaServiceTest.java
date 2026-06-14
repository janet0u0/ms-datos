package com.cordillera.msdatos.service;

import com.cordillera.msdatos.dto.VentaRequestDTO;
import com.cordillera.msdatos.exception.ResourceNotFoundException;
import com.cordillera.msdatos.model.Venta;
import com.cordillera.msdatos.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    // ── obtenerTodas() ───────────────────────────────────────────────────

    @Test
    @DisplayName("Debe retornar lista de ventas cuando existen registros")
    void obtenerTodasTest() {
        when(ventaRepository.findAll()).thenReturn(List.of(venta));

        var result = ventaService.obtenerTodas();

        assertEquals(1, result.size());
        assertEquals("Santiago", result.get(0).getSucursal());
    }

    @Test
    @DisplayName("Debe retornar lista vacía cuando no hay ventas")
    void obtenerTodas_CuandoNoHayVentas_DeberiaRetornarVacio() {
        when(ventaRepository.findAll()).thenReturn(List.of());

        var result = ventaService.obtenerTodas();

        assertTrue(result.isEmpty());
    }

    // ── obtenerPorSucursal() ─────────────────────────────────────────────

    @Test
    @DisplayName("Debe retornar ventas por sucursal existente")
    void obtenerPorSucursalTest() {
        when(ventaRepository.findBySucursal("Santiago"))
                .thenReturn(List.of(venta));

        var result = ventaService.obtenerPorSucursal("Santiago");

        assertFalse(result.isEmpty());
        assertEquals("Santiago", result.get(0).getSucursal());
    }

    @Test
    @DisplayName("Debe retornar lista vacía cuando sucursal no existe")
    void obtenerPorSucursal_CuandoNoExiste_DeberiaRetornarVacio() {
        when(ventaRepository.findBySucursal("Arica"))
                .thenReturn(List.of());

        var result = ventaService.obtenerPorSucursal("Arica");

        assertTrue(result.isEmpty());
    }

    // ── obtenerPorOrigen() ───────────────────────────────────────────────

    @Test
    @DisplayName("Debe retornar ventas por origen POS")
    void obtenerPorOrigenTest() {
        when(ventaRepository.findByOrigen("POS"))
                .thenReturn(List.of(venta));

        var result = ventaService.obtenerPorOrigen("POS");

        assertEquals(1, result.size());
        assertEquals("POS", result.get(0).getOrigen());
    }

    @Test
    @DisplayName("Debe retornar lista vacía cuando origen no existe")
    void obtenerPorOrigen_CuandoNoExiste_DeberiaRetornarVacio() {
        when(ventaRepository.findByOrigen("DESCONOCIDO"))
                .thenReturn(List.of());

        var result = ventaService.obtenerPorOrigen("DESCONOCIDO");

        assertTrue(result.isEmpty());
    }

    // ── obtenerPorEstado() ───────────────────────────────────────────────

    @Test
    @DisplayName("Debe retornar ventas por estado PROCESADO")
    void obtenerPorEstadoTest() {
        when(ventaRepository.findByEstado("PROCESADO"))
                .thenReturn(List.of(venta));

        var result = ventaService.obtenerPorEstado("PROCESADO");

        assertEquals(1, result.size());
        assertEquals("PROCESADO", result.get(0).getEstado());
    }

    @Test
    @DisplayName("Debe retornar lista vacía cuando estado no tiene ventas")
    void obtenerPorEstado_CuandoNoExiste_DeberiaRetornarVacio() {
        when(ventaRepository.findByEstado("PENDIENTE"))
                .thenReturn(List.of());

        var result = ventaService.obtenerPorEstado("PENDIENTE");

        assertTrue(result.isEmpty());
    }

    // ── registrarVenta() ─────────────────────────────────────────────────

    @Test
    @DisplayName("Debe registrar venta y asignar estado PROCESADO automaticamente")
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
        assertEquals("PROCESADO", result.getEstado());
    }

    @Test
    @DisplayName("Debe asignar fechaVenta automaticamente al registrar")
    void registrarVenta_DebeAsignarFechaAutomaticamente() {
        VentaRequestDTO dto = new VentaRequestDTO();
        dto.setSucursal("Temuco");
        dto.setMonto(28000.0);
        dto.setCantidad(5);
        dto.setOrigen("TIENDA");

        when(ventaRepository.save(any(Venta.class)))
                .thenAnswer(i -> i.getArgument(0));

        var result = ventaService.registrarVenta(dto);

        assertNotNull(result.getFechaVenta());
        assertEquals("Temuco", result.getSucursal());
        assertEquals("PROCESADO", result.getEstado());
    }

    // ── obtenerTotalVentas() ─────────────────────────────────────────────

    @Test
    @DisplayName("Debe calcular correctamente la suma total de ventas")
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
    @DisplayName("Debe retornar 0 cuando no hay ventas registradas")
    void obtenerTotalVentas_CuandoNoHayVentas_DeberiaRetornarCero() {
        when(ventaRepository.findAll()).thenReturn(List.of());

        Double total = ventaService.obtenerTotalVentas();

        assertEquals(0.0, total);
    }

    @Test
    @DisplayName("Debe calcular correctamente total con una sola venta")
    void obtenerTotalVentas_ConUnaVenta_DeberiaRetornarMonto() {
        when(ventaRepository.findAll())
                .thenReturn(List.of(
                        Venta.builder().monto(50000.0).build()
                ));

        Double total = ventaService.obtenerTotalVentas();

        assertEquals(50000.0, total);
    }

    // ── eliminarVenta() ──────────────────────────────────────────────────

    @Test
    @DisplayName("Debe eliminar venta correctamente cuando existe")
    void eliminarVenta_okTest() {
        when(ventaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(ventaRepository).deleteById(1L);

        assertDoesNotThrow(() -> ventaService.eliminarVenta(1L));

        verify(ventaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepcion cuando venta no existe")
    void eliminarVenta_notFoundTest() {
        when(ventaRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> ventaService.eliminarVenta(99L));

        verify(ventaRepository, never()).deleteById(any());
    }
 // ── Reglas de negocio ─────────────────────────────────────────────

@Test
@DisplayName("Debe rechazar sucursal nula")
void registrarVenta_SucursalNula() {

    VentaRequestDTO dto = new VentaRequestDTO();
    dto.setSucursal(null);
    dto.setMonto(10000.0);
    dto.setCantidad(1);
    dto.setOrigen("POS");

    assertThrows(
            IllegalArgumentException.class,
            () -> ventaService.registrarVenta(dto)
    );
}

@Test
@DisplayName("Debe rechazar sucursal vacía")
void registrarVenta_SucursalVacia() {

    VentaRequestDTO dto = new VentaRequestDTO();
    dto.setSucursal("");
    dto.setMonto(10000.0);
    dto.setCantidad(1);
    dto.setOrigen("POS");

    assertThrows(
            IllegalArgumentException.class,
            () -> ventaService.registrarVenta(dto)
    );
}

@Test
@DisplayName("Debe rechazar sucursal con espacios")
void registrarVenta_SucursalBlank() {

    VentaRequestDTO dto = new VentaRequestDTO();
    dto.setSucursal("   ");
    dto.setMonto(10000.0);
    dto.setCantidad(1);
    dto.setOrigen("POS");

    assertThrows(
            IllegalArgumentException.class,
            () -> ventaService.registrarVenta(dto)
    );
}

@Test
@DisplayName("Debe rechazar monto igual a cero")
void registrarVenta_MontoCero() {

    VentaRequestDTO dto = new VentaRequestDTO();
    dto.setSucursal("Santiago");
    dto.setMonto(0.0);
    dto.setCantidad(1);
    dto.setOrigen("POS");

    assertThrows(
            IllegalArgumentException.class,
            () -> ventaService.registrarVenta(dto)
    );
}

@Test
@DisplayName("Debe rechazar monto negativo")
void registrarVenta_MontoNegativo() {

    VentaRequestDTO dto = new VentaRequestDTO();
    dto.setSucursal("Santiago");
    dto.setMonto(-1000.0);
    dto.setCantidad(1);
    dto.setOrigen("POS");

    assertThrows(
            IllegalArgumentException.class,
            () -> ventaService.registrarVenta(dto)
    );
}

@Test
@DisplayName("Debe rechazar cantidad igual a cero")
void registrarVenta_CantidadCero() {

    VentaRequestDTO dto = new VentaRequestDTO();
    dto.setSucursal("Santiago");
    dto.setMonto(10000.0);
    dto.setCantidad(0);
    dto.setOrigen("POS");

    assertThrows(
            IllegalArgumentException.class,
            () -> ventaService.registrarVenta(dto)
    );
}

@Test
@DisplayName("Debe rechazar cantidad negativa")
void registrarVenta_CantidadNegativa() {

    VentaRequestDTO dto = new VentaRequestDTO();
    dto.setSucursal("Santiago");
    dto.setMonto(10000.0);
    dto.setCantidad(-1);
    dto.setOrigen("POS");

    assertThrows(
            IllegalArgumentException.class,
            () -> ventaService.registrarVenta(dto)
    );
}

@Test
@DisplayName("Debe rechazar origen nulo")
void registrarVenta_OrigenNulo() {

    VentaRequestDTO dto = new VentaRequestDTO();
    dto.setSucursal("Santiago");
    dto.setMonto(10000.0);
    dto.setCantidad(1);
    dto.setOrigen(null);

    assertThrows(
            IllegalArgumentException.class,
            () -> ventaService.registrarVenta(dto)
    );
}

@Test
@DisplayName("Debe rechazar origen vacío")
void registrarVenta_OrigenVacio() {

    VentaRequestDTO dto = new VentaRequestDTO();
    dto.setSucursal("Santiago");
    dto.setMonto(10000.0);
    dto.setCantidad(1);
    dto.setOrigen("");

    assertThrows(
            IllegalArgumentException.class,
            () -> ventaService.registrarVenta(dto)
    );
}

@Test
@DisplayName("Debe rechazar origen con espacios")
void registrarVenta_OrigenBlank() {

    VentaRequestDTO dto = new VentaRequestDTO();
    dto.setSucursal("Santiago");
    dto.setMonto(10000.0);
    dto.setCantidad(1);
    dto.setOrigen("   ");

    assertThrows(
            IllegalArgumentException.class,
            () -> ventaService.registrarVenta(dto)
    );
}
@Test
@DisplayName("Debe rechazar monto nulo")
void registrarVenta_MontoNulo() {

    VentaRequestDTO dto = new VentaRequestDTO();
    dto.setSucursal("Santiago");
    dto.setMonto(null);
    dto.setCantidad(1);
    dto.setOrigen("POS");

    assertThrows(
            IllegalArgumentException.class,
            () -> ventaService.registrarVenta(dto)
    );
}
@Test
@DisplayName("Debe rechazar cantidad nula")
void registrarVenta_CantidadNula() {

    VentaRequestDTO dto = new VentaRequestDTO();
    dto.setSucursal("Santiago");
    dto.setMonto(10000.0);
    dto.setCantidad(null);
    dto.setOrigen("POS");

    assertThrows(
            IllegalArgumentException.class,
            () -> ventaService.registrarVenta(dto)
    );
}
}