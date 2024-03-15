package com.cybersecurity.iam.repository;

import com.cybersecurity.iam.database.relationship.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    List<RolePermission> findAllByRoleId(Integer id);
}
