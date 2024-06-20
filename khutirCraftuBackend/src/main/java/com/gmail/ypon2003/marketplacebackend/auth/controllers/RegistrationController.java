package com.gmail.ypon2003.marketplacebackend.auth.controllers;

import com.gmail.ypon2003.marketplacebackend.auth.dto.RegisterRequest;
import com.gmail.ypon2003.marketplacebackend.auth.services.RegistrationService;
import com.gmail.ypon2003.marketplacebackend.repositories.PersonRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final PersonRepository personRepository;


    @PostMapping("/register")
    public ResponseEntity<?> registerPerson(@RequestBody @Valid RegisterRequest registerRequest) {
        try {
            if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
                return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
            }
            if(personRepository.findByEmail(registerRequest.getEmail()) != null) {
                return new ResponseEntity<>("User with this email already exists", HttpStatus.BAD_REQUEST);
            }

            registrationService.register(registerRequest.getEmail(), registerRequest.getPassword(), registerRequest.getConfirmPassword());

            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/change-role-to-seller")
    public ResponseEntity<?> changeRoleToSeller(@RequestParam String email) {
        try {
            registrationService.changeRoleToSeller(email);
            return new ResponseEntity<>("User role changed to seller successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
