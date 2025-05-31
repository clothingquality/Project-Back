package com.co.quality.clothing.services;

import com.co.quality.clothing.entity.Productos;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ProductosService {
    List<Productos> obtenerTodos();

    Page<Productos> obtenerPorProducto(Long producto, Pageable pageable, Long categoriaId, Long marcaId,
                                       Long calidadId, Long descuento, Long precioInicio, Long precioFin, Long nuevo);

    ResponseEntity<Productos> obtenerPorId(Long id);

    Productos crear(Productos producto);

    ResponseEntity<Productos> actualizar(Long id, Productos datos);

    ResponseEntity<Void> eliminar(Long id);
}
