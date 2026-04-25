package com.cordillera.msdatos.repository;

import com.cordillera.msdatos.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * PATRÓN REPOSITORY
 * =================
 * Abstrae el acceso a la base de datos.
 * La lógica de negocio no necesita conocer
 * los detalles de cómo se guardan los datos.
 */
@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    // Buscar ventas por sucursal
    List<Venta> findBySucursal(String sucursal);

    // Buscar ventas por origen (POS o ECOMMERCE)
    List<Venta> findByOrigen(String origen);

    // Buscar ventas por estado
    List<Venta> findByEstado(String estado);
}