package com.cybersecurity.iam.controller;

import com.cybersecurity.iam.payload.request.AddressRequest;
import com.cybersecurity.iam.payload.response.AddressResponse;
import com.cybersecurity.iam.payload.response.MessageResponse;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@PreAuthorize("hasAuthority('manage')")
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/iam-api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    @PostMapping()
    public ResponseEntity<AddressResponse> createAddress(@RequestBody AddressRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddress(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddress(@PathVariable Integer id) {
        return ResponseEntity.ok(addressService.getAddress(id));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<AddressResponse> patchAddress(@PathVariable Integer id, @RequestBody AddressRequest request) {
        return ResponseEntity.ok(addressService.patchAddress(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteAddress(@PathVariable Integer id) {
        return ResponseEntity.ok(addressService.deleteAddress(id));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> createAddresses(@RequestBody List<AddressRequest> request) {
        return ResponseEntity.ok(addressService.createAddresses(request));
    }

    @GetMapping()
    public ResponseEntity<List<AddressResponse>> getAddresses() {
        return ResponseEntity.ok(addressService.getAddresses());
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> deleteAddresses(@RequestBody List<Integer> ids){
        return ResponseEntity.ok(addressService.deleteAddresses(ids));
    }
}
