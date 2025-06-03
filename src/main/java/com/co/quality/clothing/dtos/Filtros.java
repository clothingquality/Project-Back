package com.co.quality.clothing.dtos;

import com.co.quality.clothing.entity.Calidad;
import com.co.quality.clothing.entity.Categoria;
import com.co.quality.clothing.entity.Marcas;
import com.co.quality.clothing.entity.Precios;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filtros {
    private List<Categoria> categorias;
    private List<Calidad> calidad;
    private List<Marcas> marcas;
    private List<Precios> precios;
}