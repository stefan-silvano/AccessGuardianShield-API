package com.cybersecurity.iam.service;

import com.cybersecurity.iam.database.entity.Address;
import com.cybersecurity.iam.database.entity.Container;
import com.cybersecurity.iam.database.entity.Organization;
import com.cybersecurity.iam.exception.type.NotFoundException;
import com.cybersecurity.iam.payload.request.OrganizationRequest;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.response.MessageResponse;
import com.cybersecurity.iam.payload.response.OrganizationResponse;
import com.cybersecurity.iam.payload.validator.RequestValidator;
import com.cybersecurity.iam.repository.AddressRepository;
import com.cybersecurity.iam.repository.OrganizationRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final AddressRepository addressRepository;

    public OrganizationResponse createOrganization(OrganizationRequest request) {
        RequestValidator.validateObject(request);
        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new NotFoundException("Address with id: " + request.getAddressId() + "not found"));
        Organization organization = Organization.builder()
                .name(request.getName())
                .description(request.getDescription())
                .phoneNumber(request.getPhoneNumber())
                .address(address)
                .build();
        organization = organizationRepository.save(organization);
        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .description(organization.getDescription())
                .phoneNumber(organization.getPhoneNumber())
                .address(organization.getAddress())
                .build();
    }

    public OrganizationResponse getOrganization(Integer id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organization with id: " + id + "not found"));
        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .description(organization.getDescription())
                .phoneNumber(organization.getPhoneNumber())
                .address(organization.getAddress())
                .build();
    }

    public OrganizationResponse patchOrganization(Integer id, OrganizationRequest request) {
        RequestValidator.validateObject(request);
        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new NotFoundException("Address with id: " + request.getAddressId() + " not found"));
        Organization organization = Organization.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .phoneNumber(request.getPhoneNumber())
                .address(address)
                .build();
        organization = organizationRepository.save(organization);
        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .description(organization.getDescription())
                .phoneNumber(organization.getPhoneNumber())
                .address(organization.getAddress())
                .build();
    }

    public GenericResponse deleteOrganization(Integer id) {
        if (!organizationRepository.existsById(id)) {
            throw new NotFoundException("Organization with id: " + id + " not found");
        }
        organizationRepository.deleteById(id);
        return new GenericResponse(organizationRepository.findById(id), "Organization with id: " + id
                + " successfully deleted", HttpServletResponse.SC_OK);
    }

    public List<GenericResponse> createOrganizations(List<OrganizationRequest> request) {
        List<GenericResponse> response = new ArrayList<>();
        for (OrganizationRequest req : request) {
            if (RequestValidator.isObjectValid(req) != null) {
                response.add(new GenericResponse(req, RequestValidator.isObjectValid(req), HttpServletResponse.SC_BAD_REQUEST));
            } else {
                Address address = addressRepository.findById(req.getAddressId())
                        .orElse(null);
                if (address != null) {
                    Organization organization = Organization.builder()
                            .name(req.getName())
                            .description(req.getDescription())
                            .phoneNumber(req.getPhoneNumber())
                            .address(address)
                            .build();
                    organizationRepository.save(organization);
                    response.add(new GenericResponse(req, "Organization successfully created", HttpServletResponse.SC_CREATED));
                } else {
                    response.add(new GenericResponse(req, "The organizationId " + req.getAddressId() + " not found", HttpServletResponse.SC_BAD_REQUEST));
                }
            }
        }
        return response;
    }

    public List<OrganizationResponse> getOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();
        if (organizations.isEmpty()) {
            throw new NotFoundException("No organization was found");
        }

        List<OrganizationResponse> organizationsResponse = new ArrayList<>();
        for (Organization org : organizations) {
            organizationsResponse.add(OrganizationResponse.builder()
                    .id(org.getId())
                    .name(org.getName())
                    .description(org.getDescription())
                    .phoneNumber(org.getPhoneNumber())
                    .address(org.getAddress())
                    .build()
            );
        }
        return organizationsResponse;
    }

    public List<GenericResponse> deleteOrganizations(List<Integer> ids) {
        List<GenericResponse> response = new ArrayList<>();
        for (Integer id : ids) {
            if (organizationRepository.existsById(id)) {
                response.add(new GenericResponse(organizationRepository.findById(id), "Organization with id: " + id + " successfully deleted", HttpServletResponse.SC_OK));
                organizationRepository.deleteById(id);
            } else {
                response.add(new GenericResponse(null, "Organization with id: " + id + " not found", HttpServletResponse.SC_NOT_FOUND));
            }
        }
        return response;
    }
}
