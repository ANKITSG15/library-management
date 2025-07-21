package com.lms.controller;


import com.lms.dto.AuthRequest;
import com.lms.dto.AuthResponse;
import com.lms.dto.Role;

import com.lms.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok().body(authService.doLogin(request));
    }

    @PostMapping("/register/member")
    public ResponseEntity<AuthResponse> registerMember(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok().body(authService.register(request, Role.MEMBERS));
    }

    @PostMapping("/register/librarian")
    public ResponseEntity<AuthResponse> registerLibrarian(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok().body(authService.register(request, Role.LIBRARIAN));
    }

}
