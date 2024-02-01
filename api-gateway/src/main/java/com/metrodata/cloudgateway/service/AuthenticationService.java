package com.metrodata.cloudgateway.service;

import java.util.stream.Collectors;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.metrodata.cloudgateway.model.AuthenticationResponse;

@Service
public class AuthenticationService{
    public AuthenticationResponse login(
            OidcUser oidcUser,
            Model model,
            OAuth2AuthorizedClient client){

        AuthenticationResponse authenticationResponse
                = AuthenticationResponse.builder()
                .email(oidcUser.getEmail())
                .accessToken(client.getAccessToken().getTokenValue())
                .refreshToken(client.getRefreshToken().getTokenValue())
                .expiresAt(client.getAccessToken().getExpiresAt().getEpochSecond())
                .authorities(oidcUser.getAuthorities()
                        .stream()
                        .map(authority -> authority.getAuthority())
                        .collect(Collectors.toList()))
                .build();
        return authenticationResponse;
    }
}
