package com.cybersecurity.iam.repository;

import com.cybersecurity.iam.database.entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainerRepository extends JpaRepository<Container, Integer> {
}
