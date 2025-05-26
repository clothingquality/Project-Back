package com.co.quality.clothing.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InicioSesion {
    private String email;
    private String password;
}
