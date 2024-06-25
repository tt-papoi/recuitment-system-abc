package com.learnthepath.recruitmentsystemabc.controller;

import com.learnthepath.recruitmentsystemabc.dto.EnterpriseDto;
import com.learnthepath.recruitmentsystemabc.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping({"admin/home", "admin/approval-enterprise"})
    String showApprovalEnterprisePage(Model model) {
        List<EnterpriseDto> notMemberEnterprises = adminService.getNonMemberEnterprises();
        model.addAttribute("nonMemberEnterprises", notMemberEnterprises);
        return "admin-page/approval-enterprise";
    }

    @GetMapping("admin/approval-recruitment")
    String showApprovalRecruitmentPage() {
        return "admin-page/approval-recruitment";
    }
}
