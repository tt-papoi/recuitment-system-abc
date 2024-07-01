package com.learnthepath.recruitmentsystemabc.controller;

import com.learnthepath.recruitmentsystemabc.dto.EnterpriseDto;
import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.service.AdminService;
import com.learnthepath.recruitmentsystemabc.service.EnterpriseService;
import com.learnthepath.recruitmentsystemabc.service.InvoiceService;
import com.learnthepath.recruitmentsystemabc.service.RecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private RecruitmentService recruitmentService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private AdminService adminService;

    @GetMapping({"/admin/home", "/admin/enterprise"})
    public String showApprovalEnterprisePage(Model model) {
        List<EnterpriseDto> notMemberEnterprises = enterpriseService.getNonMemberEnterprises();
        model.addAttribute("nonMemberEnterprises", notMemberEnterprises);
        return "admin-page/admin-enterprise";
    }

    @GetMapping("/admin/enterprise/details")
    public String showEnterpriseDetailsPage(@RequestParam(value = "id") Integer id, Model model) {
        EnterpriseDto enterpriseDto = enterpriseService.findById(id);
        if (!enterpriseDto.getStatus().equals("NON_MEMBER")) {
            return "error/404";
        }
        model.addAttribute("enterprise", enterpriseDto);
        return "admin-page/admin-enterprise-details";
    }

    @PostMapping("/admin/enterprise/approve")
    public String approveEnterprise(@RequestParam(value = "id", required = false) Integer id) {
        enterpriseService.updateStatusById(id, "MEMBER");
        return "redirect:/admin/enterprise";
    }

    @PostMapping("/admin/enterprise/disapprove")
    public String disapproveEnterprise(@RequestParam(value = "id", required = false) Integer id) {
        enterpriseService.updateStatusById(id, "DISAPPROVAL");
        return "redirect:/admin/enterprise";
    }

    @GetMapping("/admin/recruitment")
    public String showApprovalRecruitmentPage(Model model) {
        List<RecruitmentDto> pendingApprovalRecruitment = adminService.findRecruitmentByStatus("PENDING_APPROVAL");
        model.addAttribute("recruitments", pendingApprovalRecruitment);
        return "admin-page/admin-recruitment";
    }

    @GetMapping("/admin/recruitment/details")
    String showRecruitmentDetailsPage(@RequestParam(value = "id") Integer id, Model model) {
        RecruitmentDto recruitmentDto = recruitmentService.findById(id);
        if (!recruitmentDto.getStatus().equals("PENDING_APPROVAL")) {
            return "error/404";
        }
        model.addAttribute("recruitment", recruitmentDto);
        return "admin-page/admin-recruitment-details";
    }

    @PostMapping("/admin/recruitment/approve")
    public String approveRecruitment(RecruitmentDto recruitmentDto) {
        recruitmentService.updateStatusById(recruitmentDto.getId(), "APPROVAL");
        invoiceService.createInvoice(recruitmentDto.getId());
        return "redirect:/admin/recruitment";
    }

    @PostMapping("/admin/recruitment/disapprove")
    public String disapproveRecruitment(RecruitmentDto recruitmentDto) {
        Integer recruitmentId = recruitmentDto.getId();
        recruitmentService.updateStatusById(recruitmentId, "DISAPPROVAL");
        recruitmentService.updateDisapprovalReasonById(recruitmentId, recruitmentDto.getDisapprovalReason());
        return "redirect:/admin/recruitment";
    }
}
