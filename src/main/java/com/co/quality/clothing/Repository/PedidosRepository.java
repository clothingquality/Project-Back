package com.co.quality.clothing.Repository;

import com.co.quality.clothing.entity.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PedidosRepository extends JpaRepository<Pedidos, Long>, JpaSpecificationExecutor<Pedidos> {
}