package com.example.gcptask.service;

import com.example.gcptask.model.UserData;
import com.example.gcptask.model.UserDataMessage;
import com.example.gcptask.repository.UserDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.List;

@Service
public class MessageHandlerService {

    private final UserDataRepository repository;
    private final RedisCacheService cacheService;
    private final ObjectMapper objectMapper;
    private final FileStorageService fileStorageService;

    public MessageHandlerService(UserDataRepository repository, 
                               RedisCacheService cacheService,
                               ObjectMapper objectMapper,
                               FileStorageService fileStorageService) {
        this.repository = repository;
        this.cacheService = cacheService;
        this.objectMapper = objectMapper;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public void handleMessage(UserDataMessage message) throws IOException {
        
        // Validate the data
        validateMessage(message);
        
        // Store the file
        String fileName = fileStorageService.storeFile(message.getFileContent(), message.getFileName());
        
        // Create and save UserData
        UserData userData = new UserData();
        userData.setName(message.getName());
        userData.setEmail(message.getEmail());
        userData.setDescription(message.getDescription());
        userData.setFileName(fileName);
        
        repository.save(userData);
        
        // Update Redis cache with latest 3 entries
        List<UserData> recentData = repository.findTop3ByOrderByCreatedAtDesc();
        cacheService.cacheRecentData(recentData);
    }
    
    private void validateMessage(UserDataMessage message) {
        if (message.getName() == null || message.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (message.getEmail() == null || !message.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Valid email is required");
        }
        if (message.getFileContent() == null || message.getFileContent().length == 0) {
            throw new IllegalArgumentException("File content is required");
        }
    }
}