package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.InvoiceDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface InvoiceService {
    List<InvoiceDto> findByEnterpriseIdAndStatus(Integer enterpriseId, String status);
    InvoiceDto findById(Integer id);
    void createInvoice(Integer recruitmentId);
    Double calculatePriceBasedOnTime(LocalDate startDate, LocalDate endDate);
    double findPriceByTimeUnit(String timeUnit);
    void payInvoice(Integer invoiceId, String method);
}
