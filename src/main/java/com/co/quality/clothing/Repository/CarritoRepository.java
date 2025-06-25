package com.co.quality.clothing.Repository;

import com.co.quality.clothing.entity.Carrito;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    List<Carrito> findByUsuarioId(Long usuarioId);

    List<Carrito> findByCreatedAtBefore(LocalDateTime createdAt);
}
