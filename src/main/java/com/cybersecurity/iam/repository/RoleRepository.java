package com.cybersecurity.iam.repository;

import com.cybersecurity.iam.database.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
