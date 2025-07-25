package com.co.quality.clothing.controllers;

import com.co.quality.clothing.entity.Precios;
import com.co.quality.clothing.services.PreciosService;
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
@RequestMapping("qualityclothing/api/precios")
public class PreciosController {

    private final PreciosService service;

    @GetMapping("/obtener/todos")
    public List<Precios> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/obtener/por/id/{id}")
    public ResponseEntity<Precios> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public Precios crear(@RequestBody Precios precios) {
        return service.crear(precios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Precios> actualizar(@PathVariable Long id, @RequestBody Precios datos) {
        return service.actualizar(id,datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }
}
