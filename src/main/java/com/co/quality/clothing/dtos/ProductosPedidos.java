package com.co.quality.clothing.dtos;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ProductosPedidos {
    private Long cantidad;
    private String color;
    private String nombre;
    private Long descuento;
    private Long idProducto;
    private BigDecimal precio;
    private String imagen;
    private String talla;
}
