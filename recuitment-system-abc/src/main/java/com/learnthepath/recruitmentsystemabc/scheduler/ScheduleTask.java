package com.learnthepath.recruitmentsystemabc.scheduler;

import com.learnthepath.recruitmentsystemabc.entity.InvoiceEntity;
import com.learnthepath.recruitmentsystemabc.entity.RecruitmentEntity;
import com.learnthepath.recruitmentsystemabc.repository.InvoiceRepository;
import com.learnthepath.recruitmentsystemabc.repository.RecruitmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleTask {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private RecruitmentRepository recruitmentRepository;

    // Run at 0am everyday
    @Scheduled(cron = "*/10 * * * * ?")
    @Transactional
    public void postRecruitments() {
        List<InvoiceEntity> paidInvoices = invoiceRepository.findByStatusAndRecruitmentStatus("PAID", "APPROVAL");
        LocalDate currentDate = LocalDate.now();

        for (InvoiceEntity invoice : paidInvoices) {
            LocalDate startDate = invoice.getRecruitment().getStartDate();
            LocalDate endDate = invoice.getRecruitment().getEndDate();
            if ((startDate.isEqual(currentDate) || startDate.isBefore(currentDate))
                    && endDate.isAfter(currentDate)) {
                invoice.getRecruitment().setStatus("POSTING");
            }
        }
    }

    // Run at 0am everyday
    @Scheduled(cron = "*/10 * * * * ?")
    @Transactional
    public void unpostRecruitments() {
        List<RecruitmentEntity> postingRecruitments = recruitmentRepository.findByStatus("POSTING");
        LocalDate currentDate = LocalDate.now();

        for (RecruitmentEntity postingRecruitment : postingRecruitments) {
            LocalDate endDate = postingRecruitment.getEndDate();
            if (endDate.isBefore(currentDate)) {
                postingRecruitment.setStatus("POSTED");
            }
        }
    }
}
