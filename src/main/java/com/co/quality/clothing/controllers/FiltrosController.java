package com.co.quality.clothing.controllers;

import com.co.quality.clothing.dtos.Categorias;
import com.co.quality.clothing.dtos.Filtros;
import com.co.quality.clothing.services.FiltrosService;
import com.co.quality.clothing.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("qualityclothing/api/filtros")
@CrossOrigin(origins = { Constants.ADMIN_PAGE, Constants.USERS_PAGE})
public class FiltrosController {

    private final FiltrosService filtrosService;

    @GetMapping("/obtener/por/producto/{producto}")
    public ResponseEntity<Filtros> obtenerPorProducto(@PathVariable Long producto) {
        return filtrosService.obtenerPorProducto(producto);
    }

    @GetMapping("/obtener/sub-categorias")
    public ResponseEntity<Categorias> obtenerSubCategorias() {
        return filtrosService.obtenerSubCategorias();
    }
}
