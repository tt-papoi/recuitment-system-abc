package com.learnthepath.recruitmentsystemabc.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseDto {
    private Integer id;

    @NotEmpty(message = "Name cannot be blank")
    private String name;

    @NotEmpty(message = "Address cannot be blank")
    private String address;

    @NotEmpty(message = "Tax code cannot be blank")
    private String taxCode;

    @NotEmpty(message = "Representative cannot be blank")
    private String representative;

    @NotEmpty(message = "Phone number cannot be blank")
    private String phoneNumber;

    @NotEmpty(message = "Email cannot be blank")
    private String email;

    private String status;
}
