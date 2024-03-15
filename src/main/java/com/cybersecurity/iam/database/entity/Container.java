package com.cybersecurity.iam.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="container")
@Entity(name="Container")
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "container_id")
    private Container parent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Container)) return false;
        return id != null && id.equals(((Container) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
