package com.co.quality.clothing.services.impl;

import com.co.quality.clothing.Repository.FileMetadataRepository;
import com.co.quality.clothing.entity.FileMetadata;
import com.co.quality.clothing.services.FileMetadataService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.co.quality.clothing.utils.CompressImages;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class FileMetadataServiceImpl implements FileMetadataService {

    private final FileMetadataRepository fileRepository;

    private final S3Client s3Client;

    private final Region region;

    private final CompressImages compressImages;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Override
    public List<FileMetadata> uploadFile(List<MultipartFile> files) throws IOException {
        List<FileMetadata> archivosGuardados = new ArrayList<>();

        for (MultipartFile file : files) {

            String key = UUID.randomUUID() + "_" + file.getOriginalFilename();
            byte[] fileBytes;

            if (file.getContentType() != null) {
                fileBytes = compressImages.compressImage(file);
            } else {
                fileBytes = file.getBytes(); // no es imagen, dejar como está
            }

            try(InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
                PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

                s3Client.putObject(putRequest, RequestBody.fromInputStream(inputStream, fileBytes.length));

                String url = String.format("https://%s.s3.%s.amazonaws.com/%s",
                    bucketName, region.toString(), key);

                FileMetadata archivo = FileMetadata.builder()
                    .originalFileName(file.getOriginalFilename())
                    .url(url)
                    .build();

                fileRepository.save(archivo);
                archivosGuardados.add(archivo);

            } catch (IOException e) {
                throw new RuntimeException("Error al subir archivo: " + file.getOriginalFilename(), e);
            }
        }

        return archivosGuardados;
    }

    @Override
    public void deleteArchivo(Long id) {
        FileMetadata archivo = fileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Archivo no encontrado con ID: " + id));

        String bucketUrlPrefix = String.format("https://%s.s3.%s.amazonaws.com/",
            bucketName, region.toString());

        String key = archivo.getUrl().replace(bucketUrlPrefix, "");

        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build();

        s3Client.deleteObject(deleteRequest);

        fileRepository.deleteById(id);
    }

    @Override
    public FileMetadata updateArchivo(Long id, MultipartFile file) throws IOException {
        FileMetadata archivoExistente = fileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Archivo no encontrado con ID: " + id));

        // Extraer key del archivo anterior
        String bucketUrlPrefix = String.format("https://%s.s3.%s.amazonaws.com/",
            bucketName, region.toString());
        String oldKey = archivoExistente.getUrl().replace(bucketUrlPrefix, "");

        // Borrar archivo antiguo de S3
        s3Client.deleteObject(DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(oldKey)
            .build());

        // Subir nuevo archivo
        String newKey = UUID.randomUUID() + "_" + file.getOriginalFilename();
        byte[] fileBytes;

        if (file.getContentType() != null) {
            fileBytes = compressImages.compressImage(file);
        } else {
            fileBytes = file.getBytes(); // no es imagen, dejar como está
        }

        try(InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
            PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(newKey)
                .contentType(file.getContentType())
                .build();

            s3Client.putObject(putRequest, RequestBody.fromInputStream(inputStream, fileBytes.length));

        } catch (IOException e) {
            throw new RuntimeException("Error al actualizar archivo en S3", e);
        }

        // Actualizar entidad en base de datos
        String nuevaUrl = String.format("https://%s.s3.%s.amazonaws.com/%s",
            bucketName, region.toString(), newKey);

        archivoExistente.setOriginalFileName(file.getOriginalFilename());
        archivoExistente.setUrl(nuevaUrl);

        return fileRepository.save(archivoExistente);
    }
}
