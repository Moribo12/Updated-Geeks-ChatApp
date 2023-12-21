package com.example.newChatAppGeeks.user.Service;


import com.example.newChatAppGeeks.user.Model.User;
import com.example.newChatAppGeeks.user.Enum.Status;
import com.example.newChatAppGeeks.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    // add ResponseObject for a descriptive error handling
    @Autowired
    UserRepository userRepository;

    public User registerUser(String nickName, String password, String email){
        if(nickName ==null || password ==null){
            return null;
        }
        else{
            if(this.userRepository.findById(nickName).isPresent()){
                System.out.println("User already Exists!");
                return null;
            }
            User user = new User();
            user.setNickName(nickName);
            user.setPassword(password);
            user.setEmail(email);
            return this.userRepository.save(user);
        }
    }

    public User authenticate(String nickName, String password){
        User authenticatedUser = this.userRepository.findByNickNameAndPassword(nickName,password).orElse(null);
        if(authenticatedUser != null){
            authenticatedUser.setStatus(Status.ONLINE);
            this.userRepository.save(authenticatedUser);
        }
        return authenticatedUser;
    }


    public void disconnect(String nickName) {
        User storedUser = this.userRepository.findById(nickName).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            this.userRepository.save(storedUser);
        }
    }

    public ResponseEntity<List<User>> findConnectedUsers() {
        try {
            return new ResponseEntity<>(this.userRepository.findAllByStatus(Status.ONLINE),HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }


}
