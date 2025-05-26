package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.Repository.TallasRepository;
import com.co.quality.clothing.entity.Tallas;
import com.co.quality.clothing.services.TallasService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TallasServiceImpl implements TallasService {

    private final TallasRepository repository;

    @Override
    public List<Tallas> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public List<Tallas> obtenerPorProducto(final Long producto) {
        return repository.findByProductoId(producto, Sort.by(Sort.Direction.ASC, "orden"));
    }

    @Override
    public ResponseEntity<Tallas> obtenerPorId(final Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Tallas crear(final Tallas talla) {
        return repository.save(talla);
    }

    @Override
    public ResponseEntity<Tallas> actualizar(final Long id, final Tallas datos) {
        return repository.findById(id)
            .map(tallas -> {
                tallas.setNombre(datos.getNombre());
                tallas.setOrden(datos.getOrden());
                return ResponseEntity.ok(repository.save(tallas));
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
