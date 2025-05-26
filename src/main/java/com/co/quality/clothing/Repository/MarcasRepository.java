package com.co.quality.clothing.Repository;

import com.co.quality.clothing.entity.Marcas;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcasRepository extends JpaRepository<Marcas, Long> {
    List<Marcas> findByProductoId(Long productoId);
}
