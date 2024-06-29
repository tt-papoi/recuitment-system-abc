package com.learnthepath.recruitmentsystemabc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "time_based_price")
public class TimeBasedPriceEntity {
    @Id
    Integer id;

    @Column(name = "time_unit")
    String timeUnit;

    @Column(name = "price")
    Double price;
}
