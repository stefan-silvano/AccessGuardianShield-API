package com.cybersecurity.iam.repository;

import com.cybersecurity.iam.database.relationship.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPermissionRepository extends JpaRepository<UserPermission, Integer> {
}
