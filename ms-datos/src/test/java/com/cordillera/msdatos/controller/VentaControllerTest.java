package com.cordillera.msdatos.controller;

import com.cordillera.msdatos.dto.VentaRequestDTO;
import com.cordillera.msdatos.dto.VentaResponseDTO;
import com.cordillera.msdatos.service.VentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VentaController.class)
class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VentaService ventaService;

    @Test
    void obtenerVentasDebeRetornarLista() throws Exception {

        VentaResponseDTO venta = new VentaResponseDTO(
                1L,
                "Santiago",
                15000.0,
                2,
                "POS",
                LocalDateTime.now(),
                "PROCESADO"
        );

        when(ventaService.obtenerTodas())
                .thenReturn(List.of(venta));

        mockMvc.perform(get("/api/datos/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sucursal").value("Santiago"));
    }

    @Test
    void obtenerTotalDebeRetornarTotalVentas() throws Exception {

        when(ventaService.obtenerTotalVentas())
                .thenReturn(50000.0);

        mockMvc.perform(get("/api/datos/ventas/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(50000.0));
    }

    @Test
    void obtenerPorSucursalDebeRetornarVentas() throws Exception {

        VentaResponseDTO venta = new VentaResponseDTO(
                1L,
                "Valparaiso",
                20000.0,
                3,
                "ECOMMERCE",
                LocalDateTime.now(),
                "PROCESADO"
        );

        when(ventaService.obtenerPorSucursal("Valparaiso"))
                .thenReturn(List.of(venta));

        mockMvc.perform(get("/api/datos/ventas/sucursal/Valparaiso"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sucursal").value("Valparaiso"));
    }

    @Test
    void obtenerPorOrigenDebeRetornarVentas() throws Exception {

        VentaResponseDTO venta = new VentaResponseDTO(
                1L,
                "Santiago",
                10000.0,
                1,
                "POS",
                LocalDateTime.now(),
                "PROCESADO"
        );

        when(ventaService.obtenerPorOrigen("POS"))
                .thenReturn(List.of(venta));

        mockMvc.perform(get("/api/datos/ventas/origen/POS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].origen").value("POS"));
    }

    @Test
    void obtenerPorEstadoDebeRetornarVentas() throws Exception {

        VentaResponseDTO venta = new VentaResponseDTO(
                1L,
                "Santiago",
                12000.0,
                2,
                "POS",
                LocalDateTime.now(),
                "PROCESADO"
        );

        when(ventaService.obtenerPorEstado("PROCESADO"))
                .thenReturn(List.of(venta));

        mockMvc.perform(get("/api/datos/ventas/estado/PROCESADO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("PROCESADO"));
    }

    @Test
    void registrarVentaDebeRetornarCreated() throws Exception {

        VentaRequestDTO request = new VentaRequestDTO(
                "Santiago",
                15000.0,
                2,
                "POS"
        );

        VentaResponseDTO response = new VentaResponseDTO(
                1L,
                "Santiago",
                15000.0,
                2,
                "POS",
                LocalDateTime.now(),
                "PROCESADO"
        );

        when(ventaService.registrarVenta(any(VentaRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/datos/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("PROCESADO"));
    }

    @Test
    void eliminarVentaDebeRetornarNoContent() throws Exception {

        doNothing().when(ventaService).eliminarVenta(1L);

        mockMvc.perform(delete("/api/datos/ventas/1"))
                .andExpect(status().isNoContent());
    }
}