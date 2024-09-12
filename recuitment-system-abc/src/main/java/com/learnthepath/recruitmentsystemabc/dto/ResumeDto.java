package com.learnthepath.recruitmentsystemabc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResumeDto {

    private Integer id;

    @NotNull
    private byte[] content;

    private String status;

    private RecruitmentDto recruitment;

    private CandidateDto candidate;
}
