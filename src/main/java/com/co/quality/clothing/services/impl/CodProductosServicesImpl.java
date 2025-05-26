package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.Repository.CodProductosRepository;
import com.co.quality.clothing.entity.CodProductos;
import com.co.quality.clothing.services.CodProductosService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodProductosServicesImpl implements CodProductosService {

    private final CodProductosRepository repository;

    @Override
    public List<CodProductos> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public ResponseEntity<CodProductos> obtenerPorId(final Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public CodProductos crear(final CodProductos codProducto) {
        return repository.save(codProducto);
    }

    @Override
    public ResponseEntity<CodProductos> actualizar(final Long id, final CodProductos datos) {
        return repository.findById(id)
            .map(codProductos -> {
                codProductos.setNombre(datos.getNombre());
                return ResponseEntity.ok(repository.save(codProductos));
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
