package com.co.quality.clothing.services;

import com.co.quality.clothing.dtos.Categorias;
import com.co.quality.clothing.dtos.Filtros;
import org.springframework.http.ResponseEntity;

public interface FiltrosService {
    ResponseEntity<Filtros> obtenerPorProducto(Long producto);

    ResponseEntity<Categorias> obtenerSubCategorias();
}
