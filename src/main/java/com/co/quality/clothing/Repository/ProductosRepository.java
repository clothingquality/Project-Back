package com.co.quality.clothing.Repository;

import com.co.quality.clothing.entity.Productos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductosRepository extends JpaRepository<Productos, Long> {
    Page<Productos> findByProductoId(Long productoId, Pageable pageable);
}
