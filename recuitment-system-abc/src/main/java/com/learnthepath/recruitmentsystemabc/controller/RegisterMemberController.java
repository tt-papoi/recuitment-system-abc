package com.learnthepath.recruitmentsystemabc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterMemberController {
    @GetMapping("/register-member/home")
    public String showMemberRegistrationPage() {
        return "register-member";
    }

    @GetMapping("/register-member/enterprise")
    public String showEnterpriseRegistrationPage() {
        return "register-enterprise";
    }

    @GetMapping("/register-member/candidate")
    public String showCandidateRegistrationPage() {
        return "register-candidate";
    }
}
