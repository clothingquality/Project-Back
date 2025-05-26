package com.co.quality.clothing.services;

import com.co.quality.clothing.entity.Calidad;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface CalidadService {

    List<Calidad> obtenerTodos();

    List<Calidad> obtenerPorProducto(Long producto);

    ResponseEntity<Calidad> obtenerPorId(Long id);

    Calidad crear(Calidad calidad);

    ResponseEntity<Calidad> actualizar(Long id, Calidad datos);

    ResponseEntity<Void> eliminar(Long id);
}
