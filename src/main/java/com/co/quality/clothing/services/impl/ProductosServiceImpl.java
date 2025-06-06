package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.Repository.ProductosRepository;
import com.co.quality.clothing.entity.FileMetadata;
import com.co.quality.clothing.entity.Productos;
import com.co.quality.clothing.services.FileMetadataService;
import com.co.quality.clothing.services.ProductosService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductosServiceImpl implements ProductosService {
    private final ProductosRepository repository;

    private final FileMetadataService fileMetadataService;

    @Override
    public List<Productos> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public Page<Productos> obtenerPorProducto(final Long producto, Pageable pageable, Long categoriaId,
                                              Long marcaId, Long calidadId, Long descuento, Long precioInicio,
                                              Long precioFin, Long nuevo) {

        Specification<Productos> spec = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("producto").get("id"), producto)
        );

        if (categoriaId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("categoria").get("id"), categoriaId));
        }

        if (marcaId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("marca").get("id"), marcaId));
        }

        if (calidadId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("calidad").get("id"), calidadId));
        }

        if (descuento != null) {
            if (descuento == 0) {
                spec = spec.and((root, query, cb) ->
                        cb.greaterThan(root.get("descuento"), 0));
            } else if (descuento == 1) {
                spec = spec.and((root, query, cb) ->
                        cb.equal(root.get("descuento"), 0));
            }
        }

        if (nuevo != null) {
            if (nuevo == 0) {
                spec = spec.and((root, query, cb) ->
                        cb.equal(root.get("nuevo"), 0));
            } else if (nuevo == 1) {
                spec = spec.and((root, query, cb) ->
                        cb.equal(root.get("nuevo"), 1));
            }
        }

        if (precioInicio != null && precioFin != null) {
            spec = spec.and((root, query, cb) ->
                    cb.between(root.get("precio"), precioInicio, precioFin));
        }

        return repository.findAll(spec, pageable);
    }

    @Override
    public ResponseEntity<Productos> obtenerPorId(final Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Productos crear(final Productos producto) {
        return repository.save(producto);
    }

    @Override
    public ResponseEntity<Productos> actualizar(final Long id, final Productos datos) {
        return repository.findById(id)
            .map(productos -> {
                productos.setNombre(datos.getNombre());
                productos.setDescripcion(datos.getDescripcion());
                productos.setDescuento(datos.getDescuento());
                productos.setNuevo(datos.getNuevo());
                productos.setPrecio(datos.getPrecio());
                productos.setUnidades(datos.getUnidades());
                productos.setCalidad(datos.getCalidad());
                productos.setCategoria(datos.getCategoria());
                productos.setMarca(datos.getMarca());
                productos.setTallas(datos.getTallas());
                return ResponseEntity.ok(repository.save(productos));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<Void> eliminar(final Long id) {
        if (repository.existsById(id)) {
            Productos producto = repository.findById(id).get();

            repository.deleteById(id);

            if(producto.getImagenes() != null) {
                for (FileMetadata imagen : producto.getImagenes()) {
                    fileMetadataService.deleteArchivo(imagen.getId());
                }
            }

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
