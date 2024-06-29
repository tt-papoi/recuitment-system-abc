package com.learnthepath.recruitmentsystemabc.controller;

import com.learnthepath.recruitmentsystemabc.dto.InvoiceDto;
import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.service.EnterpriseService;
import com.learnthepath.recruitmentsystemabc.service.InvoiceService;
import com.learnthepath.recruitmentsystemabc.service.RecruitmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    RecruitmentService recruitmentService;

    @Autowired
    private InvoiceService invoiceService;

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
        Integer enterpriseId = enterpriseService.getCurrentEnterprise().getId();
        String status = "UNPAID";
        List<InvoiceDto> unpaidInvoices = invoiceService.findByEnterpriseIdAndStatus(enterpriseId, status);
        model.addAttribute("invoices", unpaidInvoices);
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
        recruitmentService.createNewRecruitment(recruitmentDto);
        return "redirect:/enterprise/recruitment/pending-approval";
    }

    @GetMapping("/enterprise/recruitment/pending-paid/details")
    String showPaymentDetails(@RequestParam(value = "id") Integer invoiceId, Model model) {
        InvoiceDto invoiceDto = invoiceService.findById(invoiceId);
        if (!invoiceDto.getStatus().equals("UNPAID")) {
            return "error/404";
        }
        model.addAttribute("invoice", invoiceDto);
        return "enterprise-page/enterprise-recruitment-payment-details";
    }
}
