package com.learnthepath.recruitmentsystemabc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnterpriseController {

    @GetMapping("/enterprise/home")
    public String showEnterpriseHomePage() {
        return "home_enterprise";
    }
}
