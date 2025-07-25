package com.co.quality.clothing.controllers;

import com.co.quality.clothing.entity.Colores;
import com.co.quality.clothing.services.ColoresService;
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
@RequestMapping("qualityclothing/api/colores")
public class ColoresController {

    private final ColoresService service;

    @GetMapping("/obtener/todos")
    public List<Colores> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/obtener/por/id/{id}")
    public ResponseEntity<Colores> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public Colores crear(@RequestBody Colores colores) {
        return service.crear(colores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Colores> actualizar(@PathVariable Long id, @RequestBody Colores datos) {
        return service.actualizar(id,datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }
}
