package com.learnthepath.recruitmentsystemabc.controller;

import com.learnthepath.recruitmentsystemabc.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;

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
}
