package com.co.quality.clothing.services;

import com.co.quality.clothing.entity.Pedidos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface PedidosService {
    List<Pedidos> obtenerTodos();

    Page<Pedidos> obtenerPorFiltro(Pageable pageable, Date fechaDesde, Date fechaHasta,
                                   Long estado, Long metodoPago);

    ResponseEntity<Pedidos> obtenerPorId(Long id);

    Pedidos crear(Pedidos pedido);

    ResponseEntity<Pedidos> actualizar(Long id, Pedidos datos);

    ResponseEntity<Void> eliminar(Long id);
}
