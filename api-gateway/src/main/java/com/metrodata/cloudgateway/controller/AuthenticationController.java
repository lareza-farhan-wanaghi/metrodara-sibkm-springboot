package com.metrodata.cloudgateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.metrodata.cloudgateway.model.AuthenticationResponse;
import com.metrodata.cloudgateway.service.AuthenticationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/authenticate")
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @GetMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @AuthenticationPrincipal OidcUser oidcUser,
            Model model,
            @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client)
    {
        return new ResponseEntity<>(authenticationService.login(oidcUser,model,client), HttpStatus.OK);
    }

    // @GetMapping("/testnet")
    // public ResponseEntity<String> testnet(){
    //     return new ResponseEntity<>("Testnet",HttpStatus.OK);
    // }

}
