package com.metrodata.cloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class CloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudGatewayApplication.class, args);
		System.out.println("==[API GATEWAY RUNNING]==");
	}

	@Bean
	KeyResolver userKeySolver(){
		return exchange -> Mono.just("userKey");
	}

}
