package com.learnthepath.recruitmentsystemabc.controller;

import com.learnthepath.recruitmentsystemabc.dto.EnterpriseDto;
import com.learnthepath.recruitmentsystemabc.exception.EntityNotFoundException;
import com.learnthepath.recruitmentsystemabc.service.AdminService;
import com.learnthepath.recruitmentsystemabc.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    EnterpriseService enterpriseService;

    @GetMapping({"/admin/home", "/admin/approval-enterprise"})
    String showApprovalEnterprisePage(Model model) {
        List<EnterpriseDto> notMemberEnterprises = adminService.getNonMemberEnterprises();
        model.addAttribute("nonMemberEnterprises", notMemberEnterprises);
        return "admin-page/approval-enterprise";
    }

    @GetMapping("/admin/approval-enterprise/details")
    String showEnterpriseDetailsPage(@RequestParam(value = "id") Integer id, Model model) {
        EnterpriseDto enterpriseDto = enterpriseService.mapToEnterpriseDto(enterpriseService.findById(id));
        if (!enterpriseDto.getStatus().equals("NON_MEMBER")) {
            return "error/404";
        }
        model.addAttribute("enterprise", enterpriseDto);
        return "admin-page/approval-enterprise-details";
    }

    @GetMapping("/admin/approval-recruitment")
    String showApprovalRecruitmentPage() {
        return "admin-page/approval-recruitment";
    }

    @PostMapping("/admin/approve-enterprise/approve")
    public String approveEnterprise(@RequestParam(value = "id", required = false) Integer id) {
        enterpriseService.updateStatusEnterpriseById(id, "MEMBER");
        return "redirect:/admin/approval-enterprise";
    }

    @PostMapping("/admin/approve-enterprise/disapprove")
    public String disapproveEnterprise(@RequestParam(value = "id", required = false) Integer id) {
        enterpriseService.updateStatusEnterpriseById(id, "DISAPPROVAL");
        return "redirect:/admin/approval-enterprise";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404"; // Assuming you have an error/404.html template
    }
}
