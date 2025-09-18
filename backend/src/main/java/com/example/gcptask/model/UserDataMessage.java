package com.example.gcptask.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserDataMessage implements Serializable {
    private String name;
    private String email;
    private String description;
    private String fileName;
    private byte[] fileContent;
}