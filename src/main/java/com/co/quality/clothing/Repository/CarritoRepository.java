package com.co.quality.clothing.Repository;

import com.co.quality.clothing.entity.Carrito;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    List<Carrito> findByUsuarioId(Long usuarioId);

    @Modifying
    @Transactional
    void deleteByCreatedAtBefore(LocalDateTime limite);

    List<Carrito> findByCreatedAtBefore(LocalDateTime fecha);
}
