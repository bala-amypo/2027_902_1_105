// package com.example.demo.config;

// import io.swagger.v3.oas.models.OpenAPI;
// import io.swagger.v3.oas.models.servers.Server;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import java.util.List;

// @Configuration
// public class SwaggerConfig {

//     @Bean
//     public OpenAPI customOpenAPI() {
//         return new OpenAPI()
//                 // You need to change the port as per your server
//                 .servers(List.of(
//                         new Server().url("https://9005.vs.amypo.ai")
//                 ));
//         }
// }

package com.example.apiproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Digital Visitor Management API",
                version = "1.0",
                description = "APIs for authentication, visitors, hosts, appointments, visit logs and alerts"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT token required for API access",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
 
}