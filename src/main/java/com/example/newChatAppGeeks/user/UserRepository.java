package com.example.newChatAppGeeks.user;

import com.example.newChatAppGeeks.user.Status;
import com.example.newChatAppGeeks.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends MongoRepository<User, String> {
    List<User> findAllByStatus(Status status);
    Optional<User> findByNickName(String nickName);

    Optional<User> findByNickNameAndPassword(String nickName,String password);
}
