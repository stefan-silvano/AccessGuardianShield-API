package com.cybersecurity.iam.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="role")
@Entity(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private String type;

    private String riskLevel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "container_id")
    private Container container;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        return id != null && id.equals(((Role) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
