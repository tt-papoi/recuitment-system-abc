package com.learnthepath.recruitmentsystemabc.controller;

import com.learnthepath.recruitmentsystemabc.service.CurrencyService;
import com.learnthepath.recruitmentsystemabc.service.EnterpriseService;
import com.learnthepath.recruitmentsystemabc.service.InvoiceService;
import com.learnthepath.recruitmentsystemabc.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Slf4j
@Controller
public class PaymentController {

    @Autowired
    private PaypalService paypalService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/enterprise/recruitment/payment/create")
    public String createPayment(
            @RequestParam("invoiceId") String invoiceId,
            @RequestParam("method") String method,
            @RequestParam("amount") String amountInVND,
            @RequestParam("description") String description,
            RedirectAttributes redirectAttributes
    ) {
        Double amountInUSD = 0.0;
        try {
            amountInUSD = currencyService.convert("VND", "USD", Double.parseDouble(amountInVND));
        } catch (IOException e) {
            log.error("Error: ", e);
        }

        try {
            String currency = "USD";
            String cancelUrl = "http://localhost:8080/enterprise/recruitment/payment/cancel?invoiceId=" + invoiceId;
            String successUrl = "http://localhost:8080/enterprise/recruitment/payment/success?invoiceId="
                    + invoiceId
                    + "&method=" + method;

            Payment payment = paypalService.createPayment(
                    amountInUSD,
                    currency,
                    method,
                    "sale",
                    description,
                    cancelUrl,
                    successUrl
            );

            for (Links links: payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred:: ", e);
        }

        String errorMessage = "An error occurred during payment processing";
        redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        return "redirect:/enterprise/recruitment/payment/error";
    }

    @GetMapping("/enterprise/recruitment/payment/success")
    public String paymentSuccess(
            @RequestParam("invoiceId") String invoiceId,
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
            @RequestParam("method") String method,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                invoiceService.payInvoice(Integer.parseInt(invoiceId), method);
                return "enterprise-page/enterprise-recruitment-payment-success";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Payment was not approved");
                return "redirect:/enterprise/recruitment/payment/error";
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred:: ", e);
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred during payment processing");
            return "redirect:/enterprise/recruitment/payment/error";
        }
    }

    @GetMapping("/enterprise/recruitment/payment/cancel")
    public String paymentCancel() {
        return "redirect:/enterprise/recruitment/pending-paid";
    }

    @GetMapping("/enterprise/recruitment/payment/error")
    public String paymentError(@ModelAttribute("errorMessage") String errorMessage, Model model) {
        model.addAttribute("enterprise", enterpriseService.getCurrentEnterprise());
        model.addAttribute("errorMessage", errorMessage);
        return "enterprise-page/enterprise-recruitment-payment-error";
    }
}
