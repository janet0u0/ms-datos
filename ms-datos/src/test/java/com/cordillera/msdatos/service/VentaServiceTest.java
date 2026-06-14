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
    // ── Reglas de negocio adicionales ─────────────────────────────

@Test
@DisplayName("Debe conservar todos los datos enviados al registrar")
void registrarVenta_DebeConservarDatosIngresados() {

    VentaRequestDTO dto = new VentaRequestDTO();
    dto.setSucursal("Valparaiso");
    dto.setMonto(35000.0);
    dto.setCantidad(4);
    dto.setOrigen("ECOMMERCE");

    when(ventaRepository.save(any(Venta.class)))
            .thenAnswer(i -> i.getArgument(0));

    var result = ventaService.registrarVenta(dto);

    assertEquals("Valparaiso", result.getSucursal());
    assertEquals(35000.0, result.getMonto());
    assertEquals(4, result.getCantidad());
    assertEquals("ECOMMERCE", result.getOrigen());
}

@Test
@DisplayName("Debe guardar una sola vez en el repositorio")
void registrarVenta_DebeGuardarUnaSolaVez() {

    VentaRequestDTO dto = new VentaRequestDTO();
    dto.setSucursal("Santiago");
    dto.setMonto(10000.0);
    dto.setCantidad(2);
    dto.setOrigen("POS");

    when(ventaRepository.save(any(Venta.class)))
            .thenAnswer(i -> i.getArgument(0));

    ventaService.registrarVenta(dto);

    verify(ventaRepository, times(1))
            .save(any(Venta.class));
}

@Test
@DisplayName("Debe verificar existencia antes de eliminar")
void eliminarVenta_DebeVerificarExistenciaAntesDeEliminar() {

    when(ventaRepository.existsById(1L))
            .thenReturn(true);

    doNothing().when(ventaRepository)
            .deleteById(1L);

    ventaService.eliminarVenta(1L);

    verify(ventaRepository, times(1))
            .existsById(1L);

    verify(ventaRepository, times(1))
            .deleteById(1L);
}

@Test
@DisplayName("Debe retornar exactamente el total acumulado")
void obtenerTotalVentas_DebeSumarCorrectamente() {

    when(ventaRepository.findAll())
            .thenReturn(List.of(
                    Venta.builder().monto(10000.0).build(),
                    Venta.builder().monto(20000.0).build(),
                    Venta.builder().monto(30000.0).build()
            ));

    Double total = ventaService.obtenerTotalVentas();

    assertEquals(60000.0, total);
}
}