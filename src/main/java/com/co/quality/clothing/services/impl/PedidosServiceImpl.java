package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.Repository.PedidosRepository;
import com.co.quality.clothing.dtos.ProductosPedidos;
import com.co.quality.clothing.entity.Pedidos;
import com.co.quality.clothing.services.PedidosService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidosServiceImpl implements PedidosService {

    private final PedidosRepository repository;

    @Override
    public List<Pedidos> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public Page<Pedidos> obtenerPorFiltro(Pageable pageable, Date fechaDesde, Date fechaHasta, Long estado,
                                          Long metodoPago) {

        Specification<Pedidos> spec = Specification.where(null);

        if (fechaDesde != null && fechaHasta != null) {
            spec = spec.and((root, query, cb) ->
                    cb.between(root.get("fecha"), fechaDesde, fechaHasta));
        } else if (fechaDesde != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("fecha"), fechaDesde));
        } else if (fechaHasta != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("fecha"), fechaHasta));
        }

        if (estado != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("estado"), estado));
        }

        if (metodoPago != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("metodoPago"), metodoPago));
        }

        return repository.findAll(spec, pageable);
    }

    @Override
    public ResponseEntity<Pedidos> obtenerPorId(Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Pedidos crear(Pedidos pedido) {
        BigDecimal total = BigDecimal.ZERO;

        for (ProductosPedidos producto : pedido.getProductos()) {
            BigDecimal descuentoDecimal = BigDecimal.valueOf(producto.getDescuento())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            // precio * (1 - descuento)
            BigDecimal precioConDescuento = producto.getPrecio()
                    .multiply(BigDecimal.ONE.subtract(descuentoDecimal));

            // Multiplicamos por la cantidad
            BigDecimal subtotal = precioConDescuento
                    .multiply(BigDecimal.valueOf(producto.getCantidad()));

            total = total.add(subtotal);
        }

        pedido.setPrecioTotal(total);
        return repository.save(pedido);
    }

    @Override
    public ResponseEntity<Pedidos> actualizar(Long id, Pedidos datos) {
        return repository.findById(id)
                .map(pedido -> {
                    pedido.setEstado(datos.getEstado());
                    return ResponseEntity.ok(repository.save(pedido));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
