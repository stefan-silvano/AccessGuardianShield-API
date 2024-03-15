package com.cybersecurity.iam.database.relationship;

import com.cybersecurity.iam.database.entity.Permission;
import com.cybersecurity.iam.database.entity.Role;
import com.cybersecurity.iam.database.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authorization")
@Entity(name = "Authorization")
public class Authorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_id")
    private Permission permission;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role_id")
    private UserRole userRole;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_permission_id")
    private UserPermission userPermission;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_permission_id")
    private RolePermission rolePermission;

    private Timestamp startDate;

    private Timestamp endDate;
}
