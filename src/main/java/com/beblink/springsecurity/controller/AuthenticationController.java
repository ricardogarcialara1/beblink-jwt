package com.beblink.springsecurity.controller;

import com.beblink.springsecurity.model.dto.AuthenticationRequest;
import com.beblink.springsecurity.model.dto.AuthenticationResponse;
import com.beblink.springsecurity.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * The type Authentication controller.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Login response entity.
     *
     * @param authRequest the auth request
     * @return the response entity
     */
    @PreAuthorize("permitAll")
    /**
     * Login response entity.
     *
     * @param authRequest the auth request
     * @return the response entity
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authRequest){
        var jwtDto = authenticationService.login(authRequest);

        return ResponseEntity.ok(jwtDto);
    }

    /**
     * Public access endpoint string.
     *
     * @return the string
     */
    @PreAuthorize("permitAll")
    @GetMapping("/public-access")
    public String publicAccessEndpoint(){
        return "este endpoint es público";
    }
}
