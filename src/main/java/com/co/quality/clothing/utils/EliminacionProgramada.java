package com.co.quality.clothing.utils;

import com.co.quality.clothing.Repository.CarritoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EliminacionProgramada {

    private final CarritoRepository repository;

    public EliminacionProgramada(CarritoRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRate = 60000) // cada 60 segundos
    public void borrarRegistrosVencidos() {
        LocalDateTime hace30Min = LocalDateTime.now().minusMinutes(1);
        repository.deleteByCreatedAtBefore(hace30Min);
    }
}
