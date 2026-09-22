package com.giuliabalaban.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;

@Configuration
public class SecurityConfig {

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

XorCsrfTokenRequestAttributeHandler requestHandler=
new XorCsrfTokenRequestAttributeHandler();

requestHandler.setCsrfRequestAttributeName(null);

http
.authorizeHttpRequests(auth->auth
.anyRequest().permitAll()
)
.csrf(csrf->csrf
.csrfTokenRequestHandler(requestHandler)
)
.headers(headers->headers
.contentSecurityPolicy(csp->csp
.policyDirectives(
"default-src 'self'; "+
"script-src 'self'; "+
"style-src 'self'; "+
"img-src 'self' data:; "+
"media-src 'self'; "+
"font-src 'self'; "+
"object-src 'none'; "+
"base-uri 'self'; "+
"form-action 'self'; "+
"frame-ancestors 'none'"
)
)
);

return http.build();
}

}