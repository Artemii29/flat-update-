package com.example.demo.controllers;

import com.example.demo.dtos.JwtRequest;
import com.example.demo.dtos.RegistrationUserDto;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1")
//@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final AuthService authService;
    private final UserService service;


    public UserController(AuthService authService, UserService service){
        this.authService = authService;
        this.service = service;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        System.out.println("UserController createAuthToken " + authRequest.getUsername());
        return authService.createAuthToken(authRequest);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

}
