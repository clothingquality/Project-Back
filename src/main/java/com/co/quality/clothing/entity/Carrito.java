package com.co.quality.clothing.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cantidad;
    private String color;
    private String nombre;
    private String nombreColor;
    private Long descuento;
    private Long idProducto;
    private BigDecimal precio;
    private String imagen;
    private String talla;

    @Column(name = "created_at")
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("America/Bogota"));

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuarios usuario;
}
