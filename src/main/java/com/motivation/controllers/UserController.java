package com.motivation.controllers;

import com.motivation.config.Errors;
import com.motivation.customExceptions.UsernameIsInUseException;
import com.motivation.models.bindingModels.RegistrationModel;
import com.motivation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(@ModelAttribute RegistrationModel registrationModel){
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute RegistrationModel registrationModel, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "register";
        }

        try {
            this.userService.register(registrationModel);
        } catch (UsernameIsInUseException ex) {
            bindingResult.rejectValue("username","error.user", "This username is already in use");
            return "register";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(required = false) String error, Model model){
        if(error != null){
            model.addAttribute("error", Errors.INVALID_CREDENTIALS);
        }

        return "login";
    }

    @GetMapping("/user")
    public String getUserPage(Principal principal){
        // principal.getName is returning username
        return "user";
    }
}
