package com.co.quality.clothing.services;

import com.co.quality.clothing.entity.Precios;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface PreciosService {
    List<Precios> obtenerTodos();

    ResponseEntity<Precios> obtenerPorId(Long id);

    Precios crear(Precios precios);

    ResponseEntity<Precios> actualizar(Long id, Precios datos);

    ResponseEntity<Void> eliminar(Long id);
}
