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

    /* ****************************************************** Methods to render the HTML Page ********************************************************/
    @GetMapping("/register")
    public String getRegisterPage(Model model){
        //Models are used for communication between the controller and the view layer
        // Create a new User object and add it to the model attribute named "registerRequest"
        model.addAttribute("registerRequest",new User());

        // Return the name of the view/template/page to be rendered, in this case, "register_page"
        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest",new User());
        return "login_page";
    }


    /***************************************************** Method that communicate with the Service layer ****************************************** */
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
        // Call the userService to authenticate the user based on the provided nickname and password
        User authenticated= this.userService.authenticate(user.getNickName(),user.getPassword());

        // Check if authentication was successful
        if(authenticated != null){
            // Add the authenticated user's nickname to the model attribute named "userUsername"
            model.addAttribute("userUsername",authenticated.getNickName());
            return "index";
        }else{
            return "error_page";
        }
    }


    @GetMapping("/logout/{nickName}")
       //It's gonna logout the user with the nickname specified on the URL endpoint
        public String disconnectUser(@PathVariable String nickName){

        // Check if the provided nickname is not null
        if(nickName != null ){
            // Call the userService to disconnect the user with the given nickname
            this.userService.disconnect(nickName);
        }
        // Redirect to the "/login" endpoint after disconnecting the user
        return "redirect:/login";
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers() {
        return this.userService.findConnectedUsers();
    }
}
