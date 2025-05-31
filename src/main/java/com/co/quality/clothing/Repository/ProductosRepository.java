package com.co.quality.clothing.Repository;

import com.co.quality.clothing.entity.Productos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductosRepository extends JpaRepository<Productos, Long>, JpaSpecificationExecutor<Productos> {
}
