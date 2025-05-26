package com.co.quality.clothing.Repository;

import com.co.quality.clothing.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
    Usuarios findByEmail(String email);
}
