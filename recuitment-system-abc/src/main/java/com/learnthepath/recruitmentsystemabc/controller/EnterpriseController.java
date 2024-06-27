package com.learnthepath.recruitmentsystemabc.controller;

import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.service.EnterpriseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;


    @GetMapping("/enterprise/home")
    public String showEnterpriseHomePage(Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        return "enterprise-page/enterprise-posted-recruitment";
    }

    @GetMapping("/enterprise/recruitment/posted")
    public String showPostedJobsPage(Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        return "enterprise-page/enterprise-posted-recruitment";
    }

    @GetMapping("/enterprise/recruitment/pending-paid")
    public String showPendingPaidPage(Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        return "enterprise-page/enterprise-pending-paid-recruitment";
    }

    @GetMapping("/enterprise/recruitment/disapproval")
    public String showDisapprovalPage(Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        return "enterprise-page/enterprise-disapproval-recruitment";
    }

    @GetMapping("/enterprise/recruitment/pending-approval")
    public String showPendingApprovalPage(Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        List<RecruitmentDto> recruitmentDtos = enterpriseService.findPendingApprovalRecruitment();
        model.addAttribute("pendingApprovalRecruitment", recruitmentDtos);

        return "enterprise-page/enterprise-pending-approval-recruitment";
    }

    @GetMapping("/enterprise/recruitment/create")
    public String showCreateRecruitmentPage(Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        model.addAttribute("recruitment", new RecruitmentDto());
        return "enterprise-page/enterprise-create-recruitment";
    }

    @PostMapping("/enterprise/recruitment/create/submit")
    String handleCreateRecruitment(@Valid RecruitmentDto recruitmentDto, BindingResult result) {
        enterpriseService.createNewRecruitment(recruitmentDto);
        return "redirect:/enterprise/recruitment/pending-approval";
    }
}
