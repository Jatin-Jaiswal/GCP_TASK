package com.example.gcptask.repository;

import com.example.gcptask.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
    List<UserData> findTop3ByOrderByCreatedAtDesc();
}