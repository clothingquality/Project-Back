package com.co.quality.clothing.entity;

import com.co.quality.clothing.dtos.Unidades;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Long descuento;
    private Long nuevo;
    private BigDecimal precio;

    @ElementCollection
    private List<Unidades> unidades;

    @ManyToOne
    @JoinColumn(name = "calidad")
    private Calidad calidad;

    @ManyToOne
    @JoinColumn(name = "categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "marca")
    private Marcas marca;

    @ManyToOne
    @JoinColumn(name = "tallas_productos")
    private List<Tallas> tallas;

    @ManyToOne
    @JoinColumn(name = "producto")
    private CodProductos producto;

    @OneToMany
    @JoinColumn(name = "imagenes")
    private List<FileMetadata> imagenes;
}
