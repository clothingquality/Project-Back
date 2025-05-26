package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.Repository.CarritoRepository;
import com.co.quality.clothing.entity.Carrito;
import com.co.quality.clothing.services.CarritoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository repository;

    @Override
    public List<Carrito> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public List<Carrito> obtenerPorUsuario(final Long usuario) {
        return repository.findByUsuarioId(usuario);
    }

    @Override
    public ResponseEntity<Carrito> obtenerPorId(final Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Carrito crear(final Carrito carrito) {
        return repository.save(carrito);
    }

    @Override
    public ResponseEntity<Carrito> actualizar(final Long id, final Carrito datos) {
        return repository.findById(id)
            .map(carrito -> {
                carrito.setNombre(datos.getNombre());
                carrito.setCantidad(datos.getCantidad());
                carrito.setColor(datos.getColor());
                carrito.setDescuento(datos.getDescuento());
                carrito.setIdProducto(datos.getIdProducto());
                carrito.setPrecio(datos.getPrecio());
                carrito.setImagen(datos.getImagen());
                carrito.setTalla(datos.getTalla());
                return ResponseEntity.ok(repository.save(carrito));
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
