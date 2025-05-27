package com.co.quality.clothing.controllers;

import com.co.quality.clothing.entity.Marcas;
import com.co.quality.clothing.services.MarcasService;
import com.co.quality.clothing.utils.Constants;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("qualityclothing/api/marcas")
@CrossOrigin(origins = { Constants.ADMIN_PAGE, Constants.USERS_PAGE})
public class MarcasController {
    private final MarcasService service;

    @GetMapping("/obtener/todos")
    public List<Marcas> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/obtener/por/producto/{producto}")
    public List<Marcas> obtenerPorProducto(@PathVariable Long producto) {
        return service.obtenerPorProducto(producto);
    }

    @GetMapping("/obtener/por/id/{id}")
    public ResponseEntity<Marcas> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public Marcas crear(@RequestBody Marcas marcas) {
        return service.crear(marcas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marcas> actualizar(@PathVariable Long id, @RequestBody Marcas datos) {
        return service.actualizar(id,datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }
}
