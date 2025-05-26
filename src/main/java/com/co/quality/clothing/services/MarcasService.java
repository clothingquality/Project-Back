package com.co.quality.clothing.services;

import com.co.quality.clothing.entity.Marcas;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface MarcasService {
    List<Marcas> obtenerTodos();

    List<Marcas> obtenerPorProducto(Long producto);

    ResponseEntity<Marcas> obtenerPorId(Long id);

    Marcas crear(Marcas marcas);

    ResponseEntity<Marcas> actualizar(Long id, Marcas datos);

    ResponseEntity<Void> eliminar(Long id);
}
