package com.example.gcptask.controller;

import com.example.gcptask.model.UserData;
import com.example.gcptask.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data")
@CrossOrigin(origins = "http://localhost:3000")
public class UserDataController {

    @Autowired
    private UserDataService userDataService;

    @PostMapping
    public ResponseEntity<UserData> createData(@RequestBody UserData userData) {
        UserData savedData = userDataService.saveData(userData);
        return ResponseEntity.ok(savedData);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<UserData>> getRecentData() {
        return ResponseEntity.ok(userDataService.getRecentData());
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserData>> getAllData() {
        return ResponseEntity.ok(userDataService.getAllData());
    }
}