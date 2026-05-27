package com.cordillera.msdatos.repository;

import com.cordillera.msdatos.model.Venta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
// Forzamos a que use exactamente estas propiedades ignorando cualquier archivo de producción
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.show-sql=true"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VentaRepositoryTest {

    @Autowired
    private VentaRepository ventaRepository;

    @Test
    @DisplayName("Debe guardar una venta correctamente")
    void guardarVentaTest() {
        Venta venta = Venta.builder()
                .sucursal("Santiago")
                .monto(15000.0)
                .cantidad(2)
                .origen("ECOMMERCE")
                .fechaVenta(LocalDateTime.now())
                .estado("PROCESADO")
                .build();

        Venta guardada = ventaRepository.save(venta);

        assertNotNull(guardada.getId());
        assertEquals("Santiago", guardada.getSucursal());
    }

    @Test
    @DisplayName("Debe listar todas las ventas")
    void findAllTest() {
        ventaRepository.save(Venta.builder()
                .sucursal("Santiago")
                .monto(10000.0)
                .cantidad(1)
                .origen("POS")
                .fechaVenta(LocalDateTime.now())
                .estado("PROCESADO")
                .build());

        ventaRepository.save(Venta.builder()
                .sucursal("Valparaiso")
                .monto(20000.0)
                .cantidad(3)
                .origen("ECOMMERCE")
                .fechaVenta(LocalDateTime.now())
                .estado("PENDIENTE")
                .build());

        List<Venta> ventas = ventaRepository.findAll();

        assertEquals(2, ventas.size());
    }

    @Test
    @DisplayName("Debe buscar ventas por sucursal")
    void findBySucursalTest() {
        ventaRepository.save(Venta.builder()
                .sucursal("Concepcion")
                .monto(5000.0)
                .cantidad(1)
                .origen("POS")
                .fechaVenta(LocalDateTime.now())
                .estado("PROCESADO")
                .build());

        List<Venta> resultado = ventaRepository.findBySucursal("Concepcion");

        assertFalse(resultado.isEmpty());
        assertEquals("Concepcion", resultado.get(0).getSucursal());
    }

    @Test
    @DisplayName("Debe buscar ventas por origen")
    void findByOrigenTest() {
        ventaRepository.save(Venta.builder()
                .sucursal("Temuco")
                .monto(8000.0)
                .cantidad(2)
                .origen("ECOMMERCE")
                .fechaVenta(LocalDateTime.now())
                .estado("PROCESADO")
                .build());

        List<Venta> resultado = ventaRepository.findByOrigen("ECOMMERCE");

        assertFalse(resultado.isEmpty());
        assertEquals("ECOMMERCE", resultado.get(0).getOrigen());
    }

    @Test
    @DisplayName("Debe buscar ventas por estado")
    void findByEstadoTest() {
        ventaRepository.save(Venta.builder()
                .sucursal("La Serena")
                .monto(12000.0)
                .cantidad(1)
                .origen("POS")
                .fechaVenta(LocalDateTime.now())
                .estado("PENDIENTE")
                .build());

        List<Venta> resultado = ventaRepository.findByEstado("PENDIENTE");

        assertFalse(resultado.isEmpty());
        assertEquals("PENDIENTE", resultado.get(0).getEstado());
    }

    @Test
    @DisplayName("Debe eliminar venta por ID")
    void deleteByIdTest() {
        Venta venta = ventaRepository.save(Venta.builder()
                .sucursal("Puerto Montt")
                .monto(30000.0)
                .cantidad(4)
                .origen("POS")
                .fechaVenta(LocalDateTime.now())
                .estado("PROCESADO")
                .build());

        Long id = venta.getId();

        ventaRepository.deleteById(id);

        assertFalse(ventaRepository.existsById(id));
    }
}