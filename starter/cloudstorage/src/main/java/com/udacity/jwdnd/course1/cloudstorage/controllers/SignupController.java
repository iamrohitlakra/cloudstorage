package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String signupView(Model model) {
        return "signup";
    }

    @PostMapping
    public String doSignUp(@ModelAttribute User user, Model model) {
        String signupError = null;

        if (!userService.isUserNameAvailable(user.getUsername())) {
            signupError = String.format("The username %s is not available" + user.getUsername());
        }

        if (signupError == null) {
            int rowAdded = userService.createUser(user);
            if (rowAdded < 0) {
                signupError = "There was an error while signing you up. Please try again";
            }
        }

        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }

}
