package com.beblink.springsecurity.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Authentication request.
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationRequest {

    private String username;

    private String password;
}
