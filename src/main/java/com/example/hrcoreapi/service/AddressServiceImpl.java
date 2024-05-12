package com.example.hrcoreapi.service;

import com.example.hrcoreapi.entities.Address;
import com.example.hrcoreapi.repositories.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{
    public final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
    @Override
    public Address saveAddress(Address address){
        return addressRepository.save(address);
    }
}
