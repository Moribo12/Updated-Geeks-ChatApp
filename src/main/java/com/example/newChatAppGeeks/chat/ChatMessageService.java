package com.example.newChatAppGeeks.chat;

import org.springframework.stereotype.Service;

import java.util.List;
public interface ChatMessageService {
    ChatMessage saveMessage(ChatMessage chatMessage);
    List<ChatMessage> findChatMessages(String senderId, String recipientId);
}
