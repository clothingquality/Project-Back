package com.co.quality.clothing.services;

import com.co.quality.clothing.entity.Colores;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ColoresService {

    List<Colores> obtenerTodos();

    ResponseEntity<Colores> obtenerPorId(Long id);

    Colores crear(Colores colores);

    ResponseEntity<Colores> actualizar(Long id, Colores datos);

    ResponseEntity<Void> eliminar(Long id);
}
