package com.co.quality.clothing.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class CompressImages {

    public byte[] compressImage(MultipartFile file) throws IOException {
        final int maxSizeBytes = 1 * 1024 * 1024; // 1MB
        double quality = 0.9;
        double scale = 1.0;
        byte[] bestAttempt = null;

        // Redimensionar la imagen original a tamaño máximo permitido
        byte[] originalBytes = file.getBytes();
        BufferedImage resizedImage;

        try (ByteArrayInputStream bais = new ByteArrayInputStream(originalBytes)) {
            resizedImage = Thumbnails.of(bais)
                    .size(800, 800)
                    .asBufferedImage();
        }

        // Bajar peso de imagen
        while (quality >= 0.1) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                Thumbnails.of(resizedImage)
                        .scale(scale)
                        .outputQuality(quality)
                        .outputFormat("jpg")
                        .toOutputStream(outputStream);

                byte[] compressedBytes = outputStream.toByteArray();

                // Guardamos el intento actual (el más liviano logrado hasta ahora)
                if (bestAttempt == null || compressedBytes.length < bestAttempt.length) {
                    bestAttempt = compressedBytes;
                }

                if (compressedBytes.length <= maxSizeBytes) {
                    return compressedBytes;
                }

                // Reducir calidad y resolución
                quality -= 0.1;
                scale -= 0.05;
            }
        }
        return bestAttempt;
    }
}
