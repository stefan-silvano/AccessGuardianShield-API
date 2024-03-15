package com.cybersecurity.iam.database.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="permission")
@Entity(name = "Permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private String riskLevel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "container_id")
    private Container container;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permission)) return false;
        return id != null && id.equals(((Permission) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
