package com.co.quality.clothing.services;

import com.co.quality.clothing.entity.Carrito;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface CarritoService {
    List<Carrito> obtenerTodos();

    List<Carrito> obtenerPorUsuario(Long usuario);

    ResponseEntity<Carrito> obtenerPorId(Long id);

    Carrito crear(Carrito carrito);

    ResponseEntity<Carrito> actualizar(Long id, Carrito datos);

    ResponseEntity<Void> eliminar(Long id);
}
