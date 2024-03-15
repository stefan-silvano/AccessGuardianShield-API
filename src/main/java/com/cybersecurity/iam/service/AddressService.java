package com.cybersecurity.iam.service;

import com.cybersecurity.iam.exception.type.NotFoundException;
import com.cybersecurity.iam.database.entity.Address;
import com.cybersecurity.iam.payload.request.AddressRequest;
import com.cybersecurity.iam.payload.response.AddressResponse;
import com.cybersecurity.iam.payload.response.MessageResponse;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.validator.RequestValidator;
import com.cybersecurity.iam.repository.AddressRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressResponse createAddress(AddressRequest request) {
        RequestValidator.validateObject(request);
        Address address = Address.builder()
                .country(request.getCountry())
                .city(request.getCity())
                .street(request.getStreet())
                .number(request.getNumber())
                .postalCode(request.getPostalCode())
                .build();
        address = addressRepository.save(address);
        return AddressResponse.builder()
                .id(address.getId())
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .number(address.getNumber())
                .postalCode(address.getPostalCode())
                .build();
    }

    public AddressResponse getAddress(Integer id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address with id: " + id + " not found"));

        return AddressResponse.builder()
                .id(address.getId())
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .number(address.getNumber())
                .postalCode(address.getPostalCode())
                .build();
    }

    public AddressResponse patchAddress(Integer id, AddressRequest request) {
        RequestValidator.validateObject(request);
        Address address = Address.builder()
                .id(id)
                .country(request.getCountry())
                .city(request.getCity())
                .street(request.getStreet())
                .number(request.getNumber())
                .postalCode(request.getPostalCode())
                .build();
        address = addressRepository.save(address);
        return AddressResponse.builder()
                .id(address.getId())
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .number(address.getNumber())
                .postalCode(address.getPostalCode())
                .build();
    }

    public GenericResponse deleteAddress(Integer id) {
        if (!addressRepository.existsById(id)) {
            throw new NotFoundException("Address with id: " + id + " not found");
        }
        addressRepository.deleteById(id);
        return new GenericResponse(addressRepository.findById(id), "Address with id: " + id
                + " successfully deleted", HttpServletResponse.SC_OK);
    }

    public List<GenericResponse> createAddresses(List<AddressRequest> request) {
        List<GenericResponse> response = new ArrayList<>();
        for (AddressRequest req : request) {
            if (RequestValidator.isObjectValid(req) != null) {
                response.add(new GenericResponse(req, RequestValidator.isObjectValid(req), HttpServletResponse.SC_BAD_REQUEST));
            } else {
                Address address = Address.builder()
                        .country(req.getCountry())
                        .city(req.getCity())
                        .street(req.getStreet())
                        .number(req.getNumber())
                        .postalCode(req.getPostalCode())
                        .build();
                addressRepository.save(address);
                response.add(new GenericResponse(req, "Address successfully created", HttpServletResponse.SC_CREATED));
            }
        }
        return response;
    }

    public List<AddressResponse> getAddresses() {
        List<Address> addresses = addressRepository.findAll();
        if(addresses.isEmpty())
            throw new NotFoundException("No address was found");

        List<AddressResponse> addressesResponse = new ArrayList<>();
        for (Address adr : addresses) {
            addressesResponse.add(AddressResponse.builder()
                    .id(adr.getId())
                    .country(adr.getCountry())
                    .city(adr.getCity())
                    .street(adr.getStreet())
                    .number(adr.getNumber())
                    .postalCode(adr.getPostalCode())
                    .build()
            );
        }
        return addressesResponse;
    }

    public List<GenericResponse> deleteAddresses(List<Integer> ids) {
        List<GenericResponse> response = new ArrayList<>();
        for (Integer id : ids) {
            if(addressRepository.existsById(id)){
                response.add(new GenericResponse(addressRepository.findById(id), "Address with id: " + id + " successfully deleted", HttpServletResponse.SC_OK));
                addressRepository.deleteById(id);
            }
            else{
                response.add(new GenericResponse(null, "Address with id: " + id + " not found", HttpServletResponse.SC_NOT_FOUND));
            }
        }
        return response;
    }
}
