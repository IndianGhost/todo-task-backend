package io.github.indianghost.todotask.controllers;

import io.github.indianghost.todotask.records.JwtRequestRecord;
import io.github.indianghost.todotask.records.JwtResponseRecord;
import io.github.indianghost.todotask.services.CustomUserDetailsService;
import io.github.indianghost.todotask.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public JwtResponseRecord login(@RequestBody JwtRequestRecord jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.username(), jwtRequest.password()));
        } catch (AuthenticationException e) {
            throw new Exception("Invalid credentials", e);
        }

        // Generate JWT on successful login
        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.username());
        final String jwt = jwtUtils.generateToken(userDetails);

        return new JwtResponseRecord(jwt);
    }
}
