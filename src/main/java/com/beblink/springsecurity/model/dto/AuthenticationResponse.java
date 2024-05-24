package com.beblink.springsecurity.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Authentication response.
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse {

    private String jwt;
}
