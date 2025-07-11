package com.co.quality.clothing.entity;

import com.co.quality.clothing.dtos.ProductosPedidos;
import com.co.quality.clothing.dtos.Unidades;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha;
    private Long estado;
    private Long metodoPago;
    private BigDecimal precioTotal;
    private String direccionEntrega;
    private String ciudadEntrega;
    private String departamentoEntrega;
    private String observacionEntrega;
    private String email;
    private String celular;

    @ElementCollection
    private List<ProductosPedidos> productos;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuarios usuario;

}
