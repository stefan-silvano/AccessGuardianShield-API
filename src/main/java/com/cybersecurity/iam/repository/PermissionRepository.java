package com.cybersecurity.iam.repository;

import com.cybersecurity.iam.database.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PermissionRepository extends JpaRepository<Permission, Integer>{
}
