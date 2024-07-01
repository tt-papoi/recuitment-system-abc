package com.learnthepath.recruitmentsystemabc.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDto {
    private Integer id;

    @NotEmpty(message = "Name cannot be blank")
    private String name;

    @NotEmpty(message = "Phone number cannot be blank")
    private String phoneNumber;

    @NotEmpty(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "Date of birth cannot be blank")
    private LocalDate birthday;
}
