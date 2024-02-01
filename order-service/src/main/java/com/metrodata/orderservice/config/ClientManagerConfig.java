package com.metrodata.orderservice.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;

@Configuration
@AllArgsConstructor
public class ClientManagerConfig {

    private ClientRegistrationRepository clientRegistrationRepository;
    private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

    @Bean
    public OAuth2AuthorizedClientManager clientManager(){
        OAuth2AuthorizedClientProvider oAuth2AuthorizedClientProvider
                = OAuth2AuthorizedClientProviderBuilder
                .builder()
                .clientCredentials()
                .build();

        DefaultOAuth2AuthorizedClientManager oAuth2AuthorizedClientManager
                = new DefaultOAuth2AuthorizedClientManager(
                        clientRegistrationRepository,oAuth2AuthorizedClientRepository);

        oAuth2AuthorizedClientManager.setAuthorizedClientProvider(oAuth2AuthorizedClientProvider);
        return oAuth2AuthorizedClientManager;
    }

}
