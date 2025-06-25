package com.co.quality.clothing.utils;

import com.co.quality.clothing.Repository.CarritoRepository;
import com.co.quality.clothing.entity.Carrito;
import com.co.quality.clothing.services.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EliminacionProgramada {

    private final CarritoRepository repository;
    private final CarritoService carritoService;

    @Scheduled(fixedRate = 60000) // cada 60 segundos
    public void borrarRegistrosVencidos() {
        LocalDateTime hace30Min = LocalDateTime.now().minusMinutes(1);

        List<Carrito> vencidos = repository.findByCreatedAtBefore(hace30Min);

        // 2. Procesarlos uno a uno
        for (Carrito entidad : vencidos) {
            carritoService.eliminar(entidad.getId());
        }
    }
}
