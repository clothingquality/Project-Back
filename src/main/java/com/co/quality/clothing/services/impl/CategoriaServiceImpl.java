package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.Repository.CategoriaRepository;
import com.co.quality.clothing.entity.Categoria;
import com.co.quality.clothing.services.CategoriaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repository;

    @Override
    public List<Categoria> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public List<Categoria> obtenerPorProducto(final Long producto) {
        return repository.findByProductoId(producto, Sort.by(Sort.Direction.ASC, "orden"));
    }

    @Override
    public ResponseEntity<Categoria> obtenerPorId(final Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Categoria crear(final Categoria categoria) {
        return repository.save(categoria);
    }

    @Override
    public ResponseEntity<Categoria> actualizar(final Long id, final Categoria datos) {
        return repository.findById(id)
            .map(categoria -> {
                categoria.setNombre(datos.getNombre());
                categoria.setOrden(datos.getOrden());
                return ResponseEntity.ok(repository.save(categoria));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> eliminar(final Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
