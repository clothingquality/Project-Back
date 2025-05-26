package com.co.quality.clothing.dtos;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Unidades {
    private Long cantidad;

    private Long idColor;
    private String codColor;
    private String nombreColor;

    private Long idTalla;
    private String nombreTalla;
}
