package com.learnthepath.recruitmentsystemabc.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentDto {
    private Integer id;

    @NotEmpty(message = "Applied position cannot be blank")
    private String appliedPosition;

    @NotEmpty(message = "Requirment cannot be blank")
    private String requirments;

    @NotEmpty(message = "Job decription cannot be blank")
    private String jobDecription;

    @NotNull(message = "Number of candidates cannot be blank")
    private Integer numberCandidates;

    private String status;

    @NotNull(message = "Start date position cannot be blank")
    @Future(message = "Date must be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be blank")
    @Future(message = "Date must be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
