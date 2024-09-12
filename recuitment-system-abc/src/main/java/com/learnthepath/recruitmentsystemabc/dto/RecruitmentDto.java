package com.learnthepath.recruitmentsystemabc.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotEmpty(message = "Requirement cannot be blank")
    private String requirements;

    @NotEmpty(message = "Job description cannot be blank")
    private String jobDescription;

    @NotNull(message = "Number of candidates cannot be blank")
    private Integer numberCandidates;

    private String status;

    @NotNull(message = "Number of candidates cannot be blank")
    private String disapprovalReason;

    @NotNull(message = "Work experience cannot be blank")
    private String workExperience;

    @NotNull(message = "Benefits cannot be blank")
    private String benefits;

    @NotNull(message = "Salary cannot be blank")
    private String salary;

    @NotNull(message = "Work location cannot be blank")
    private String workLocation;

    @NotNull(message = "Start date position cannot be blank")
    @Future(message = "Date must be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be blank")
    @Future(message = "Date must be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private EnterpriseDto enterprise;
}
