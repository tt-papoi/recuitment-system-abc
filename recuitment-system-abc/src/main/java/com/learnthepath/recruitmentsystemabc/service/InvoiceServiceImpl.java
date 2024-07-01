package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.InvoiceDto;
import com.learnthepath.recruitmentsystemabc.entity.InvoiceEntity;
import com.learnthepath.recruitmentsystemabc.entity.RecruitmentEntity;
import com.learnthepath.recruitmentsystemabc.entity.TimeBasedPriceEntity;
import com.learnthepath.recruitmentsystemabc.exception.EntityNotFoundException;
import com.learnthepath.recruitmentsystemabc.repository.InvoiceRepository;
import com.learnthepath.recruitmentsystemabc.repository.RecruitmentRepository;
import com.learnthepath.recruitmentsystemabc.repository.TimeBasedPriceRepository;
import com.learnthepath.recruitmentsystemabc.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService{
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Autowired
    private TimeBasedPriceRepository timeBasedPriceRepository;

    @Override
    public List<InvoiceDto> findByEnterpriseIdAndStatus(Integer enterpriseId, String status) {
        return invoiceRepository.findByEnterpriseIdAndStatus(enterpriseId, status).stream()
                .map(Utils::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDto findById(Integer id) {
        return Utils.mapToDto(
                invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find recruitment with id: " + id)));
    }

    @Override
    public void createInvoice(Integer recruitmentId) {
        String paymentStatus = "UNPAID";
        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setStatus(paymentStatus);

        RecruitmentEntity recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find recruitment with id: " + recruitmentId));

        invoice.setTotalAmount(
                calculatePriceBasedOnTime(
                        recruitment.getStartDate(),
                        recruitment.getEndDate()));

        invoice.setRecruitment(recruitment);
        invoiceRepository.save(invoice);
    }

    @Override
    public Double calculatePriceBasedOnTime(LocalDate startDate, LocalDate endDate) {
        double price = 0.0;

        long daysBetween;
        long monthsBetween;
        long yearsBetween = ChronoUnit.YEARS.between(startDate, endDate);

        if (yearsBetween > 0) {
            price += yearsBetween * findPriceByTimeUnit("YEAR");
            startDate = startDate.plusYears(yearsBetween);
        }

        monthsBetween = ChronoUnit.MONTHS.between(startDate, endDate);
        if (monthsBetween > 0) {
            price += monthsBetween * findPriceByTimeUnit("MONTH");
            startDate = startDate.plusMonths(monthsBetween);
        }

        daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        if (daysBetween > 0) {
            price += daysBetween * findPriceByTimeUnit("DAY");
        }

        return price;
    }

    @Override
    public double findPriceByTimeUnit(String timeUnit) {
        double price = 0.0;
        for(TimeBasedPriceEntity timeBasedPrice : timeBasedPriceRepository.findAll()) {
            if(timeBasedPrice.getTimeUnit().equals(timeUnit)) {
                return timeBasedPrice.getPrice();
            }
        }
        return price;
    }

    @Override
    public void payInvoice(Integer invoiceId, String method) {
        InvoiceEntity invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find invoice with id: " + invoiceId));
        invoice.setStatus("PAID");
        invoice.setMethod(method);
        LocalDate currentDate = LocalDate.now();
        invoice.setPaymentDate(currentDate);
        invoiceRepository.save(invoice);
    }
}
