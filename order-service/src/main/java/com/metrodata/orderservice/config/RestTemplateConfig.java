package com.metrodata.orderservice.config;

import com.metrodata.orderservice.external.decoder.RestTemplateResponseErrorHandler;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Access;
import java.util.Arrays;

@Configuration
@AllArgsConstructor
public class RestTemplateConfig {

    private ClientManagerConfig clientManagerConfig;

    @Bean // Tiap kali aplikasi berjalan object ini akan di jalankan/ logic di eksekusi
    @LoadBalanced
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());

        // Add Interceptor here
        restTemplate.setInterceptors(Arrays.asList(new RestTemplateInterceptor(
                clientManagerConfig.clientManager()
        )));
        return restTemplate;
    }

}
