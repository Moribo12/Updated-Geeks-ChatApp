package com.example.newChatAppGeeks.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
     User registerUser(String nickName, String password, String email);
     User authenticate(String nickName, String password);
     void disconnect(User user);
     ResponseEntity<List<User>> findConnectedUsers();
}
