package com.learnthepath.recruitmentsystemabc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "candidates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateEntity {
    @Id
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserEntity user;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ResumeEntity> resumes = new HashSet<>();
}
