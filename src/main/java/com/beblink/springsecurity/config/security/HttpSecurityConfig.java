package com.beblink.springsecurity.config.security;

import com.beblink.springsecurity.config.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The type Http security config.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
public class HttpSecurityConfig {

    /**
     * Security filter chain security filter chain.
     *
     * @param http                   the http
     * @param authenticationProvider the authentication provider
     * @param authenticationFilter   the authentication filter
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider, JwtAuthenticationFilter authenticationFilter) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionMangConfig -> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
                //.authorizeHttpRequests(builderRequestMatchers());
        return http.build();
    }

    /*private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> builderRequestMatchers() {
        return authConfig -> {
            //Public Endpoint
            authConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/auth/public-access").permitAll();
            authConfig.requestMatchers("/error").permitAll()*//**//*;
            //Private Endpoint
            authConfig.requestMatchers(HttpMethod.GET, "/products").hasAuthority(Permission.READ_ALL_PRODUCTS.name());
            authConfig.requestMatchers(HttpMethod.POST, "/products").hasAuthority(Permission.SAVE_ONE_PRODUCT.name());

            authConfig.anyRequest().denyAll();
        };
    }*/
}
