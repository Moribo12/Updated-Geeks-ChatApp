package com.example.newChatAppGeeks.user;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    @Autowired
     UserRepository repository;

//    public void saveUser(User user) {
//        user.setStatus(Status.ONLINE);
//        repository.save(user);
//    }

    public User registerUser(String nickName, String password, String email){
        if(nickName ==null || password ==null){
            return null;
        }
        else{
            if(repository.findByNickName(nickName).isPresent()){
                System.out.println("User already Exists!");
                return null;
            }
            User user = new User();
            user.setNickName(nickName);
            user.setPassword(password);
            user.setStatus(Status.ONLINE);
            user.setEmail(email);
            return repository.save(user);
        }
    }

    public User authenticate(String nickName, String password){
        return repository.findByNickNameAndPassword(nickName,password).orElse(null);
    }


    public void disconnect(User user) {
        var storedUser = repository.findById(user.getNickName()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            repository.save(storedUser);
        }
    }

    public ResponseEntity<List<User>> findConnectedUsers() {
        try {
            return new ResponseEntity<>(repository.findAllByStatus(Status.ONLINE),HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }


}
