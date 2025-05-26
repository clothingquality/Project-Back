package com.co.quality.clothing.Repository;

import com.co.quality.clothing.entity.Calidad;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalidadRepository extends JpaRepository<Calidad, Long> {
    List<Calidad> findByProductoId(Long productoId, final Sort orden);
}
