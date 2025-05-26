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
    public Page<Productos> obtenerPorProducto(final Long producto, Pageable pageable) {
        return repository.findByProductoId(producto, pageable);
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

            for (FileMetadata imagen : producto.getImagenes()) {
                fileMetadataService.deleteArchivo(imagen.getId());
            }
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
