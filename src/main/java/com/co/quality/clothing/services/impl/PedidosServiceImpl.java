package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.Repository.PedidosRepository;
import com.co.quality.clothing.dtos.ProductosPedidos;
import com.co.quality.clothing.entity.Pedidos;
import com.co.quality.clothing.services.PedidosService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class PedidosServiceImpl implements PedidosService {

    private static final Logger logger = LoggerFactory.getLogger(PedidosServiceImpl.class);

    private final PedidosRepository repository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String email;

    @Value("${spring.mail.password}")
    private String pass;

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
        Pedidos responsePedido = repository.save(pedido);

        String cuerpoVenta = "Se creo una orden de compra con el ID: " + responsePedido.getId()
                + " para el cliente con email: " + responsePedido.getUsuario().getEmail() + " con numero de wssp: "
                + responsePedido.getUsuario().getCelular() + " con entrega a: " + responsePedido.getDireccionEntrega()
                + " por un valor de: $" + responsePedido.getPrecioTotal();
        String asuntoVenta = "Nueva orden de compra creada con ID: " + responsePedido.getId();

        String cuerpoCliente = "Tu orden ha sido creada con exito, " +
                "Estaremos revisando el pedido y nos contactaremos contigo lo antes posible para confirmar todo, " +
                "Gracias por confiar en QualityClothing!!";
        String asuntoCliente = "Orden confirmada, QualityClothingCol";

        logger.info("Correo: {}", email);
        logger.info("Contrasena: {}", pass);

        enviarCorreo(cuerpoVenta, asuntoVenta, email);
        enviarCorreo(cuerpoCliente, asuntoCliente, responsePedido.getUsuario().getEmail());

        return responsePedido;
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

    public void enviarCorreo(String cuerpo, String asunto, String emailTo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(emailTo);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);
        mensaje.setFrom(email);

        mailSender.send(mensaje);
    }
}
