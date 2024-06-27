package com.learnthepath.recruitmentsystemabc.controller;

import com.learnthepath.recruitmentsystemabc.dto.EnterpriseDto;
import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.exception.EntityNotFoundException;
import com.learnthepath.recruitmentsystemabc.service.AdminService;
import com.learnthepath.recruitmentsystemabc.service.EnterpriseService;
import com.learnthepath.recruitmentsystemabc.service.RecruitmentService;
import com.learnthepath.recruitmentsystemabc.utils.Utils;
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

    @GetMapping({"/admin/home", "/admin/enterprise"})
    String showApprovalEnterprisePage(Model model) {
        List<EnterpriseDto> notMemberEnterprises = adminService.getNonMemberEnterprises();
        model.addAttribute("nonMemberEnterprises", notMemberEnterprises);
        return "admin-page/admin-enterprise";
    }

    @GetMapping("/admin/enterprise/details")
    String showEnterpriseDetailsPage(@RequestParam(value = "id") Integer id, Model model) {
        EnterpriseDto enterpriseDto = Utils.mapToDto(enterpriseService.findById(id));
        if (!enterpriseDto.getStatus().equals("NON_MEMBER")) {
            return "error/404";
        }
        model.addAttribute("enterprise", enterpriseDto);
        return "admin-page/admin-enterprise-details";
    }

    @PostMapping("/admin/enterprise/approve")
    public String approveEnterprise(@RequestParam(value = "id", required = false) Integer id) {
        enterpriseService.updateStatusEnterpriseById(id, "MEMBER");
        return "redirect:/admin/enterprise";
    }

    @PostMapping("/admin/enterprise/disapprove")
    public String disapproveEnterprise(@RequestParam(value = "id", required = false) Integer id) {
        enterpriseService.updateStatusEnterpriseById(id, "DISAPPROVAL");
        return "redirect:/admin/enterprise";
    }

    @GetMapping("/admin/recruitment")
    String showApprovalRecruitmentPage(Model model) {
        List<RecruitmentDto> pendingApprovalRecruitment = adminService.getRecruitmentByStatus("PENDING_APPROVAL");
        model.addAttribute("recruitments", pendingApprovalRecruitment);
        return "admin-page/admin-recruitment";
    }

    @GetMapping("/admin/recruitment/details")
    String showRecruitmentDetailsPage(@RequestParam(value = "id") Integer id, Model model) {
        RecruitmentDto recruitmentDto = Utils.mapToDto(adminService.findById(id));
        if (!recruitmentDto.getStatus().equals("PENDING_APPROVAL")) {
            return "error/404";
        }
        model.addAttribute("recruitment", recruitmentDto);
        return "admin-page/admin-recruitment-details";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404"; // Assuming you have an error/404.html template
    }
}
