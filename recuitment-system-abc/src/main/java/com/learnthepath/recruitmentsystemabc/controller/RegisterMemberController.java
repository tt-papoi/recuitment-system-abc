package com.learnthepath.recruitmentsystemabc.controller;

import com.learnthepath.recruitmentsystemabc.dto.CandidateDto;
import com.learnthepath.recruitmentsystemabc.dto.EnterpriseDto;
import com.learnthepath.recruitmentsystemabc.service.CandidateService;
import com.learnthepath.recruitmentsystemabc.service.EnterpriseService;
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
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterMemberController {
    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/register-member/home")
    public String showMemberRegistrationPage() {
        return "register-page/register-member";
    }

    @GetMapping("/register-member/enterprise")
    public String showEnterpriseRegistrationPage(Model model) {
        model.addAttribute("enterprise", new EnterpriseDto());
        return "register-page/register-enterprise";
    }

    @PostMapping("/register-member/enterprise/submit")
    public String handleEnterpriseRegistration(@Valid EnterpriseDto enterpriseDto, BindingResult result) {
        if (result.hasErrors()) {
            return "register-page/register-enterprise";
        }
        enterpriseService.saveEnterprise(enterpriseDto);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:" + userService.determineRedirectUrl(auth);
        }
        return "redirect:/";
    }

    @GetMapping("/register-member/candidate")
    public String showCandidateRegistrationPage(Model model) {
        model.addAttribute("candidate", new CandidateDto());
        return "register-page/register-candidate";
    }

    @PostMapping("/register-member/candidate/submit")
    public String handleEnterpriseRegistration(
            @Valid CandidateDto candidateDto,
            BindingResult result, Model model) {

//        if (result.hasErrors()) {
//            model.addAttribute("candidate", candidateDto);
//            return "register-page/register-candidate";
//        }

        candidateService.saveCandidate(candidateDto);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:" + userService.determineRedirectUrl(auth);
        }
        return "redirect:/";
    }
}
