package com.co.quality.clothing.dtos;

import com.co.quality.clothing.entity.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categorias {
    private List<Categoria> camisetas;
    private List<Categoria> gorras;
    private List<Categoria> jeans;
    private List<Categoria> sudaderas;
    private List<Categoria> tenis;
    private List<Categoria> accesorios;
    private List<Categoria> buzos;
}
