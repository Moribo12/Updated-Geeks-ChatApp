package com.example.newChatAppGeeks.user.Service;

import com.example.newChatAppGeeks.user.Model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
     User registerUser(String nickName, String password, String email);
     User authenticate(String nickName, String password);
     void disconnect(User user);
     ResponseEntity<List<User>> findConnectedUsers();
}
