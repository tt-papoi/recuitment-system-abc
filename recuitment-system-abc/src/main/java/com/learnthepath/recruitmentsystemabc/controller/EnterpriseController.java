package com.learnthepath.recruitmentsystemabc.controller;

import com.learnthepath.recruitmentsystemabc.dto.EnterpriseDto;
import com.learnthepath.recruitmentsystemabc.dto.InvoiceDto;
import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.dto.ResumeDto;
import com.learnthepath.recruitmentsystemabc.service.EnterpriseService;
import com.learnthepath.recruitmentsystemabc.service.InvoiceService;
import com.learnthepath.recruitmentsystemabc.service.RecruitmentService;
import com.learnthepath.recruitmentsystemabc.service.ResumeService;
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
    private RecruitmentService recruitmentService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ResumeService resumeService;

    @GetMapping({"/enterprise/recruitment/posted", "/enterprise/home"})
    public String showPostedJobsPage(Model model) {
        EnterpriseDto enterpriseDto = enterpriseService.getCurrentEnterprise();
        model.addAttribute("enterprise", enterpriseDto);
        List<RecruitmentDto> recruitmentDtos = recruitmentService
                .findRecruitmentByEnterpriseIdAndStatus(enterpriseDto.getId(), "POSTED");
        recruitmentDtos.addAll(recruitmentService
                .findRecruitmentByEnterpriseIdAndStatus(enterpriseDto.getId(), "POSTING"));
        model.addAttribute("recruitments", recruitmentDtos);
        return "enterprise-page/enterprise-recruitment-posted";
    }

    @GetMapping("/enterprise/recruitment/posted/details")
    public String showPostedJobsDetails(@RequestParam(value = "recruitmentId") Integer recruitmentId,
                                        @RequestParam(value = "resumesStatus", required = false) String resumesStatus,
                                        Model model) {
        RecruitmentDto recruitmentDto = recruitmentService.findById(recruitmentId);
        if (!recruitmentDto.getStatus().equals("POSTED") && !recruitmentDto.getStatus().equals("POSTING")) {
            return "error/404";
        }
        List<ResumeDto> resumes;
        if(resumesStatus == null) {
            resumes = resumeService.findByRecruitmentId(recruitmentId);
        }
        else {
            resumes = resumeService.findByRecruitmentIdAndStatus(recruitmentId, resumesStatus);
        }

        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        model.addAttribute("recruitment", recruitmentDto);
        model.addAttribute("resumes", resumes);
        model.addAttribute("resumesStatus", resumesStatus);
        return "enterprise-page/enterprise-recruitment-posted-details";
    }

    @GetMapping("/enterprise/recruitment/disapproval")
    public String showDisapprovalRecruitmentPage(Model model) {
        EnterpriseDto enterpriseDto = enterpriseService.getCurrentEnterprise();
        model.addAttribute("enterprise", enterpriseDto);
        String status = "DISAPPROVAL";
        List<RecruitmentDto> recruitmentDtos = recruitmentService.findRecruitmentByEnterpriseIdAndStatus(enterpriseDto.getId(), status);
        model.addAttribute("pendingApprovalRecruitment", recruitmentDtos);

        return "enterprise-page/enterprise-recruitment-disapproval";
    }

    @GetMapping("/enterprise/recruitment/disapproval/details")
    public String showDisapprovalRecruitmentDetails(@RequestParam(value = "id") Integer recruitmentId, Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        RecruitmentDto recruitmentDto = recruitmentService.findById(recruitmentId);
        if (!recruitmentDto.getStatus().equals("DISAPPROVAL")) {
            return "error/404";
        }
        model.addAttribute("recruitment", recruitmentDto);
        return "enterprise-page/enterprise-recruitment-disapproval-details";
    }

    @GetMapping("/enterprise/recruitment/pending-approval")
    public String showPendingApprovalPage(Model model) {
        EnterpriseDto enterpriseDto = enterpriseService.getCurrentEnterprise();
        model.addAttribute("enterprise", enterpriseDto);
        String status = "PENDING_APPROVAL";
        List<RecruitmentDto> recruitmentDtos = recruitmentService.findRecruitmentByEnterpriseIdAndStatus(enterpriseDto.getId(), status);
        model.addAttribute("pendingApprovalRecruitment", recruitmentDtos);

        return "enterprise-page/enterprise-recruitment-pending-approval";
    }

    @GetMapping("/enterprise/recruitment/pending-approval/details")
    public String showPendingApprovalRecruitmentDetails(@RequestParam(value = "id") Integer recruitmentId, Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        RecruitmentDto recruitmentDto = recruitmentService.findById(recruitmentId);
        if (!recruitmentDto.getStatus().equals("PENDING_APPROVAL")) {
            return "error/404";
        }
        model.addAttribute("recruitment", recruitmentDto);
        return "enterprise-page/enterprise-recruitment-pending-approval-details";
    }

    @GetMapping("/enterprise/recruitment/create")
    public String showCreateRecruitmentPage(Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        model.addAttribute("recruitment", new RecruitmentDto());
        model.addAttribute("title", "Tạo tin tuyển dụng");
        return "enterprise-page/enterprise-recruitment-create";
    }

    @GetMapping("/enterprise/recruitment/edit")
    public String showEditRecruitmentPage(@RequestParam(value = "id") Integer recruitmentId, Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        RecruitmentDto recruitmentDto = recruitmentService.findById(recruitmentId);
        if (!recruitmentDto.getStatus().equals("DISAPPROVAL")) {
            return "error/404";
        }
        model.addAttribute("recruitment", recruitmentDto);
        model.addAttribute("title", "Chỉnh sửa");
        return "enterprise-page/enterprise-recruitment-create";
    }

    @PostMapping("/enterprise/recruitment/create/submit")
    public String handleCreateRecruitment(@Valid RecruitmentDto recruitmentDto, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("title", "Chỉnh sửa");
//            model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
//            model.addAttribute("recruitment", recruitmentDto);
//            return "enterprise-page/enterprise-recruitment-create";
//        }

        if(recruitmentDto.getId() == null) {
            recruitmentService.createNewRecruitment(recruitmentDto);
            return "redirect:/enterprise/recruitment/pending-approval";
        }

        RecruitmentDto recruitment = recruitmentService.findById(recruitmentDto.getId());
        if(recruitment != null) {
            recruitmentService.updateRecruitment(recruitmentDto);
        }
        return "redirect:/enterprise/recruitment/pending-approval";
    }

    @GetMapping("/enterprise/recruitment/pending-paid")
    public String showPendingPaidPage(Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        Integer enterpriseId = enterpriseService.getCurrentEnterprise().getId();
        String status = "UNPAID";
        List<InvoiceDto> unpaidInvoices = invoiceService.findByEnterpriseIdAndStatus(enterpriseId, status);
        model.addAttribute("invoices", unpaidInvoices);
        return "enterprise-page/enterprise-recruitment-payments";
    }

    @GetMapping("/enterprise/recruitment/pending-paid/details")
    public String showPaymentDetails(@RequestParam(value = "id") Integer invoiceId, Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        InvoiceDto invoiceDto = invoiceService.findById(invoiceId);
        if (!invoiceDto.getStatus().equals("UNPAID")) {
            return "error/404";
        }
        model.addAttribute("invoice", invoiceDto);
        return "enterprise-page/enterprise-recruitment-payment-details";
    }

    @GetMapping("/enterprise/invoice")
    public String showInvoicePage(Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        Integer enterpriseId = enterpriseService.getCurrentEnterprise().getId();
        String status = "PAID";
        List<InvoiceDto> invoices = invoiceService.findByEnterpriseIdAndStatus(enterpriseId, status);
        model.addAttribute("invoices", invoices);
        return "enterprise-page/enterprise-invoices";
    }

    @GetMapping("/enterprise/invoice/details")
    public String showInvoiceDetails(@RequestParam(value = "id") Integer invoiceId, Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        InvoiceDto invoiceDto = invoiceService.findById(invoiceId);
        if (!invoiceDto.getStatus().equals("PAID")) {
            return "error/404";
        }
        model.addAttribute("invoice", invoiceDto);
        return "enterprise-page/enterprise-invoice-details";
    }
}
