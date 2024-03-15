package com.cybersecurity.iam.repository;

import com.cybersecurity.iam.database.entity.BusinessCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessCodeRepository extends JpaRepository<BusinessCode, Integer> {
}
