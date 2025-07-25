package com.co.quality.clothing.controllers;

import com.co.quality.clothing.entity.Calidad;
import com.co.quality.clothing.services.CalidadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("qualityclothing/api/calidad")
public class CalidadController {

    private final CalidadService service;

    @GetMapping("/obtener/todos")
    public List<Calidad> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/obtener/por/producto/{producto}")
    public List<Calidad> obtenerPorProducto(@PathVariable Long producto) {
        return service.obtenerPorProducto(producto);
    }

    @GetMapping("/obtener/por/id/{id}")
    public ResponseEntity<Calidad> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public Calidad crear(@RequestBody Calidad calidad) {
        return service.crear(calidad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Calidad> actualizar(@PathVariable Long id, @RequestBody Calidad datos) {
        return service.actualizar(id,datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }
}
