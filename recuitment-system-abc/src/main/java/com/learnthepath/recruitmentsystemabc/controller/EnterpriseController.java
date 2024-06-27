package com.learnthepath.recruitmentsystemabc.controller;

import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.service.EnterpriseService;
import com.learnthepath.recruitmentsystemabc.service.RecruitmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private RecruitmentService recruitmentService;

    @GetMapping("/enterprise/home")
    public String showEnterpriseHomePage(Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        return "enterprise-page/enterprise-posted-job";
    }

    @GetMapping("/enterprise/recruitment/posted")
    public String showPostedJobsPage(Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        return "enterprise-page/enterprise-posted-job";
    }

    @GetMapping("/enterprise/recruitment/pending-paid")
    public String showPendingPaidPage(Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        return "enterprise-page/enterprise-posted-job";
    }

    @GetMapping("/enterprise/recruitment/disapproval")
    public String showDisapprovalPage(Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        return "enterprise-page/enterprise-posted-job";
    }

    @GetMapping("/enterprise/recruitment/pending-approval")
    public String showPendingApprovalPage(Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        return "enterprise-page/enterprise-posted-job";
    }

    @GetMapping("/enterprise/recruitment/create")
    public String showCreateRecruitmentPage(Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        model.addAttribute("recruitment", new RecruitmentDto());
        return "enterprise-page/enterprise-create-recruitment";
    }

    @PostMapping("/enterprise/recruitment/create/submit")
    String handleCreateRecruitment(@Valid RecruitmentDto recruitmentDto, BindingResult result) {
        recruitmentService.createNewRecruitment(recruitmentDto);
        return "redirect:/enterprise/recruitment/pending-approval";
    }
}
