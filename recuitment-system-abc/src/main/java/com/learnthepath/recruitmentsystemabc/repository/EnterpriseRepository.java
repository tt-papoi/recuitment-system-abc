package com.learnthepath.recruitmentsystemabc.repository;

import com.learnthepath.recruitmentsystemabc.entity.EnterpriseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterpriseRepository extends JpaRepository<EnterpriseEntity, Integer> {
    List<EnterpriseEntity> findByStatus(String status);
}
