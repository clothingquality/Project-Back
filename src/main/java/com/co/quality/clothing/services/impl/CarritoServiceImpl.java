package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.Repository.CarritoRepository;
import com.co.quality.clothing.Repository.ProductosRepository;
import com.co.quality.clothing.dtos.Unidades;
import com.co.quality.clothing.entity.Carrito;
import com.co.quality.clothing.entity.Productos;
import com.co.quality.clothing.services.CarritoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository repository;

    private final ProductosRepository productosRepository;

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
        Optional<Productos> producto = productosRepository.findById(carrito.getIdProducto());

        if (producto.isPresent()) {
            Productos p = producto.get();
            List<Unidades> unidades = p.getUnidades();

            for (Unidades unidad : unidades) {
                if (Objects.equals(unidad.getCodColor(), carrito.getColor())
                        && Objects.equals(unidad.getNombreTalla(), carrito.getTalla())) {
                    unidad.setCantidad(unidad.getCantidad() - carrito.getCantidad());
                    break;
                }
            }

            p.setUnidades(unidades);
            productosRepository.save(p);
        }

        List<Carrito> carritoUsuarios = repository.findByUsuarioId(carrito.getUsuario().getId());

        for (Carrito carritoUpdate : carritoUsuarios) {
            carritoUpdate.setCreatedAt(carrito.getCreatedAt());
            repository.save(carritoUpdate);
        }

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

            Optional<Carrito> carrito = repository.findById(id);

            if (carrito.isPresent()) {
                Carrito car = carrito.get();

                Optional<Productos> producto = productosRepository.findById(car.getIdProducto());

                if (producto.isPresent()) {
                    Productos p = producto.get();
                    List<Unidades> unidades = p.getUnidades();

                    for (Unidades unidad : unidades) {
                        if (Objects.equals(unidad.getCodColor(), car.getColor())
                                && Objects.equals(unidad.getNombreTalla(), car.getTalla())) {
                            unidad.setCantidad(unidad.getCantidad() + car.getCantidad());
                            break;
                        }
                    }

                    p.setUnidades(unidades);
                    productosRepository.save(p);
                }
            }

            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @Scheduled(fixedRate = 60000) // cada 60 segundos
    public void borrarRegistrosVencidos() {
        LocalDateTime hace30Min = LocalDateTime.now().minusMinutes(1);
        List<Carrito> vencidos = repository.findByCreatedAtBefore(hace30Min);

        if (!vencidos.isEmpty()) {
            for (Carrito carrito : vencidos) {
                Optional<Productos> producto = productosRepository.findById(carrito.getIdProducto());

                if (producto.isPresent()) {
                    Productos p = producto.get();
                    List<Unidades> unidades = p.getUnidades();

                    for (Unidades unidad : unidades) {
                        if (Objects.equals(unidad.getCodColor(), carrito.getColor())
                                && Objects.equals(unidad.getNombreTalla(), carrito.getTalla())) {
                            unidad.setCantidad(unidad.getCantidad() + carrito.getCantidad());
                            break;
                        }
                    }

                    p.setUnidades(unidades);
                    productosRepository.save(p);
                }
                repository.deleteById(carrito.getId());
            }
        }
    }
}
