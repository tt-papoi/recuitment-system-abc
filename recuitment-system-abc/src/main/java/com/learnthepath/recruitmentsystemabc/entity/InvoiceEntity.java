package com.learnthepath.recruitmentsystemabc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Integer id;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "payment_status")
    private String status;

    @Column(name = "method")
    private String method;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private RecruitmentEntity recruitment;
}
