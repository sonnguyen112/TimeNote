package com.TimeNote.gatewayserver.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.TimeNote.gatewayserver.security.AuthenticationFilter;

@Configuration
public class GatewayConfig {
    
    @Autowired
	private AuthenticationFilter filter;

    
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes().route("auth", r -> r.path("/auth_api/**").filters(f -> f.filter(filter)).uri("lb://auth-service"))
				.route("course", r -> r.path("/course_api/**").filters(f -> f.filter(filter)).uri("lb://course-service"))
				.route("attendance", r -> r.path("/attendance_api/**").filters(f -> f.filter(filter)).uri("lb://attendance-service"))
				.build();
	}
    
}
