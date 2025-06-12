package com.co.quality.clothing.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class CompressImages {

    public byte[] compressImage(MultipartFile file) throws IOException {
        try (InputStream input = file.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Thumbnails.of(input)
                    .scale(1.0) // no cambia el tamaño (usa .scale(0.8) si quieres reducir)
                    .outputQuality(0.75) // entre 0 (peor calidad) y 1 (mejor calidad)
                    .toOutputStream(outputStream);

            byte[] compressedBytes = outputStream.toByteArray();

            // Verificar si sigue siendo mayor a 1MB
            final int maxSizeBytes = 1 * 1024 * 1024;
            if (compressedBytes.length > maxSizeBytes) {
                // Intentar reducir aún más, recursivamente o ajustando parámetros
                return compressImageWithLowerQuality(file, 0.5);
            }

            return compressedBytes;
        }
    }

    private byte[] compressImageWithLowerQuality(MultipartFile file, double quality) throws IOException {
        try (InputStream input = file.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Thumbnails.of(input)
                    .scale(0.8)
                    .outputQuality(quality)
                    .toOutputStream(outputStream);

            return outputStream.toByteArray();
        }
    }
}
