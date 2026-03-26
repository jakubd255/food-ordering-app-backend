package pl.jakubdudek.foodorderingappbackend.util;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileManager {
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadFile(MultipartFile file) {
        try {
            if(file.isEmpty()) return "File is empty";

            String originalFileName = file.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID()+"_"+originalFileName;

            Path destPath = Paths.get(uploadDir + uniqueFileName).toAbsolutePath().normalize();
            Files.createDirectories(destPath.getParent());
            file.transferTo(destPath);

            return uniqueFileName;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Resource downloadFile(String uniqueFileName) throws MalformedURLException {
        Path filePath = Paths.get(uploadDir + uniqueFileName).toAbsolutePath().normalize();
        Resource file = new UrlResource(filePath.toUri());

        if(file.exists() && file.isReadable()) {
            return file;
        }
        else {
            throw new EntityNotFoundException("File not found");
        }
    }

    public void deleteFile(String uniqueFileName) throws IOException {
        Path filePath = Paths.get(uploadDir + uniqueFileName).toAbsolutePath().normalize();
        Files.deleteIfExists(filePath);
    }
}
