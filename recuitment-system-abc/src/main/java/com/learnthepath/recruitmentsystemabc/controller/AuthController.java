package com.learnthepath.recruitmentsystemabc.controller;

import com.learnthepath.recruitmentsystemabc.dto.UserDto;
import com.learnthepath.recruitmentsystemabc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping(value = {"/", "/index"})
    public String showHomePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:" + userService.determineRedirectUrl(auth);
        }
        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:" + userService.determineRedirectUrl(auth);
        }
        model.addAttribute("user", new UserDto());
        return "login";
    }

    @GetMapping("/sign-up")
    public String showSignUpPage(Model model, @RequestParam(required = false) String username) {
        model.addAttribute("user", new UserDto());
        return "sign-up";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/sign-up/create-account")
    public String handleSubmitSignUpForm(@Valid @ModelAttribute("user") UserDto userDto,
                                         BindingResult result) {

        if (userService.checkUserExist(userDto.getUsername())) {
            result.rejectValue("username", " ",
                    "Username is already in use");
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", " ",
                    "The password does not match");
        }

        if (result.hasErrors()) {
            return "sign-up";
        }

        userService.saveUser(userDto);
        return "redirect:/login";
    }

}
