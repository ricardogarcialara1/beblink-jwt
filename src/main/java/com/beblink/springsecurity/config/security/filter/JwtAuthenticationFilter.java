package com.beblink.springsecurity.config.security.filter;

import com.beblink.springsecurity.repository.UserRepository;
import com.beblink.springsecurity.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * The type Jwt authentication filter.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Obtener el header que contiene el jwt
        var authHeader = request.getHeader("Authorization"); //Bearer jwt

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        //Obtener jwt desde header
        var jwt = authHeader.split(" ")[1];

        //Obtener subject/username desde el jwt
        var username = jwtService.extractUsername(jwt);

        //Setear un objeto Authentication dentro del SecurityContext
        var user = userRepository.findByUsername(username).get();
        var authToken = new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        //Ejecutar el resto de filtros
        filterChain.doFilter(request, response);
    }
}
