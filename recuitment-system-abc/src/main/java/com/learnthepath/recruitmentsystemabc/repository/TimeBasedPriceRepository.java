package com.learnthepath.recruitmentsystemabc.repository;

import com.learnthepath.recruitmentsystemabc.entity.TimeBasedPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeBasedPriceRepository extends JpaRepository<TimeBasedPriceEntity, Integer> {
}
