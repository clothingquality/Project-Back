package com.co.quality.clothing.controllers;

import com.co.quality.clothing.dtos.InicioSesion;
import com.co.quality.clothing.entity.Usuarios;
import com.co.quality.clothing.services.UsuariosService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("qualityclothing/api/usuarios")
public class UsuariosController {

    private final UsuariosService service;

    @GetMapping("/obtener/todos")
    public List<Usuarios> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/obtener/por/email/{email}")
    public Usuarios obtenerPorProducto(@PathVariable String email) {
        return service.obtenerPorEmail(email);
    }

    @PostMapping
    public Usuarios crear(@RequestBody Usuarios usuario) {
        return service.crear(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuarios> actualizar(@PathVariable Long id, @RequestBody Usuarios datos) {
        return service.actualizar(id,datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody InicioSesion request) {
        return service.login(request);
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {
        service.createResetToken(email);
        return "Un Link para cambiar la contraseña fue enviado a tu email.";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String newPassword) {
        service.resetPassword(token, newPassword);
        return "Contraseña cambiada con exito.";
    }
}
