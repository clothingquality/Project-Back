package com.co.quality.clothing.controllers;

import com.co.quality.clothing.entity.FileMetadata;
import com.co.quality.clothing.services.FileMetadataService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("qualityclothing/api/imagenes")
public class FileMetadataController {

    private final FileMetadataService service;

    @PostMapping("/upload")
    public ResponseEntity<List<FileMetadata>> uploadFile(@RequestParam("files") List<MultipartFile> files)
    throws IOException {
        List<FileMetadata> guardados = service.uploadFile(files);
        return ResponseEntity.ok(guardados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteArchivo(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FileMetadata> update(
        @PathVariable Long id,
        @RequestParam("file") MultipartFile file) throws IOException {
        FileMetadata fileUpdated = service.updateArchivo(id, file);
        return ResponseEntity.ok(fileUpdated);
    }
}
