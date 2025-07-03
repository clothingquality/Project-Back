package com.co.quality.clothing.controllers;

import com.co.quality.clothing.entity.Pedidos;
import com.co.quality.clothing.services.PedidosService;
import com.co.quality.clothing.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("qualityclothing/api/pedidos")
@CrossOrigin(origins = { Constants.ADMIN_PAGE, Constants.USERS_PAGE,
        Constants.ADMIN_PAGE_PROD, Constants.USERS_PAGE_PROD})
public class PedidosController {

    private final PedidosService service;

    @GetMapping("/obtener/todos")
    public List<Pedidos> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/obtener/por/filtro")
    public Page<Pedidos> obtenerPorProducto(
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(required = false)
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
                                              @RequestParam(required = false)
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta,
                                              @RequestParam(required = false) Long estado,
                                              @RequestParam(required = false) Long metodoPago) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return service.obtenerPorFiltro(pageable, fechaDesde, fechaHasta, estado, metodoPago);
    }

    @GetMapping("/obtener/por/id/{id}")
    public ResponseEntity<Pedidos> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public Pedidos crear(@RequestBody Pedidos pedido) {
        return service.crear(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedidos> actualizar(@PathVariable Long id, @RequestBody Pedidos datos) {
        return service.actualizar(id,datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }
}
