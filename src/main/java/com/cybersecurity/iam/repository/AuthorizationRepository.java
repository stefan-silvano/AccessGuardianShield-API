package com.cybersecurity.iam.repository;

import com.cybersecurity.iam.database.relationship.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, Integer> {
    List<Authorization> findAllByUserPermissionId(Integer userPermissionId);
    List<Authorization> findAllByUserRoleId(Integer userRoleId);
    List<Authorization> findAllByRolePermissionId(Integer rolePermissionId);
    List<Authorization> findAllByUserId(Integer userId);
    List<Authorization> findAllByRoleId(Integer roleId);
    List<Authorization> findAllByPermissionId(Integer permissionId);
}
