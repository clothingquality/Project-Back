package com.co.quality.clothing.services;

import com.co.quality.clothing.dtos.InicioSesion;
import com.co.quality.clothing.entity.Usuarios;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface UsuariosService {
    List<Usuarios> obtenerTodos();

    Usuarios obtenerPorEmail(String email);

    Usuarios crear(Usuarios usuario);

    ResponseEntity<Usuarios> actualizar(Long id, Usuarios datos);

    ResponseEntity<Void> eliminar(Long id);

    ResponseEntity<?> login(InicioSesion request);
}
