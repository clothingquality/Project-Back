package com.co.quality.clothing.services;

import com.co.quality.clothing.entity.FileMetadata;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileMetadataService {

    List<FileMetadata> uploadFile(List<MultipartFile> file) throws IOException;

    void deleteArchivo(Long id);

    FileMetadata updateArchivo(Long id, MultipartFile file) throws IOException;
}
