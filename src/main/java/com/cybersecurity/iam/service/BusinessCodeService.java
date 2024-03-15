package com.cybersecurity.iam.service;

import com.cybersecurity.iam.database.entity.BusinessCode;
import com.cybersecurity.iam.database.entity.Role;
import com.cybersecurity.iam.exception.type.NotFoundException;
import com.cybersecurity.iam.payload.request.BusinessCodeRequest;
import com.cybersecurity.iam.payload.response.BusinessCodeResponse;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.response.MessageResponse;
import com.cybersecurity.iam.payload.validator.RequestValidator;
import com.cybersecurity.iam.repository.BusinessCodeRepository;
import com.cybersecurity.iam.repository.RoleRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessCodeService {

    private final BusinessCodeRepository businessCodeRepository;
    private final RoleRepository roleRepository;

    public BusinessCodeResponse createBusinessCode(BusinessCodeRequest request) {
        RequestValidator.validateObject(request);
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new NotFoundException("Role with id: " + request.getRoleId() + " not found"));
        BusinessCode businessCode = BusinessCode.builder()
                .code(request.getCode())
                .role(role)
                .build();
        businessCode = businessCodeRepository.save(businessCode);
        return BusinessCodeResponse.builder()
                .id(businessCode.getId())
                .code(businessCode.getCode())
                .role(businessCode.getRole())
                .build();
    }

    public BusinessCodeResponse getBusinessCode(Integer id) {
        BusinessCode businessCode = businessCodeRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Business code with id: " + id + " not found"));

        return BusinessCodeResponse.builder()
                .id(businessCode.getId())
                .code(businessCode.getCode())
                .role(businessCode.getRole())
                .build();
    }

    public BusinessCodeResponse patchBusinessCode(Integer id, BusinessCodeRequest request) {
        RequestValidator.validateObject(request);
        Role role = roleRepository.findById(request.getRoleId()).orElseThrow(() ->
                new NotFoundException("Role with id: " + request.getRoleId() + " not found"));
        BusinessCode businessCode = BusinessCode.builder()
                .id(id)
                .code(request.getCode())
                .role(role)
                .build();
        businessCode = businessCodeRepository.save(businessCode);
        return BusinessCodeResponse.builder()
                .id(businessCode.getId())
                .code(businessCode.getCode())
                .role(businessCode.getRole())
                .build();
    }

    public GenericResponse deleteBusinessCode(Integer id) {
        if (!businessCodeRepository.existsById(id)) {
            throw new NotFoundException("Business code with id: " + id + " not found");
        }
        businessCodeRepository.deleteById(id);
        return new GenericResponse(businessCodeRepository.findById(id), "Business code with id: " + id
                + " successfully deleted", HttpServletResponse.SC_OK);
    }

    public List<GenericResponse> createBusinessCodes(List<BusinessCodeRequest> request) {
        List<GenericResponse> response = new ArrayList<>();
        for (BusinessCodeRequest req : request) {
            if (RequestValidator.isObjectValid(req) != null) {
                response.add(new GenericResponse(req, RequestValidator.isObjectValid(req), HttpServletResponse.SC_BAD_REQUEST));
            } else {
                Role role = roleRepository.findById(req.getRoleId()).orElse(null);
                if (role != null) {
                    BusinessCode businessCode = BusinessCode.builder()
                            .code(req.getCode())
                            .role(role)
                            .build();
                    businessCodeRepository.save(businessCode);
                    response.add(new GenericResponse(req, "Business code successfully created", HttpServletResponse.SC_CREATED));
                } else {
                    response.add(new GenericResponse(req, "The roleId " + req.getRoleId() + "not found", HttpServletResponse.SC_BAD_REQUEST));
                }
            }
        }
        return response;
    }

    public List<BusinessCodeResponse> getBusinessCodes() {
        List<BusinessCode> businessCodes = businessCodeRepository.findAll();
        if (businessCodes.isEmpty())
            throw new NotFoundException("No business code was found");

        List<BusinessCodeResponse> businessCodeResponses = new ArrayList<>();
        for (BusinessCode businessCode : businessCodes) {
            businessCodeResponses.add(BusinessCodeResponse.builder()
                    .id(businessCode.getId())
                    .code(businessCode.getCode())
                    .role(businessCode.getRole())
                    .build()
            );
        }
        return businessCodeResponses;
    }

    public List<GenericResponse> deleteBusinessCodes(List<Integer> ids) {
        List<GenericResponse> response = new ArrayList<>();
        for (Integer id : ids) {
            if (businessCodeRepository.existsById(id)) {
                response.add(new GenericResponse(businessCodeRepository.findById(id), "Business code with id: " + id + " successfully deleted", HttpServletResponse.SC_OK));
                businessCodeRepository.deleteById(id);
            } else {
                response.add(new GenericResponse(null, "Business code with id: " + id + " not found", HttpServletResponse.SC_NOT_FOUND));
            }
        }
        return response;
    }
}