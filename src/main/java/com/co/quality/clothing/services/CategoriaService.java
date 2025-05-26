package com.co.quality.clothing.services;

import com.co.quality.clothing.entity.Categoria;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface CategoriaService {
    List<Categoria> obtenerTodos();

    List<Categoria> obtenerPorProducto(Long producto);

    ResponseEntity<Categoria> obtenerPorId(Long id);

    Categoria crear(Categoria categoria);

    ResponseEntity<Categoria> actualizar(Long id, Categoria datos);

    ResponseEntity<Void> eliminar(Long id);
}
