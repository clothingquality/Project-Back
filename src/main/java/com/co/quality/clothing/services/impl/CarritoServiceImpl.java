package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.Repository.CarritoRepository;
import com.co.quality.clothing.Repository.ProductosRepository;
import com.co.quality.clothing.dtos.Unidades;
import com.co.quality.clothing.entity.Carrito;
import com.co.quality.clothing.entity.Productos;
import com.co.quality.clothing.services.CarritoService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository repository;

    private final ProductosRepository productosRepository;

    private static final Logger logger = LoggerFactory.getLogger(CarritoServiceImpl.class);

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

        logger.info("Iniciando busqueda en carrito: {}", carrito.getUsuario().getId());
        System.out.println("Iniciando busqueda en carrito: " + carrito.getUsuario().getId());

        if (carritoUsuarios != null && !carritoUsuarios.isEmpty()) {
            logger.info("El dato 1 del carrito es: {}", carritoUsuarios.get(0).getCreatedAt());
            System.out.println("El dato 1 del carrito es: " + carritoUsuarios.get(0).getCreatedAt());
            carrito.setCreatedAt(carritoUsuarios.get(0).getCreatedAt());
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
                carrito.setNombreColor(datos.getNombreColor());
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
    @Scheduled(fixedRate = 60000)
    public void borrarRegistrosVencidos() {
        ZonedDateTime hace15Min = ZonedDateTime.now().minusMinutes(30);
        List<Carrito> vencidos = repository.findByCreatedAtBefore(hace15Min);

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
