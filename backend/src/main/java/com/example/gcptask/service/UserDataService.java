package com.example.gcptask.service;

import com.example.gcptask.model.UserData;
import com.example.gcptask.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDataService {

    @Autowired
    private UserDataRepository userDataRepository;

    @CacheEvict(value = "recentData", allEntries = true)
    public UserData saveData(UserData userData) {
        return userDataRepository.save(userData);
    }

    @Cacheable("recentData")
    public List<UserData> getRecentData() {
        return userDataRepository.findAll(
            PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdAt"))
        ).getContent();
    }

    public List<UserData> getAllData() {
        return userDataRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}