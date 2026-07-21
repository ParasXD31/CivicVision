package com.paras.civicvision.backend.repository;

import com.paras.civicvision.backend.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository
        extends JpaRepository<Complaint, Long> {

}