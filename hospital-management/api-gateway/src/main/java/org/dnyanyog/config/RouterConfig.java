package org.dnyanyog.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder
        .routes()
        
        // directory service
        .route(
            "directory_login_route",
            r -> r.path("/api/v1/directory/validate/**").uri("http://directory-service:8081"))
        .route(
            "directory_add_route",
            r -> r.path("/api/v1/directory/**").uri("http://directory-service:8081"))

        // patient service
        .route("patient_add_route",
        		r -> r.path("/api/v1/patient/**").uri("http://patient-service:8082"))

        // appointment service
        .route(
            "appointment_add_route",
            r -> r.path("/api/v1/appointment/**").uri("http://appointment-service:8084"))

        // case service
        .route("case_add_route",
        		r -> r.path("/api/v1/case/**").uri("http://case:8083"))
        .build();
  }
}
