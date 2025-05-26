package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.Repository.MarcasRepository;
import com.co.quality.clothing.entity.Marcas;
import com.co.quality.clothing.services.MarcasService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarcasServiceImpl implements MarcasService {

    private final MarcasRepository repository;

    @Override
    public List<Marcas> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public List<Marcas> obtenerPorProducto(final Long producto) {
        return repository.findByProductoId(producto);
    }

    @Override
    public ResponseEntity<Marcas> obtenerPorId(final Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Marcas crear(final Marcas marcas) {
        return repository.save(marcas);
    }

    @Override
    public ResponseEntity<Marcas> actualizar(final Long id, final Marcas datos) {
        return repository.findById(id)
            .map(marca -> {
                marca.setNombre(datos.getNombre());
                return ResponseEntity.ok(repository.save(marca));
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
