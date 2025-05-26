package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.Repository.CalidadRepository;
import com.co.quality.clothing.entity.Calidad;
import com.co.quality.clothing.services.CalidadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalidadServiceImpl implements CalidadService {

    private final CalidadRepository repository;

    @Override public List<Calidad> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public List<Calidad> obtenerPorProducto(final Long producto) {
        return repository.findByProductoId(producto, Sort.by(Sort.Direction.ASC, "orden"));
    }

    @Override
    public ResponseEntity<Calidad> obtenerPorId(final Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Calidad crear(final Calidad calidad) {
        return repository.save(calidad);
    }

    @Override
    public ResponseEntity<Calidad> actualizar(final Long id, final Calidad datos) {
        return repository.findById(id)
            .map(calidad -> {
                calidad.setNombre(datos.getNombre());
                calidad.setOrden(datos.getOrden());
                return ResponseEntity.ok(repository.save(calidad));
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
