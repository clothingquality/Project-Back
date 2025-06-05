package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.dtos.Categorias;
import com.co.quality.clothing.dtos.Filtros;
import com.co.quality.clothing.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FiltrosServiceImpl implements FiltrosService {
    private final CategoriaService categoriaService;
    private final CalidadService calidadService;
    private final MarcasService marcasService;
    private final PreciosService preciosService;

    @Override
    public ResponseEntity<Filtros> obtenerPorProducto(final Long producto) {
        Filtros filtros = new Filtros();
        filtros.setCategorias(categoriaService.obtenerPorProducto(producto));
        filtros.setCalidad(calidadService.obtenerPorProducto(producto));
        filtros.setMarcas(marcasService.obtenerPorProducto(producto));
        filtros.setPrecios(preciosService.obtenerTodos());

        if (filtros.getCategorias().isEmpty() ||
                filtros.getCalidad().isEmpty() ||
                filtros.getMarcas().isEmpty() ||
                filtros.getPrecios().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(filtros);
    }

    @Override
    public ResponseEntity<Categorias> obtenerSubCategorias() {
        Categorias categorias = new Categorias();

        categorias.setCamisetas(categoriaService.obtenerPorProducto(1L));
        categorias.setGorras(categoriaService.obtenerPorProducto(2L));
        categorias.setJeans(categoriaService.obtenerPorProducto(3L));
        categorias.setSudaderas(categoriaService.obtenerPorProducto(4L));
        categorias.setTenis(categoriaService.obtenerPorProducto(5L));
        categorias.setAccesorios(categoriaService.obtenerPorProducto(6L));
        categorias.setBuzos(categoriaService.obtenerPorProducto(7L));

        return ResponseEntity.ok(categorias);
    }
}
