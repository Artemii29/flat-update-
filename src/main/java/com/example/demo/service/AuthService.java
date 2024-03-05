package com.example.demo.service;
import com.example.demo.dtos.JwtRequest;
import com.example.demo.dtos.JwtResponse;
import com.example.demo.dtos.RegistrationUserDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exceptions.AppError;
import com.example.demo.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.authentication.AuthenticationManager;



@Service
public class AuthService {
    private final UserService userService;
    private final TokenUtil tokenUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserService userService, TokenUtil tokenUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenUtil = tokenUtil;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
        System.out.println("createAuthToken ");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        }
        catch (BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        System.out.println("userdetails");

        UserDetails userdetails = userService.loadUserByUsername(authRequest.getUsername());
        System.out.println("userdetails"+userdetails);
        String token = tokenUtil.generateToken(userdetails);
        System.out.println(token);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto){
        System.out.println("user ");

        if(!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),"Пароли не совпадают"),HttpStatus.BAD_REQUEST);
        }
        if(userService.findByName(registrationUserDto.getName()).isPresent()){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),"Пользователь с таким именем существует"),HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrationUserDto);
        UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPhone_number(), user.getPassword());
        return ResponseEntity.ok(userDto);
    }
}
