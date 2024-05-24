package com.beblink.springsecurity.service;

import com.beblink.springsecurity.model.User;
import com.beblink.springsecurity.model.dto.AuthenticationRequest;
import com.beblink.springsecurity.model.dto.AuthenticationResponse;
import com.beblink.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Authentication service.
 */
@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    /**
     * Login authentication response.
     *
     * @param authRequest the auth request
     * @return the authentication response
     */
    public AuthenticationResponse login(AuthenticationRequest authRequest) {

        var authToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()
        );

        authenticationManager.authenticate(authToken);

        var user = userRepository.findByUsername(authRequest.getUsername()).get();
        
        var jwt = jwtService.generateToken(user, generateExtraClaims(user));

        return new AuthenticationResponse(jwt);
    }

    private Map<String, Object> generateExtraClaims(User user) {

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("permissions", user.getAuthorities());

        return extraClaims;
    }
}
