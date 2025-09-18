package com.example.gcptask.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload.directory}")
    private String uploadDir;

    public String storeFile(MultipartFile file) throws IOException {
        return storeFile(file.getBytes(), file.getOriginalFilename());
    }

    public String storeFile(byte[] fileContent, String originalFilename) throws IOException {
        // Create the upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique filename
        String filename = UUID.randomUUID().toString() + "_" + originalFilename;
        
        // Save the file
        Path filePath = uploadPath.resolve(filename);
        Files.write(filePath, fileContent);

        return filename;
    }
}