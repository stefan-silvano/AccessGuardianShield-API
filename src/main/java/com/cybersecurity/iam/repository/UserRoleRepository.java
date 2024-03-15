package com.cybersecurity.iam.repository;

import com.cybersecurity.iam.database.relationship.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}
