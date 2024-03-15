package com.cybersecurity.iam.service;

import com.cybersecurity.iam.database.entity.Container;
import com.cybersecurity.iam.exception.type.NotFoundException;
import com.cybersecurity.iam.payload.request.ContainerRequest;
import com.cybersecurity.iam.payload.response.ContainerResponse;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.validator.RequestValidator;
import com.cybersecurity.iam.repository.ContainerRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContainerService {

    private final ContainerRepository containerRepository;

    public ContainerResponse createContainer(ContainerRequest request) {
        RequestValidator.validateObject(request);
        Container parent = containerRepository.findById(request.getParentId())
                .orElseThrow(() -> new NotFoundException("Container with id: " + request.getParentId() + "not found"));
        Container container = Container.builder()
                .name(request.getName())
                .description(request.getDescription())
                .parent(parent)
                .build();
        container = containerRepository.save(container);
        return ContainerResponse.builder()
                .id(container.getId())
                .name(container.getName())
                .description(container.getDescription())
                .parent(container.getParent())
                .build();
    }

    public ContainerResponse getContainer(Integer id) {
        Container container = containerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Container with id: " + id + "not found"));
        return ContainerResponse.builder()
                .id(container.getId())
                .name(container.getName())
                .description(container.getDescription())
                .parent(container.getParent())
                .build();
    }

    public ContainerResponse patchContainer(Integer id, ContainerRequest request) {
        RequestValidator.validateObject(request);
        Container parent = containerRepository.findById(request.getParentId())
                .orElseThrow(() -> new NotFoundException("Container with id: " + request.getParentId() + "not found"));
        Container container = Container.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .parent(parent)
                .build();
        container = containerRepository.save(container);
        return ContainerResponse.builder()
                .id(container.getId())
                .name(container.getName())
                .description(container.getDescription())
                .parent(parent)
                .build();
    }

    public GenericResponse deleteContainer(Integer id) {
        if (!containerRepository.existsById(id)) {
            throw new NotFoundException("Container with id: " + id + " not found");
        }
        containerRepository.deleteById(id);
        return new GenericResponse(containerRepository.findById(id), "Container with id: " + id
                + " successfully deleted", HttpServletResponse.SC_OK);
    }

    public List<GenericResponse> createContainers(List<ContainerRequest> request) {
        List<GenericResponse> response = new ArrayList<>();
        for (ContainerRequest req : request) {
            if (RequestValidator.isObjectValid(req) != null) {
                response.add(new GenericResponse(req, RequestValidator.isObjectValid(req), HttpServletResponse.SC_BAD_REQUEST));
            } else {
                Container parent = containerRepository.findById(req.getParentId())
                        .orElse(null);
                if (parent != null) {
                    Container container = Container.builder()
                            .name(req.getName())
                            .description(req.getDescription())
                            .parent(parent)
                            .build();
                    containerRepository.save(container);
                    response.add(new GenericResponse(req, "Container successfully created", HttpServletResponse.SC_CREATED));
                } else {
                    response.add(new GenericResponse(req, "The containerId " + req.getParentId() + "not found", HttpServletResponse.SC_BAD_REQUEST));
                }
            }
        }
        return response;
    }

    public List<ContainerResponse> getContainers() {
        List<Container> containers = containerRepository.findAll();
        if (containers.isEmpty()) {
            throw new NotFoundException("No container was found");
        }

        List<ContainerResponse> containersResponse = new ArrayList<>();
        for (Container container : containers) {
            containersResponse.add(ContainerResponse.builder()
                    .id(container.getId())
                    .name(container.getName())
                    .description(container.getDescription())
                    .parent(container.getParent())
                    .build()
            );
        }
        return containersResponse;
    }

    public List<GenericResponse> deleteContainers(List<Integer> ids) {
        List<GenericResponse> response = new ArrayList<>();
        for (Integer id : ids) {
            if (containerRepository.existsById(id)) {
                response.add(new GenericResponse(containerRepository.findById(id), "Container with id: " + id + " successfully deleted", HttpServletResponse.SC_OK));
                containerRepository.deleteById(id);
            } else {
                response.add(new GenericResponse(null, "Container with id: " + id + " not found", HttpServletResponse.SC_NOT_FOUND));
            }
        }
        return response;
    }
}
