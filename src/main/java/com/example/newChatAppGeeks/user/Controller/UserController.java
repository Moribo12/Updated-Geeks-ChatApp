package com.example.newChatAppGeeks.user.Controller;

import com.example.newChatAppGeeks.user.Enum.Status;
import com.example.newChatAppGeeks.user.Model.User;
import com.example.newChatAppGeeks.user.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

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
        User registeredUser = this.userService.registerUser(user.getNickName(),user.getPassword(),user.getEmail());
        if(registeredUser != null){
            return "login_page";
        }else{
            return "error_page";
        }
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model){
        System.out.println("login request");
        User authenticated= this.userService.authenticate(user.getNickName(),user.getPassword());
        if(authenticated != null){
            model.addAttribute("userUsername",authenticated.getNickName());
            return "index";
        }else{
            return "error_page";
        }
    }


    @GetMapping("/logout/{nickName}")
        public String disconnectUser(@PathVariable String nickName){
        if(nickName != null ){
            this.userService.disconnect(nickName);
        }
//            messagingTemplate.convertAndSend("/topic/logout", user);
        return "redirect:/login";
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers() {
        return this.userService.findConnectedUsers();
    }
}
