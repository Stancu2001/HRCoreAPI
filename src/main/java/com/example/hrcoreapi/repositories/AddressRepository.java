package com.example.hrcoreapi.repositories;

import com.example.hrcoreapi.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
