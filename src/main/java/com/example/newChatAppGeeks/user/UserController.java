package com.example.newChatAppGeeks.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

//    @MessageMapping("/user.addUser")
//    @SendTo("/user/public")
//    public User addUser(
//            @Payload User user
//    ) {
//        userService.saveUser(user);
//        return user;
//    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest",new User());
        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest",new User());
        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user){
        System.out.println("register request" +user);
        User registeredUser = userService.registerUser(user.getNickName(),user.getPassword(),user.getEmail());
        if(registeredUser != null){
            return "login_page";
        }else{
            return "error_page";
        }
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model){
        System.out.println("login request");
        User authenticated= userService.authenticate(user.getNickName(),user.getPassword());
        if(authenticated != null){
            model.addAttribute("userUsername",authenticated.getNickName());
            return "index";
        }else{
            return "error_page";
        }
    }


    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public User disconnectUser(
            @Payload User user
    ) {
        userService.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers() {
        return userService.findConnectedUsers();
    }
}
