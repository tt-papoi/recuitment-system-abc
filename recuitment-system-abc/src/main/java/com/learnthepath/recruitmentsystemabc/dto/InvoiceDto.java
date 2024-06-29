package com.learnthepath.recruitmentsystemabc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {
    private Integer id;

    private LocalDate paymentDate;

    private Double totalAmount;

    private String status;

    private String method;

    private RecruitmentDto recruitment;
}
