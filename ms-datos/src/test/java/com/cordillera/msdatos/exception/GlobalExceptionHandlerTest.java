package com.cordillera.msdatos.exception;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;


    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleNotFoundTest() {

        ResourceNotFoundException ex =
                new ResourceNotFoundException("Venta no encontrada");

        ResponseEntity<Map<String, String>> response =
                handler.handleNotFound(ex);

        assertEquals(404, response.getStatusCode().value());

        Map<String, String> body = response.getBody();
        assertNotNull(body);

        assertEquals(
                "Venta no encontrada",
                body.get("error")
        );
    }

    @Test
    void handleBadRequestTest() {

        IllegalArgumentException ex =
                new IllegalArgumentException("Datos inválidos");

        ResponseEntity<Map<String, String>> response =
                handler.handleBadRequest(ex);

        assertEquals(400, response.getStatusCode().value());

        Map<String, String> body = response.getBody();
        assertNotNull(body);

        assertEquals(
                "Datos inválidos",
                body.get("error")
        );
    }

    @Test
    void handleValidationTest() throws NoSuchMethodException {

        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(new Object(), "obj");

        bindingResult.addError(
                new FieldError(
                        "obj",
                        "monto",
                        "no puede ser nulo"
                )
        );

        Method method = this.getClass()
                .getDeclaredMethod("dummyMethod", String.class);

        MethodParameter methodParameter =
                new MethodParameter(method, 0);

        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(
                        methodParameter,
                        bindingResult
                );

        ResponseEntity<Map<String, String>> response =
                handler.handleValidation(ex);

        assertEquals(400, response.getStatusCode().value());

        Map<String, String> body = response.getBody();
        assertNotNull(body);

        assertTrue(
                body.get("error")
                        .contains("monto")
        );
    }

    @Test
    void handleGeneralTest() {

        Exception ex = new Exception("Error interno");

        ResponseEntity<Map<String, String>> response =
                handler.handleGeneral(ex);

        assertEquals(500, response.getStatusCode().value());

        Map<String, String> body = response.getBody();
        assertNotNull(body);

        assertEquals(
                "Error interno del servidor",
                body.get("error")
        );

        assertEquals(
                "Error interno",
                body.get("detalle")
        );
    }

    // Método dummy para MethodParameter
    @SuppressWarnings("unused")
    private void dummyMethod(String value) {
    }
}