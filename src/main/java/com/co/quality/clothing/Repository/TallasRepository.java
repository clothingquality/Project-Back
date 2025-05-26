package com.co.quality.clothing.Repository;

import com.co.quality.clothing.entity.Tallas;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TallasRepository extends JpaRepository<Tallas, Long> {
    List<Tallas> findByProductoId(Long productoId, final Sort orden);
}