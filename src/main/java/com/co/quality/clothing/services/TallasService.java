package com.co.quality.clothing.services;

import com.co.quality.clothing.entity.Tallas;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface TallasService {
    List<Tallas> obtenerTodos();

    List<Tallas> obtenerPorProducto(Long producto);

    ResponseEntity<Tallas> obtenerPorId(Long id);

    Tallas crear(Tallas talla);

    ResponseEntity<Tallas> actualizar(Long id, Tallas datos);

    ResponseEntity<Void> eliminar(Long id);
}
