package com.co.quality.clothing.controllers;

import com.co.quality.clothing.entity.Productos;
import com.co.quality.clothing.services.ProductosService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("qualityclothing/api/productos")
public class ProductosController {
    private final ProductosService service;

    @GetMapping("/obtener/todos")
    public List<Productos> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/obtener/por/producto/{producto}")
    public Page<Productos> obtenerPorProducto(@PathVariable Long producto,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) Long categoriaId,
        @RequestParam(required = false) Long marcaId,
        @RequestParam(required = false) Long calidadId,
        @RequestParam(required = false) Long descuento,
        @RequestParam(required = false) Long precioInicio,
        @RequestParam(required = false) Long precioFin,
        @RequestParam(required = false) Long nuevo,
        @RequestParam(required = false) Long productoId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return service.obtenerPorProducto(producto, pageable, categoriaId, marcaId, calidadId, descuento,
                precioInicio, precioFin, nuevo, productoId);
    }

    @GetMapping("/obtener/por/id/{id}")
    public ResponseEntity<Productos> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public Productos crear(@RequestBody Productos producto) {
        return service.crear(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Productos> actualizar(@PathVariable Long id, @RequestBody Productos datos) {
        return service.actualizar(id,datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }
}
