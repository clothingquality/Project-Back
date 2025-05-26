package com.co.quality.clothing.Repository;

import com.co.quality.clothing.entity.Categoria;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByProductoId(Long productoId, final Sort orden);
}
