package com.cybersecurity.iam.repository;

import com.cybersecurity.iam.database.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
}
