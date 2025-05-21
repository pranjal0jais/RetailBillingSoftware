package com.pranjal.controller;

import com.pranjal.JwtUtil.JwtUtil;
import com.pranjal.dto.AuthRequest;
import com.pranjal.dto.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder,
                          UserDetailsService userDetailService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userDetailService = userDetailService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/encode")
    public ResponseEntity<String> encode(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(passwordEncoder.encode(request.get("password")));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticate(request.getEmail(), request.getPassword());
        final UserDetails userDetail = userDetailService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetail.getUsername());
        return ResponseEntity.ok(new AuthResponse(userDetail.getUsername(), token));
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
    }
}