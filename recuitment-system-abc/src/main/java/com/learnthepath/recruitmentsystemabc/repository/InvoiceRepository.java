package com.learnthepath.recruitmentsystemabc.repository;

import com.learnthepath.recruitmentsystemabc.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Integer> {
    @Query("SELECT i " +
            "FROM InvoiceEntity i " +
            "WHERE i.recruitment.id = :recruitmentId " +
            "AND i.status = :status")
    List<InvoiceEntity> findByRecruitmentIdAndStatus(Integer recruitmentId, String status);

    @Query("SELECT i " +
            "FROM InvoiceEntity i " +
            "WHERE i.recruitment.enterprise.id = :enterpriseId " +
            "AND i.status = :status")
    List<InvoiceEntity> findByEnterpriseIdAndStatus(Integer enterpriseId, String status);

    @Query("SELECT i FROM InvoiceEntity i " +
            "WHERE i.status = :invoiceStatus " +
            "AND i.recruitment.status = :recruitmentStatus")
    List<InvoiceEntity> findByStatusAndRecruitmentStatus(String invoiceStatus, String recruitmentStatus);
}
