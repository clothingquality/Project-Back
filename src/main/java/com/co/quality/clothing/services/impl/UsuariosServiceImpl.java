package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.Repository.PasswordResetTokenRepository;
import com.co.quality.clothing.Repository.UsuarioRepository;
import com.co.quality.clothing.dtos.InicioSesion;
import com.co.quality.clothing.entity.PasswordResetToken;
import com.co.quality.clothing.entity.Usuarios;
import com.co.quality.clothing.exceptions.UsuarioNoEncontradoException;
import com.co.quality.clothing.services.UsuariosService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuariosServiceImpl implements UsuariosService {

    private final UsuarioRepository repository;
    private final PasswordResetTokenRepository tokenRepository;

    @Value("${spring.mail.username}")
    private String email;

    @Autowired
    private JavaMailSender mailSender;

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

    public String createResetToken(String email) {

        Usuarios usuario = repository.findByEmail(email);
        if (usuario == null) {
            throw new UsuarioNoEncontradoException("Usuario con email " + email + " no existe");
        }

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setEmail(email);
        resetToken.setExpirationDate(LocalDateTime.now().plusMinutes(10));

        tokenRepository.save(resetToken);

        String body = "Link cambio contraseña: https://qualityclothingcol.com/changepass.html?token=" + token;
        String affair = "Recuperar contraseña";

        enviarCorreo(body, affair, email);

        return token;
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token Invalido"));

        if (resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El token expiro");
        }

        Usuarios usuario = repository.findByEmail(resetToken.getEmail());
        if (usuario == null) {
            throw new UsuarioNoEncontradoException("Usuario con email " + resetToken.getEmail() + " no existe");
        }

        usuario.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(newPassword));
        repository.save(usuario);

        tokenRepository.delete(resetToken);
    }

    public void enviarCorreo(String cuerpo, String asunto, String emailTo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(emailTo);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);
        mensaje.setFrom(email);

        mailSender.send(mensaje);
    }
}
