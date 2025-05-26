package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.Repository.ColoresRepository;
import com.co.quality.clothing.entity.Colores;
import com.co.quality.clothing.services.ColoresService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColoresServieImpl implements ColoresService {

    private final ColoresRepository repository;

    @Override
    public List<Colores> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public ResponseEntity<Colores> obtenerPorId(final Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Colores crear(final Colores colores) {
        return repository.save(colores);
    }

    @Override
    public ResponseEntity<Colores> actualizar(final Long id, final Colores datos) {
        return repository.findById(id)
            .map(colores -> {
                colores.setNombre(datos.getNombre());
                colores.setCodigo(datos.getCodigo());
                return ResponseEntity.ok(repository.save(colores));
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
