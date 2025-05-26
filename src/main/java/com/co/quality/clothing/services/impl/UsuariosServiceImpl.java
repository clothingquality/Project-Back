package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.Repository.UsuarioRepository;
import com.co.quality.clothing.dtos.InicioSesion;
import com.co.quality.clothing.entity.Usuarios;
import com.co.quality.clothing.services.UsuariosService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuariosServiceImpl implements UsuariosService {

    private final UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Usuarios> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public Usuarios obtenerPorEmail(final String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Usuarios crear(final Usuarios usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repository.save(usuario);
    }

    @Override
    public ResponseEntity<Usuarios> actualizar(final Long id, final Usuarios datos) {
        return repository.findById(id)
            .map(usuario -> {
                usuario.setNombre(datos.getNombre());
                usuario.setApellido(datos.getApellido());
                usuario.setCiudad(datos.getCiudad());
                usuario.setCelular(datos.getCelular());
                usuario.setDepartamento(datos.getDepartamento());
                usuario.setDireccion(datos.getDireccion());
                return ResponseEntity.ok(repository.save(usuario));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> eliminar(final Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> login(InicioSesion request) {
        Usuarios usuario = repository.findByEmail(request.getEmail());

        if (usuario.getEmail().equals(request.getEmail())
            && passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
                return ResponseEntity.ok(usuario);
            }

        return ResponseEntity.status(401).body("Credenciales inválidas o usuario no existe");
    }
}
