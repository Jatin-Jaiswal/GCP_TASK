package com.example.gcptask.service;

import com.example.gcptask.model.UserData;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RedisCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String RECENT_DATA_KEY = "recent_data";
    private static final int MAX_RECENT_ENTRIES = 3;

    public RedisCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void cacheRecentData(List<UserData> recentData) {
        redisTemplate.opsForValue().set(RECENT_DATA_KEY, recentData.subList(0, Math.min(recentData.size(), MAX_RECENT_ENTRIES)));
    }

    @SuppressWarnings("unchecked")
    public List<UserData> getRecentData() {
        return (List<UserData>) redisTemplate.opsForValue().get(RECENT_DATA_KEY);
    }
}