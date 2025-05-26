package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.Repository.PreciosRepository;
import com.co.quality.clothing.entity.Precios;
import com.co.quality.clothing.services.PreciosService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreciosServiceImpl implements PreciosService {

    private final PreciosRepository repository;

    @Override
    public List<Precios> obtenerTodos() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "precioInicio"));
    }

    @Override
    public ResponseEntity<Precios> obtenerPorId(final Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Precios crear(final Precios precios) {
        return repository.save(precios);
    }

    @Override
    public ResponseEntity<Precios> actualizar(final Long id, final Precios datos) {
        return repository.findById(id)
            .map(precio -> {
                precio.setPrecioInicio(datos.getPrecioInicio());
                precio.setPrecioFin(datos.getPrecioFin());
                return ResponseEntity.ok(repository.save(precio));
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
