package com.example.gcptask.service;

import com.example.gcptask.model.UserDataMessage;
import org.springframework.stereotype.Service;

@Service
public class PubSubService {

    private final MessageHandlerService messageHandlerService;

    public PubSubService(MessageHandlerService messageHandlerService) {
        this.messageHandlerService = messageHandlerService;
    }

    public void publishMessage(UserDataMessage message) throws Exception {
        messageHandlerService.handleMessage(message);
    }
}