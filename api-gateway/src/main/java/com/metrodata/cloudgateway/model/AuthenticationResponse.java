package com.metrodata.cloudgateway.model;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
	
    private String email;
    private String accessToken;
    private String refreshToken;
    private Long expiresAt;
    private Collection<String> authorities;

}
