package com.cybersecurity.iam.repository;

import com.cybersecurity.iam.database.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
